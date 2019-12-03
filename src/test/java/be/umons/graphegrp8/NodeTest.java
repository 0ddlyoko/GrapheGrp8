package be.umons.graphegrp8;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import be.umons.graphegrp8.node.Node;

public class NodeTest {
	private static Node[] nodes;

	@BeforeClass
	public static void setUp() {
		nodes = new Node[5];
		for (int i = 0; i < 5; i++)
			nodes[i] = new Node(i + 1);
		// 1 - 2
		nodes[0].addNeighbors(nodes[1]);
		nodes[1].addNeighbors(nodes[0]);
		// 1 - 4
		nodes[0].addNeighbors(nodes[3]);
		nodes[3].addNeighbors(nodes[0]);
		// 1 - 5
		nodes[0].addNeighbors(nodes[4]);
		nodes[4].addNeighbors(nodes[0]);
		// 2 - 3
		nodes[1].addNeighbors(nodes[2]);
		nodes[2].addNeighbors(nodes[1]);
		// 3 - 4
		nodes[2].addNeighbors(nodes[3]);
		nodes[3].addNeighbors(nodes[2]);
		// 3 - 5
		nodes[2].addNeighbors(nodes[4]);
		nodes[4].addNeighbors(nodes[2]);
		// 4 - 5
		nodes[3].addNeighbors(nodes[4]);
		nodes[4].addNeighbors(nodes[3]);
	}

	@Test
	public void testNode() {
		assertEquals(nodes.length, 5);
		for (int i = 0; i < 5; i++) {
			assertEquals(i + 1, nodes[i].getId());
			assertEquals(i + 1, nodes[i].hashCode());
		}
		// Node 1
		assertEquals(3, nodes[0].getNeighbors().size());
		Iterator<Node> it = nodes[0].getNeighbors().iterator();
		assertEquals(nodes[1], it.next());
		assertEquals(nodes[3], it.next());
		assertEquals(nodes[4], it.next());
		// Node 2
		it = nodes[1].getNeighbors().iterator();
		assertEquals(2, nodes[1].getNeighbors().size());
		assertEquals(nodes[0], it.next());
		assertEquals(nodes[2], it.next());
		// Node 3
		it = nodes[2].getNeighbors().iterator();
		assertEquals(3, nodes[2].getNeighbors().size());
		assertEquals(nodes[1], it.next());
		assertEquals(nodes[3], it.next());
		assertEquals(nodes[4], it.next());
		// Node 4
		it = nodes[3].getNeighbors().iterator();
		assertEquals(3, nodes[3].getNeighbors().size());
		assertEquals(nodes[0], it.next());
		assertEquals(nodes[2], it.next());
		assertEquals(nodes[4], it.next());
		// Node 5
		it = nodes[4].getNeighbors().iterator();
		assertEquals(3, nodes[4].getNeighbors().size());
		assertEquals(nodes[0], it.next());
		assertEquals(nodes[2], it.next());
		assertEquals(nodes[3], it.next());

		// HashCode
		assertEquals(1, nodes[0].hashCode());
		assertEquals(2, nodes[1].hashCode());
		assertEquals(3, nodes[2].hashCode());
		assertEquals(4, nodes[3].hashCode());
		assertEquals(5, nodes[4].hashCode());
	}
}
