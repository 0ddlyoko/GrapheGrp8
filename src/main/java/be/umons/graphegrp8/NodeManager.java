package be.umons.graphegrp8;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeManager {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private File file;
	private ReadFile readFile;
	private Modularity modularity;
	private HashMap<Integer, Node> nodes;
	private HashMap<Integer, Community> communities;

	public NodeManager(File file) {
		this.file = file;
		this.readFile = new ReadFile(file);
		this.nodes = new HashMap<Integer, Node>();
		this.communities = new HashMap<Integer, Community>();
//		load();
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
		for (int i = 1; i <= readFile.getNbVertices(); i++) {
			LOG.info("Loading node {}", i);
			// Create community
			Community c = new Community(i);
			communities.put(c.getId(), c);
			// Create node
			Node n = new Node(i);
			nodes.put(n.getId(), n);
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
		for (int i = 0; i < nodes.size(); i++) {
			LOG.info("  - Node {} with {} neighbors", i + 1, head[i + 1] - head[i]);
			Node n = nodes.get(i + 1);
			// For each neighbors
			for (int j = head[i]; j < head[i + 1]; j++) {
				LOG.info("    - => {}", succ[j - 1]);
				// A node
				n.addNeighbors(nodes.get(succ[j - 1]));
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
	 * Initialisation aléatoire d'une population
	 */
	public void initialization() {
		load();
		// On appele la suite
		evaluation();
	}

	/**
	 * Evaluation des performances des individus
	 */
	public void evaluation() {
	}

	/**
	 * Sélection pour la reproduction
	 */
	public void selection() {
	}

	/**
	 * Croisements
	 */
	public void breading() {
	}

	/**
	 * Mutations
	 */
	public void mutation() {
	}

	/**
	 * Evaluation des performances des enfants
	 */
	public void evaluationChild() {
	}

	/**
	 * Sélection pour le remplacement
	 */
	public void selectionReplacement() {
	}

	/**
	 * Stop ?
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

	public Collection<Node> getNodes() {
		return nodes.values();
	}

	public Node getNode(int id) {
		return nodes.get(id);
	}

	public Collection<Community> getCommunities() {
		return communities.values();
	}

	public Community getCommunity(int id) {
		return communities.get(id);
	}
}
