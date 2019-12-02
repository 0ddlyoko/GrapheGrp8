package be.umons.graphegrp8.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Community {
	private int id;
	private HashMap<Integer, Node> nodes;
	private double communityCost;

	public Community(int id) {
		this.id = id;
		this.nodes = new HashMap<Integer, Node>();
		this.communityCost = -1;
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
		node.setCommunity(null);
	}

	/**
	 * @return The number of nodes this community has
	 */
	public int size() {
		return nodes.size();
	}

	public Collection<Node> getNodes() {
		return nodes.values();
	}
	
	public List<Integer> getArrayOfNodes(){
		List<Integer> nodesList = new ArrayList<Integer>();
		for (Node nod : nodes.values()) {
			nodesList.add(nod.getId());
		}
		return nodesList;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Community))
			return false;
		return ((Community) obj).hashCode() == hashCode();
	}

	@Override
	public int hashCode() {
		return id;
	}

	public double getCommunityCost() {
		return communityCost;
	}

	public void setCommunityCost(double communityCost) {
		this.communityCost = communityCost;
	}
}
