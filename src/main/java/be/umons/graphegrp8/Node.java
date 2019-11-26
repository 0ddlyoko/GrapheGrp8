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

	public void setCommunity(Community community) {
		this.community = community;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
