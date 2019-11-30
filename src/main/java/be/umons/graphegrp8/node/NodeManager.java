package be.umons.graphegrp8.node;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.umons.graphegrp8.Modularity;
import be.umons.graphegrp8.file.FileParser;
import be.umons.graphegrp8.file.ReadFile;

public class NodeManager {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private FileParser fileParser;
	private Modularity modularity;
	private Node[] nodes;
	private Node[] fakeIdNodes;
	private HashMap<Integer, Community> communities;
	private HashMap<Integer, Community> tempCommunities;
	// If true, a node has changed community
	private boolean edited;
	/** The current node*/
	private Node selectedNode;

	public NodeManager(FileParser fileParser) {
		this.fileParser = fileParser;
		this.communities = new HashMap<Integer, Community>();
	}

	/**
	 * Load all
	 */
	public void load() {
		LOG.info("Loading NodeManager");
		// Create the Modularity
		modularity = new Modularity(fileParser);
		LOG.info("{} nodes loaded", fileParser.getNbVertices());
		loadNodes();
		loadEdges();
	}

	/**
	 * Load nodes
	 */
	public void loadNodes() {
		LOG.info("Loading {} nodes", fileParser.getNbVertices());
		this.nodes = new Node[fileParser.getNbVertices()];
		this.fakeIdNodes = new Node[fileParser.getNbVertices()];
		for (int i = 1; i <= fileParser.getNbVertices(); i++) {
			LOG.info("Loading node {}", i);
			// Create community
			Community c = new Community(i);
			communities.put(c.getId(), c);
			// Create node
			Node n = new Node(i);
			nodes[i - 1] = n;
			fakeIdNodes[i - 1] = n;
			changeCommunity(n, c);
		}
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
		LOG.info("Loading {} edges for {} nodes", fileParser.getNbEdges(), fileParser.getNbVertices());
		for (int i = 0; i < nodes.length; i++) {
			LOG.info("  - Node {} with {} neighbors", i + 1, head[i + 1] - head[i]);
			Node n = nodes[i];
			// For each neighbors
			for (int j = head[i]; j < head[i + 1]; j++) {
				LOG.info("    - => {}", succ[j - 1]);
				// A node
				n.addNeighbors(nodes[succ[j - 1] - 1]);
			}
		}
	}

	/**
	 * Change the community of the specific node<br />
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
		// Test if old community is empty and if it is, remove it from the HashMap
		if (deleteOldCommunity && oldCommunity != null && oldCommunity.size() == 0)
			communities.remove(oldCommunity.getId());
	}

	// Algorithme Evolutionnaire

	/**
	 * Start the algorithm 
	 * first phase
	 */
	public void start() {
		initialization();
		// Done, call evaluation
		evaluation();
		do {
			int i=0;
			// Selection
			selection();
			breading();
			ArrayList<Integer> tabOfIdCommunities = mutation();
			evaluationChild(tabOfIdCommunities);
			System.out.println("hello comm "+this.edited);
		} while (!doWeStopNow());
	}

	/**
	 * Initialisation aléatoire d'une population<br />
	 * Chaque noeud aura un id aléatoire unique
	 */
	public void initialization() {
		LOG.info("Mixing the content ...");
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
		LOG.info("Content mixed !");
	}

	/**
	 * Evaluation des performances des individus<br />
	 * Ici nous allons calculer la modularité
	 */
	public void evaluation() {
		LOG.info("begin evaluation of performances ...");
		for(Entry<Integer, Community> entry : communities.entrySet()) {
			HashMap<Integer, Community> partition = new HashMap<Integer, Community>();
			partition.put(entry.getKey(), entry.getValue());
			double mod = modularity.resultOfModularity(partition);
			entry.getValue().setCommunityCost(mod);
		}
		LOG.info("end evaluation !");
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
		}
		else {
			// Test if it's the last one
			if (selectedNode.getFakeId() == fakeIdNodes.length) {
				// Last one so here we go back to first one
				selectedNode = fakeIdNodes[0];
				edited = false;
			}
				
			else
				// Not last one so we take the next one
				selectedNode = fakeIdNodes[selectedNode.getFakeId()];
		}
		LOG.info("Selecting {}", selectedNode);
	}

	/**
	 * Croisements du noeud courant avec ses noeuds adjacents<br />
	 * Fusion
	 */
	public void breading() {
		tempCommunities = new HashMap<Integer, Community>(communities);
		for(Node node : selectedNode.getNeighbors()) {
			node.getCommunity().addNode(selectedNode);
		}
	}

	/**
	 * Mutations: on va construire une HashMap de communauté 
	 * (noeud courant associé à chaque voisin se trouvant deja dans une communauté
	 * et c'est avec cette hashmap qu'on évaluara les performances 
	 */
	public ArrayList<Integer> mutation() {
		ArrayList<Integer> tabOfIdCommunities = new ArrayList<Integer>();
		for(Node node : selectedNode.getNeighbors()) {
			tabOfIdCommunities.add(node.getCommunity().getId());
		}
		return tabOfIdCommunities;
	}

	/**
	 * Evaluation des performances des enfants<br />
	 * Calcul de la modularité locale pour chaques
	 */
	public void evaluationChild(ArrayList<Integer> indexCommunity) {
		for(Integer i : indexCommunity) {
			HashMap<Integer, Community>partition = new HashMap<Integer, Community>();
			partition.put(i, communities.get(i));
			double mod = modularity.resultOfModularity(partition);
			communities.get(i).setCommunityCost(mod);
		}
		
		
	}

	/**
	 * Sélection pour le remplacement<br />
	 * Sélection de la meilleur modularité parmis ses voisins et remplacement si
	 * besoin
	 */
	public void selectionReplacement(ArrayList<Integer> indexCommunity) {
		
		//s'il y'a un remplacement à effectuer alors 
		//on recuperer la communauté de destination ensuite 
		//on after tempCommunities à communities
		//en fin on appel changecommunity(avec selected node et la communauté dest)
		//sinon on appele pas changeCommunitie()
		
		double maxCost = selectedNode.getCommunity().getCommunityCost();
		Community dest = null;
		for(Integer i : indexCommunity) {
			double currentCost = communities.get(i).getCommunityCost();
			if(currentCost > 0 && currentCost > maxCost) {
				maxCost = currentCost;
				dest = communities.get(i);
			}
		}
		communities = tempCommunities;
		if(dest != null) {
			changeCommunity(this.selectedNode, dest);
			evaluation();
			selectedNode.setFakeId(selectedNode.getFakeId() + 1);
			edited = true;
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
}
