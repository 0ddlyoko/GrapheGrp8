package be.umons.graphegrp8;

import java.io.File;
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
		load();
	}

	/**
	 * Load all
	 */
	private void load() {
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

	public void changeCommunity(Node node, Community community) {
		community.addNode(node);
	}

	public File getFile() {
		return file;
	}

	public ReadFile getReadFile() {
		return readFile;
	}

	public Modularity getModularity() {
		return modularity;
	}

	public Node getNode(long id) {
		return nodes.get(id);
	}

	public Community getCommunity(long id) {
		return communities.get(id);
	}
}
