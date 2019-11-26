package be.umons.graphegrp8;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int id;
	private List<Node> neighbors;
	private Community community;

	public Node(int id) {
		this.id = id;
		this.neighbors = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public List<Node> getNeighbors() {
		return neighbors;
	}

	/**
	 * Add neighbors
	 * 
	 * @param n The node that is linked to this node
	 */
	public void addNeighbors(Node n) {
		neighbors.add(n);
	}

	public Community getCommunity() {
		return community;
	}

	/**
	 * Set the community of this node.<br />
	 * !!!! DO NOT USE THIS METHOD, USE {@link Community#addNode(Node)}
	 * 
	 * @param community
	 */
	public void setCommunity(Community community) {
		this.community = community;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		return ((Node) obj).hashCode() == hashCode();
	}

	@Override
	public int hashCode() {
		return id;
	}
}
