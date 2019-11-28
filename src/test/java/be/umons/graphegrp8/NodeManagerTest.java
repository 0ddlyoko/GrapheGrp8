package be.umons.graphegrp8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class NodeManagerTest {
	private NodeManager nodeManager;

	@Before
	public void setUp() {
		nodeManager = new NodeManager(new File(
				"src" + File.separator + "test" + File.separator + "resources" + File.separator + "File1.txt"));
		nodeManager.loadNodes();
		nodeManager.loadEdges();
	}

	@Test
	public void testNode() {
		assertEquals(16, nodeManager.getNodes().length);
		// First
		Node n1 = nodeManager.getNode(1);
		assertEquals(1, n1.getId());
		assertEquals(1, n1.getCommunity().getId());
		assertEquals(4, n1.getNeighbors().size());
		assertEquals(5, n1.getNeighbors().get(0).getId());
		assertEquals(9, n1.getNeighbors().get(1).getId());
		assertEquals(13, n1.getNeighbors().get(2).getId());
		assertEquals(16, n1.getNeighbors().get(3).getId());

		Node n2 = nodeManager.getNode(2);
		assertEquals(2, n2.getId());
		assertEquals(2, n2.getCommunity().getId());
		assertEquals(4, n2.getNeighbors().size());
		assertEquals(13, n2.getNeighbors().get(0).getId());
		assertEquals(6, n2.getNeighbors().get(1).getId());
		assertEquals(10, n2.getNeighbors().get(2).getId());
		assertEquals(14, n2.getNeighbors().get(3).getId());

		Node n3 = nodeManager.getNode(3);
		assertEquals(3, n3.getId());
		assertEquals(3, n3.getCommunity().getId());
		assertEquals(4, n3.getNeighbors().size());
		assertEquals(14, n3.getNeighbors().get(0).getId());
		assertEquals(7, n3.getNeighbors().get(1).getId());
		assertEquals(11, n3.getNeighbors().get(2).getId());
		assertEquals(15, n3.getNeighbors().get(3).getId());

		Node n4 = nodeManager.getNode(4);
		assertEquals(4, n4.getId());
		assertEquals(4, n4.getCommunity().getId());
		assertEquals(4, n4.getNeighbors().size());
		assertEquals(15, n4.getNeighbors().get(0).getId());
		assertEquals(8, n4.getNeighbors().get(1).getId());
		assertEquals(12, n4.getNeighbors().get(2).getId());
		assertEquals(16, n4.getNeighbors().get(3).getId());

		Node n5 = nodeManager.getNode(5);
		assertEquals(5, n5.getId());
		assertEquals(5, n5.getCommunity().getId());
		assertEquals(3, n5.getNeighbors().size());
		assertEquals(1, n5.getNeighbors().get(0).getId());
		assertEquals(9, n5.getNeighbors().get(1).getId());
		assertEquals(13, n5.getNeighbors().get(2).getId());

		Node n6 = nodeManager.getNode(6);
		assertEquals(6, n6.getId());
		assertEquals(6, n6.getCommunity().getId());
		assertEquals(3, n6.getNeighbors().size());
		assertEquals(2, n6.getNeighbors().get(0).getId());
		assertEquals(10, n6.getNeighbors().get(1).getId());
		assertEquals(14, n6.getNeighbors().get(2).getId());

		Node n7 = nodeManager.getNode(7);
		assertEquals(7, n7.getId());
		assertEquals(7, n7.getCommunity().getId());
		assertEquals(3, n7.getNeighbors().size());
		assertEquals(3, n7.getNeighbors().get(0).getId());
		assertEquals(11, n7.getNeighbors().get(1).getId());
		assertEquals(15, n7.getNeighbors().get(2).getId());

		Node n8 = nodeManager.getNode(8);
		assertEquals(8, n8.getId());
		assertEquals(8, n8.getCommunity().getId());
		assertEquals(3, n8.getNeighbors().size());
		assertEquals(4, n8.getNeighbors().get(0).getId());
		assertEquals(12, n8.getNeighbors().get(1).getId());
		assertEquals(16, n8.getNeighbors().get(2).getId());

		Node n9 = nodeManager.getNode(9);
		assertEquals(9, n9.getId());
		assertEquals(9, n9.getCommunity().getId());
		assertEquals(3, n9.getNeighbors().size());
		assertEquals(1, n9.getNeighbors().get(0).getId());
		assertEquals(5, n9.getNeighbors().get(1).getId());
		assertEquals(13, n9.getNeighbors().get(2).getId());

		Node n10 = nodeManager.getNode(10);
		assertEquals(10, n10.getId());
		assertEquals(10, n10.getCommunity().getId());
		assertEquals(3, n10.getNeighbors().size());
		assertEquals(2, n10.getNeighbors().get(0).getId());
		assertEquals(6, n10.getNeighbors().get(1).getId());
		assertEquals(14, n10.getNeighbors().get(2).getId());

		Node n11 = nodeManager.getNode(11);
		assertEquals(11, n11.getId());
		assertEquals(11, n11.getCommunity().getId());
		assertEquals(3, n11.getNeighbors().size());
		assertEquals(3, n11.getNeighbors().get(0).getId());
		assertEquals(7, n11.getNeighbors().get(1).getId());
		assertEquals(15, n11.getNeighbors().get(2).getId());

		Node n12 = nodeManager.getNode(12);
		assertEquals(12, n12.getId());
		assertEquals(12, n12.getCommunity().getId());
		assertEquals(3, n12.getNeighbors().size());
		assertEquals(4, n12.getNeighbors().get(0).getId());
		assertEquals(8, n12.getNeighbors().get(1).getId());
		assertEquals(16, n12.getNeighbors().get(2).getId());

		Node n13 = nodeManager.getNode(13);
		assertEquals(13, n13.getId());
		assertEquals(13, n13.getCommunity().getId());
		assertEquals(4, n13.getNeighbors().size());
		assertEquals(1, n13.getNeighbors().get(0).getId());
		assertEquals(5, n13.getNeighbors().get(1).getId());
		assertEquals(9, n13.getNeighbors().get(2).getId());
		assertEquals(2, n13.getNeighbors().get(3).getId());

		Node n14 = nodeManager.getNode(14);
		assertEquals(14, n14.getId());
		assertEquals(14, n14.getCommunity().getId());
		assertEquals(4, n14.getNeighbors().size());
		assertEquals(2, n14.getNeighbors().get(0).getId());
		assertEquals(6, n14.getNeighbors().get(1).getId());
		assertEquals(10, n14.getNeighbors().get(2).getId());
		assertEquals(3, n14.getNeighbors().get(3).getId());

		Node n15 = nodeManager.getNode(15);
		assertEquals(15, n15.getId());
		assertEquals(15, n15.getCommunity().getId());
		assertEquals(4, n15.getNeighbors().size());
		assertEquals(3, n15.getNeighbors().get(0).getId());
		assertEquals(7, n15.getNeighbors().get(1).getId());
		assertEquals(11, n15.getNeighbors().get(2).getId());
		assertEquals(4, n15.getNeighbors().get(3).getId());

		Node n16 = nodeManager.getNode(16);
		assertEquals(16, n16.getId());
		assertEquals(16, n16.getCommunity().getId());
		assertEquals(4, n16.getNeighbors().size());
		assertEquals(4, n16.getNeighbors().get(0).getId());
		assertEquals(8, n16.getNeighbors().get(1).getId());
		assertEquals(12, n16.getNeighbors().get(2).getId());
		assertEquals(1, n16.getNeighbors().get(3).getId());
	}

	@Test
	public void testCommunities() {
		assertEquals(16, nodeManager.getCommunities().size());
		Node node1 = nodeManager.getNode(1);
		Node node2 = nodeManager.getNode(2);
		assertEquals(1, node1.getCommunity().getNodes().size());
		assertEquals(1, node2.getCommunity().getNodes().size());
		assertNotEquals(node1.getCommunity(), node2.getCommunity());
		// Same hashCode
		assertEquals(node1.getCommunity(), new Community(1));
		nodeManager.changeCommunity(node1, node2.getCommunity());
		assertNotEquals(node1.getCommunity(), new Community(1));
		assertEquals(node1.getCommunity(), new Community(2));
		assertEquals(node1.getCommunity(), node2.getCommunity());
		assertEquals(2, node2.getCommunity().size());
		assertEquals(2, node1.getCommunity().getNodes().size());
		// Because we changed the community of Node 1, the old community was empty and
		// was removed from the HashMap
		assertEquals(15, nodeManager.getCommunities().size());
		assertNull(nodeManager.getCommunity(1));
	}

	@Test
	public void testChangeCommunity() {
		assertEquals(16, nodeManager.getCommunities().size());
		Node node1 = nodeManager.getNode(1);
		Community oldCommunity = node1.getCommunity();
		Node node2 = nodeManager.getNode(2);
		// Change community
		nodeManager.changeCommunity(node1, node2.getCommunity(), false);
		assertEquals(16, nodeManager.getCommunities().size());
		assertNotNull(nodeManager.getCommunity(1));
		// Set old community
		nodeManager.changeCommunity(node1, oldCommunity);
		assertEquals(1, node2.getCommunity().size());
		assertNotEquals(node1.getCommunity(), node2.getCommunity());
		// Change community but set deleteOldCommunity to true
		nodeManager.changeCommunity(node1, node2.getCommunity(), true);
		assertEquals(15, nodeManager.getCommunities().size());
		assertNull(nodeManager.getCommunity(1));
	}
}
