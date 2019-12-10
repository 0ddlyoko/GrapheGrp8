package be.umons.graphegrp8.node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
public class Community {
	private int id;
	private Map<Integer, Node> nodes;
	private double communityCost;

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

	public void clear() {
		nodes.clear();
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
