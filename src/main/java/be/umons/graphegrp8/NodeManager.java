package be.umons.graphegrp8;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeManager {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private File file;
	private ReadFile readFile;
	private Modularity modularity;
	private Node[] nodes;
	private Node[] fakeIdNodes;
	private HashMap<Integer, Community> communities;

	public NodeManager(File file) {
		this.file = file;
		this.readFile = new ReadFile(file);
		this.communities = new HashMap<Integer, Community>();
	}

	/**
	 * Load all
	 */
	public void load() {
		LOG.info("Loading NodeManager");
		// Create the Modularity
		modularity = new Modularity(readFile);
		LOG.info("{} nodes loaded", readFile.getNbVertices());
		loadNodes();
		loadEdges();
	}

	/**
	 * Load nodes
	 */
	public void loadNodes() {
		LOG.info("Loading {} nodes", readFile.getNbVertices());
		this.nodes = new Node[readFile.getNbVertices()];
		this.fakeIdNodes = new Node[readFile.getNbVertices()];
		for (int i = 1; i <= readFile.getNbVertices(); i++) {
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
		int[] head = readFile.getHeadTab();
		int[] succ = readFile.getSuccTab();
		// Add neighbors
		// For each node
		LOG.info("Loading {} edges for {} nodes", readFile.getNbEdges(), readFile.getNbVertices());
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
			fakeIdNodes[i].setFakeId(rand);
			fakeIdNodes[rand].setFakeId(i);
			// Invert position
			Node a = fakeIdNodes[i];
			fakeIdNodes[i] = fakeIdNodes[rand];
			fakeIdNodes[rand] = a;
		}
		LOG.info("Content mixed !");
		// Done, call evaluation
		evaluation();
	}

	/**
	 * Evaluation des performances des individus<br />
	 * Ici nous allons calculer la modularité
	 */
	public void evaluation() {
	}

	/**
	 * Sélection pour la reproduction<br />
	 * Ici nous allons sélectionner un noeud
	 */
	public void selection() {
	}

	/**
	 * Croisements<br />
	 * Fusion
	 */
	public void breading() {
	}

	/**
	 * Mutations
	 */
	public void mutation() {
	}

	/**
	 * Evaluation des performances des enfants<br />
	 * Calcul de la modularité locale pour chaques
	 */
	public void evaluationChild() {
	}

	/**
	 * Sélection pour le remplacement<br />
	 * Sélection de la meilleur modularité parmis ses voisins et remplacement si
	 * besoin
	 */
	public void selectionReplacement() {
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
	public void doWeStopNow() {
	}

	// Getters

	public File getFile() {
		return file;
	}

	public ReadFile getReadFile() {
		return readFile;
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
