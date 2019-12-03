package be.umons.graphegrp8.node;

import java.util.List;

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
		sb.append(getCommunity().getId()).append(", neighborsNumber=");
		sb.append(getNeighbors().size()).append(", neighbors=[");
		for (Node n : getNeighbors())
			sb.append(n.getId()).append(",");
		// Remove the last ","
		sb.setLength(sb.length() - 1);
		sb.append("]]");
		return sb.toString();
	}
}
