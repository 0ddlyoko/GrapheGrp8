package be.umons.graphegrp8.node;

import java.util.HashSet;
import java.util.Set;

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
	 * A set of neighboring nodes. We use Set and not List to prevent adding same
	 * node
	 */
	private Set<Node> neighbors;
	/**
	 * The community where is this node
	 */
	private Community community;

	public Node(int id) {
		this.id = id;
		this.fakeId = id;
		this.neighbors = new HashSet<>();
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

	public Set<Node> getNeighbors() {
		return neighbors;
	}

	/**
	 * Add neighbors
	 * 
	 * @param n The node that is linked to this node
	 */
	public void addNeighbors(Node node) {
		addNeighbors(node, 1);
	}

	public void addNeighbors(Node node, int cost) {
//		neighbors.add(new NodeCost(node, cost));
		neighbors.add(node);
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
