package be.umons.graphegrp8.node;

import java.util.List;

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
public class NodeGroup extends Node {
	private List<Node> nodes;

	public NodeGroup(int id, List<Node> nodes) {
		super(id);
		this.nodes = nodes;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("NodeGroup[id=");
		sb.append(getId()).append(", fakeId=");
		sb.append(getFakeId()).append(", communityId=");
		sb.append(getCommunity().getId()).append(", nodesSize=");
		sb.append(nodes.size()).append(", nodes=[");
		for (Node n : nodes)
			sb.append(n.getId()).append(",");
		// Remove the last ","
		sb.setLength(sb.length() - 1);
		sb.append("]").append(", neighborsSize=");
		sb.append(getNeighbors().size()).append(", neighbors=[");
		for (Node n : getNeighbors())
			sb.append(n.getId()).append(",");
		// Remove the last ","
		sb.setLength(sb.length() - 1);
		sb.append("]]");
		return sb.toString();
	}
}
