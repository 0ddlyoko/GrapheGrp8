package be.umons.graphegrp8;

import java.util.HashMap;

public class Community {
	private int id;
	private HashMap<Integer, Node> nodes;

	public Community(int id) {
		this.id = id;
		this.nodes = new HashMap<Integer, Node>();
	}

	/**
	 * Remove node from old community and add node to this community
	 * 
	 * @param node The node
	 */
	public void addNode(Node node) {
		if (node.getCommunity() != null)
			node.getCommunity().removeNode(node);
		node.setCommunity(this);
		nodes.put(node.getId(), node);
	}

	/**
	 * Remove node from this community
	 * 
	 * @param node The node
	 */
	public void removeNode(Node node) {
		nodes.remove(node.getId());
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
