package be.umons.graphegrp8.node;

import java.util.ArrayList;
import java.util.List;

public class Node {
	/**
	 * The id of the node
	 */
	private int id;
	/**
	 * A fake id
	 */
	private int fakeId;
	/**
	 * A list of neighboring nodes
	 */
	private List<Node> neighbors;
	/**
	 * The community where is this node
	 */
	private Community community;

	public Node(int id) {
		this.id = id;
		this.fakeId = id;
		this.neighbors = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public int getFakeId() {
		return fakeId;
	}

	public void setFakeId(int fakeId) {
		this.fakeId = fakeId;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Node[id=");
		sb.append(id).append(", fakeId=");
		sb.append(fakeId).append(", communityId=");
		sb.append(community.getId()).append(", neighborsNumber=");
		sb.append(neighbors.size()).append(", neighbors=[");
		for (Node n : neighbors)
			sb.append(n.getId()).append(",");
		// Remove the last ","
		sb.setLength(sb.length() - 1);
		sb.append("]]");
		return sb.toString();
	}
}
