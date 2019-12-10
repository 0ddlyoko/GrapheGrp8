package be.umons.graphegrp8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import be.umons.graphegrp8.file.ReadFile;
import be.umons.graphegrp8.node.Community;
import be.umons.graphegrp8.node.Node;
import be.umons.graphegrp8.node.NodeManager;

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
public class NodeManagerTest {
	private NodeManager nodeManager;

	@Before
	public void setUp() {
		ReadFile readFile = new ReadFile();
		readFile.parse(new File(
				"src" + File.separator + "test" + File.separator + "resources" + File.separator + "File1.txt"));
		nodeManager = new NodeManager(readFile);
		nodeManager.loadNodes();
		nodeManager.loadEdges();
		nodeManager.loadCommunities();
	}

	@Test
	public void testNode() {
		assertEquals(16, nodeManager.getNodes().length);
		// First
		Node n1 = nodeManager.getNode(1);
		Iterator<Node> it = n1.getNeighbors().iterator();
		assertEquals(1, n1.getId());
		assertEquals(0, n1.getCommunity().getId());
		assertEquals(4, n1.getNeighbors().size());
		assertEquals(16, it.next().getId());
		assertEquals(5, it.next().getId());
		assertEquals(9, it.next().getId());
		assertEquals(13, it.next().getId());

		Node n2 = nodeManager.getNode(2);
		it = n2.getNeighbors().iterator();
		assertEquals(2, n2.getId());
		assertEquals(1, n2.getCommunity().getId());
		assertEquals(4, n2.getNeighbors().size());
		assertEquals(6, it.next().getId());
		assertEquals(10, it.next().getId());
		assertEquals(13, it.next().getId());
		assertEquals(14, it.next().getId());

		Node n3 = nodeManager.getNode(3);
		it = n3.getNeighbors().iterator();
		assertEquals(3, n3.getId());
		assertEquals(2, n3.getCommunity().getId());
		assertEquals(4, n3.getNeighbors().size());
		assertEquals(7, it.next().getId());
		assertEquals(11, it.next().getId());
		assertEquals(14, it.next().getId());
		assertEquals(15, it.next().getId());

		Node n4 = nodeManager.getNode(4);
		it = n4.getNeighbors().iterator();
		assertEquals(4, n4.getId());
		assertEquals(3, n4.getCommunity().getId());
		assertEquals(4, n4.getNeighbors().size());
		assertEquals(16, it.next().getId());
		assertEquals(8, it.next().getId());
		assertEquals(12, it.next().getId());
		assertEquals(15, it.next().getId());

		Node n5 = nodeManager.getNode(5);
		it = n5.getNeighbors().iterator();
		assertEquals(5, n5.getId());
		assertEquals(4, n5.getCommunity().getId());
		assertEquals(3, n5.getNeighbors().size());
		assertEquals(1, it.next().getId());
		assertEquals(9, it.next().getId());
		assertEquals(13, it.next().getId());

		Node n6 = nodeManager.getNode(6);
		it = n6.getNeighbors().iterator();
		assertEquals(6, n6.getId());
		assertEquals(5, n6.getCommunity().getId());
		assertEquals(3, n6.getNeighbors().size());
		assertEquals(2, it.next().getId());
		assertEquals(10, it.next().getId());
		assertEquals(14, it.next().getId());

		Node n7 = nodeManager.getNode(7);
		it = n7.getNeighbors().iterator();
		assertEquals(7, n7.getId());
		assertEquals(6, n7.getCommunity().getId());
		assertEquals(3, n7.getNeighbors().size());
		assertEquals(3, it.next().getId());
		assertEquals(11, it.next().getId());
		assertEquals(15, it.next().getId());

		Node n8 = nodeManager.getNode(8);
		it = n8.getNeighbors().iterator();
		assertEquals(8, n8.getId());
		assertEquals(7, n8.getCommunity().getId());
		assertEquals(3, n8.getNeighbors().size());
		assertEquals(16, it.next().getId());
		assertEquals(4, it.next().getId());
		assertEquals(12, it.next().getId());

		Node n9 = nodeManager.getNode(9);
		it = n9.getNeighbors().iterator();
		assertEquals(9, n9.getId());
		assertEquals(8, n9.getCommunity().getId());
		assertEquals(3, n9.getNeighbors().size());
		assertEquals(1, it.next().getId());
		assertEquals(5, it.next().getId());
		assertEquals(13, it.next().getId());

		Node n10 = nodeManager.getNode(10);
		it = n10.getNeighbors().iterator();
		assertEquals(10, n10.getId());
		assertEquals(9, n10.getCommunity().getId());
		assertEquals(3, n10.getNeighbors().size());
		assertEquals(2, it.next().getId());
		assertEquals(6, it.next().getId());
		assertEquals(14, it.next().getId());

		Node n11 = nodeManager.getNode(11);
		it = n11.getNeighbors().iterator();
		assertEquals(11, n11.getId());
		assertEquals(10, n11.getCommunity().getId());
		assertEquals(3, n11.getNeighbors().size());
		assertEquals(3, it.next().getId());
		assertEquals(7, it.next().getId());
		assertEquals(15, it.next().getId());

		Node n12 = nodeManager.getNode(12);
		it = n12.getNeighbors().iterator();
		assertEquals(12, n12.getId());
		assertEquals(11, n12.getCommunity().getId());
		assertEquals(3, n12.getNeighbors().size());
		assertEquals(16, it.next().getId());
		assertEquals(4, it.next().getId());
		assertEquals(8, it.next().getId());

		Node n13 = nodeManager.getNode(13);
		it = n13.getNeighbors().iterator();
		assertEquals(13, n13.getId());
		assertEquals(12, n13.getCommunity().getId());
		assertEquals(4, n13.getNeighbors().size());
		assertEquals(1, it.next().getId());
		assertEquals(2, it.next().getId());
		assertEquals(5, it.next().getId());
		assertEquals(9, it.next().getId());

		Node n14 = nodeManager.getNode(14);
		it = n14.getNeighbors().iterator();
		assertEquals(14, n14.getId());
		assertEquals(13, n14.getCommunity().getId());
		assertEquals(4, n14.getNeighbors().size());
		assertEquals(2, it.next().getId());
		assertEquals(3, it.next().getId());
		assertEquals(6, it.next().getId());
		assertEquals(10, it.next().getId());

		Node n15 = nodeManager.getNode(15);
		it = n15.getNeighbors().iterator();
		assertEquals(15, n15.getId());
		assertEquals(14, n15.getCommunity().getId());
		assertEquals(4, n15.getNeighbors().size());
		assertEquals(3, it.next().getId());
		assertEquals(4, it.next().getId());
		assertEquals(7, it.next().getId());
		assertEquals(11, it.next().getId());

		Node n16 = nodeManager.getNode(16);
		it = n16.getNeighbors().iterator();
		assertEquals(16, n16.getId());
		assertEquals(15, n16.getCommunity().getId());
		assertEquals(4, n16.getNeighbors().size());
		assertEquals(1, it.next().getId());
		assertEquals(4, it.next().getId());
		assertEquals(8, it.next().getId());
		assertEquals(12, it.next().getId());
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
		assertEquals(node1.getCommunity(), new Community(0));
		nodeManager.changeCommunity(node1, node2.getCommunity());
		assertNotEquals(node1.getCommunity(), new Community(0));
		assertEquals(node1.getCommunity(), new Community(1));
		assertEquals(node1.getCommunity(), node2.getCommunity());
		assertEquals(2, node2.getCommunity().size());
		assertEquals(2, node1.getCommunity().getNodes().size());
		// Because we changed the community of Node 1, the old community was empty and
		// was removed from the HashMap
		assertEquals(15, nodeManager.getCommunities().size());
		assertNull(nodeManager.getCommunity(0));
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
		assertNotNull(nodeManager.getCommunity(0));
		// Set old community
		nodeManager.changeCommunity(node1, oldCommunity);
		assertEquals(1, node2.getCommunity().size());
		assertNotEquals(node1.getCommunity(), node2.getCommunity());
		// Change community but set deleteOldCommunity to true
		nodeManager.changeCommunity(node1, node2.getCommunity(), true);
		assertEquals(15, nodeManager.getCommunities().size());
		assertNull(nodeManager.getCommunity(0));
	}
}
