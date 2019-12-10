package be.umons.graphegrp8.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.umons.graphegrp8.Modularity;
import be.umons.graphegrp8.file.FileParser;

/**
 * MIT License
 * 
 * Copyright (c) 2019 GrapheGrp8
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class NodeManager {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private FileParser fileParser;
	private Modularity modularity;
	private Node[] nodes;
	private Node[] fakeIdNodes;
	private HashMap<Integer, Community> communities;
	// If true, a node has changed community
	private boolean edited;
	// If true, a node has changed community
	private boolean secondEdited;
	/** The current node */
	private Node selectedNode;

	private int retry = 0;

	public NodeManager(FileParser fileParser) {
		this.fileParser = fileParser;
		modularity = new Modularity(fileParser);
	}

	/**
	 * Load all
	 */
	public void load() {
		// Create the Modularity
		loadNodes();
		loadEdges();
		loadCommunities();
	}

	/**
	 * Load nodes
	 */
	public void loadNodes() {
		// Clear (Garbage Collector)
		this.nodes = new Node[fileParser.getNbVertices()];
		this.fakeIdNodes = new Node[fileParser.getNbVertices()];
		for (int i = 1; i <= fileParser.getNbVertices(); i++)
			// Create node
			fakeIdNodes[i - 1] = nodes[i - 1] = new Node(i);
	}

	/**
	 * Load edges
	 */
	public void loadEdges() {
		// Create all nodes
		int[] head = fileParser.getHeadTab();
		int[] succ = fileParser.getSuccTab();
		// Add neighbors
		// For each node
		for (int i = 0; i < nodes.length; i++) {
			Node n = nodes[i];
			// For each neighbors
			for (int j = head[i]; j < head[i + 1]; j++) {
				// A neighbor
				n.addNeighbors(nodes[succ[j - 1] - 1]);
			}
		}
	}

	/**
	 * Load communities
	 */
	public void loadCommunities() {
		if (communities != null)
			communities.clear();
		communities = new HashMap<>();
		for (int i = 0; i < nodes.length; i++) {
			Community c = new Community(i);
			communities.put(c.getId(), c);
			c.addNode(nodes[i]);
		}
	}

	/**
	 * Change the community of the specific node<br />
	 * Calculate the new cost for the old and new community<br />
	 * If old community is empty, remove the old community from the HashMap
	 * 
	 * @param node      The node
	 * @param community The new community
	 */
	public void changeCommunity(Node node, Community community) {
		changeCommunity(node, community, true);
	}

	/**
	 * Change the community of the specific node<br />
	 * Calculate the new cost for the old and new community<br />
	 * If deleteOldCommunity is set to true and old community is empty, remove the
	 * old community from the HashMap
	 * 
	 * @param node               The node
	 * @param community          The new community
	 * @param deleteOldCommunity If set to true and the old community is empty,
	 *                           remove the old community from the HashMap
	 */
	public void changeCommunity(Node node, Community community, boolean deleteOldCommunity) {
		Community oldCommunity = node.getCommunity();
		community.addNode(node);
		if (oldCommunity == null) {
			// Here it's the initialization of the node
			community.setCommunityCost(modularity.resultOfModularity(community));
		} else {
			// Calculate communityCost
			if (oldCommunity.size() == 0)
				oldCommunity.setCommunityCost(0);
			if (oldCommunity.size() == 1)
				oldCommunity.setCommunityCost(modularity.resultOfModularity(oldCommunity));
			else
				oldCommunity.setCommunityCost(modularity.resultOfModularity(oldCommunity, node, false));

			// Now community
			if (community.size() == 1)
				community.setCommunityCost(modularity.resultOfModularity(community));
			else
				community.setCommunityCost(modularity.resultOfModularity(community, node, true));

			// Test if old community is empty and if it is, remove it from the HashMap
			if (deleteOldCommunity && oldCommunity != null && oldCommunity.size() == 0)
				communities.remove(oldCommunity.getId());
		}

	}

	/**
	 * Start the algorithm
	 */
	public void start() {
		retry = 0;
		do {
			secondEdited = false;
			initialization();
			// Done, call evaluation
			evaluation();
			do {
				// Selecting a node
				selection();
				if (selectedNode.getFakeId() % 100 == 0) {
//					LOG.info("Selected {}, number of communities = {}", selectedNode.getFakeId(), communities.size());
				}
				changeCommunityIfNeeds();
			} while (!doWeStopNow());

			// Reduce nodes in same community
			// Make a copy of communities
			this.nodes = new Node[communities.size()];
			this.fakeIdNodes = new Node[communities.size()];
			// Create groups of nodes
			int i = 0;
			for (Community c : communities.values()) {
				// Retrieving all nodes from communities
				List<Node> nodes = new ArrayList<Node>();
				for (Node node : c.getNodes()) {
					if (node instanceof NodeGroup)
						for (Node n : ((NodeGroup) node).getNodes()) {
							// Save the current community of the node
							n.setCommunity(c);
							nodes.add(n);
						}
					else
						nodes.add(node);
				}
				this.fakeIdNodes[i] = this.nodes[i] = new NodeGroup(i + 1, nodes);
				// Reuse old community
				c.clear();
				changeCommunity(this.nodes[i], c);
				i++;
			}
			// Neighbors
			for (Community c : communities.values()) {
				for (Node n : c.getNodes()) {
					// Here we know that n is NodeGroup
					NodeGroup ng = (NodeGroup) n;
					for (Node n2 : ng.getNodes()) {
						// Check neighbors
						for (Node neighbor : n2.getNeighbors()) {
							// Different community, add neighbor
							if (neighbor.getCommunity().getId() != n2.getCommunity().getId()) {
								// Community of n2 has always one element
								Node other = neighbor.getCommunity().getNodes().iterator().next();
								n.addNeighbors(other);
								other.addNeighbors(n);
							}
						}
					}
				}
			}
			retry++;
			// Reset selectedNode
			selectedNode = null;
		} while (secondEdited);
	}

	/**
	 * Initialisation aléatoire d'une population<br />
	 * Chaque noeud aura un id aléatoire unique
	 */
	public void initialization() {
//		LOG.info("Mixing the content ...");
		Random r = new Random();
		int length = fakeIdNodes.length;
		for (int i = 0; i < length; i++) {
			// We have to invert the position and the fakeId of the node at i position and a
			// random node
			int rand = r.nextInt(length);
			// Invert fake id
			fakeIdNodes[i].setFakeId(rand + 1);
			fakeIdNodes[rand].setFakeId(i + 1);
			// Invert position
			Node a = fakeIdNodes[i];
			fakeIdNodes[i] = fakeIdNodes[rand];
			fakeIdNodes[rand] = a;
		}
//		LOG.info("Content mixed !");
	}

	/**
	 * Evaluation des performances des individus<br />
	 * Ici nous allons calculer la modularité pour chaque noeud
	 */
	public void evaluation() {
//		LOG.info("Calculating modularity for each communities ...");
		for (Community community : communities.values())
			community.setCommunityCost(modularity.resultOfModularity(community));
//		LOG.info("Done !");
	}

	/**
	 * Sélection pour la reproduction<br />
	 * Ici nous allons sélectionner un noeud
	 */
	public void selection() {
		if (fakeIdNodes.length == 0) {
			// Whut ?
			return;
		}
		if (selectedNode == null) {
			selectedNode = fakeIdNodes[0];
		} else {
			// Test if it's the last one
			if (selectedNode.getFakeId() == fakeIdNodes.length) {
				// Last one so here we go back to first one
				selectedNode = fakeIdNodes[0];
				edited = false;
			} else
				// Not last one so we take the next one
				selectedNode = fakeIdNodes[selectedNode.getFakeId()];
		}
	}

	/**
	 * Change de communauté si besoin
	 */
	public void changeCommunityIfNeeds() {
		Community current = selectedNode.getCommunity();
		double currentModularity = current.getCommunityCost();
		double modularityWithoutSelected = modularity.resultOfModularity(selectedNode.getCommunity(), selectedNode,
				false);
		Community best = null;
		double bestCost = -1;

		// For each neighbors calculate the new modularity if we change the community of
		// the node
		for (Node n : selectedNode.getNeighbors()) {
			// Same community, don't check here
			if (n.getCommunity().equals(current))
				continue;
			// The current cost
			double oldCost = n.getCommunity().getCommunityCost() + currentModularity;
			double newCost = modularity.resultOfModularity(n.getCommunity(), selectedNode, true)
					+ modularityWithoutSelected;
			// New cost is greater than old cost
			if (newCost > oldCost && (bestCost == -1 || newCost > bestCost)) {
				best = n.getCommunity();
				bestCost = newCost;
			}
		}
		if (best != null) {
			changeCommunity(selectedNode, best);
			edited = true;
			secondEdited = true;
		}
	}

	/**
	 * Tant qu'il y a un noeud, on continue<br />
	 * Tant qu'il y a eu un changement de communauté pour un des derniers noeud, on
	 * continue<br />
	 * Exemple:
	 * <ul>
	 * <li>On a 10 noeuds</li>
	 * <li>La boucle boucle 10 fois MINIMUM</li>
	 * <li>S'il y a eu au moins un changement de communauté durant ces 10 fois, on
	 * reboucle 10 fois</li>
	 * </ul>
	 */
	public boolean doWeStopNow() {
		return selectedNode.getFakeId() == fakeIdNodes.length && !edited;
	}

	// Getters

	public FileParser getFileParser() {
		return fileParser;
	}

	public Modularity getModularity() {
		return modularity;
	}

	public int getNumberOfNodes() {
		return nodes.length;
	}

	public Node[] getNodes() {
		return nodes;
	}

	/**
	 * @param id The id of the node
	 * @return The node that has this id
	 */
	public Node getNode(int id) {
		return nodes[id - 1];
	}

	/**
	 * @param id The fakeId of the node
	 * @return The node that has this fakeId
	 */
	public Node getFakeNode(int fakeId) {
		return fakeIdNodes[fakeId - 1];
	}

	public Collection<Community> getCommunities() {
		return communities.values();
	}

	public Community getCommunity(int id) {
		return communities.get(id);
	}

	public int getRetry() {
		return retry;
	}
}
