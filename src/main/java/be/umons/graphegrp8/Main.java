package be.umons.graphegrp8;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.umons.graphegrp8.file.ReadFile;
import be.umons.graphegrp8.file.ReadOtherFile;
import be.umons.graphegrp8.node.Community;
import be.umons.graphegrp8.node.Node;
import be.umons.graphegrp8.node.NodeGroup;
import be.umons.graphegrp8.node.NodeManager;

public class Main {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private int i = 0;

	public Main() {
		ReadOtherFile rf = new ReadOtherFile();
//		ReadFile rf = new ReadFile();
		LOG.info("Parsing file ...");
		long before = System.currentTimeMillis();
//		rf.parse(new File("src/main/resources/graphs/files/File2.txt"));
		rf.parse(new File("src/main/resources/graphs/files/others/Test.txt"));
		LOG.info("Done in {} ms", (System.currentTimeMillis() - before));
		// ReadOtherFile rf = new ReadOtherFile();
		// rf.parse(new File("src/main/resources/graphs/files/others/File0.txt"));
		int count = 10000;
		double bestModularity = -1;

		NodeManager nm = new NodeManager(rf);
		Thread t = new Thread(() -> {
			while (!Thread.interrupted()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					LOG.info("Interrupted");
					break;
				}
				LOG.info("Test #{}", i);
			}
		});
		t.start();
		for (; i < count; i++) {
			nm.load();
			before = System.currentTimeMillis();
			nm.start();
			long after = System.currentTimeMillis();
			double modularity = nm.getModularity().resultOfModularity(nm.getCommunities());
			if (modularity > bestModularity) {
				bestModularity = modularity;
				// TODO Save the modularity
				LOG.info("Modularity = {}, # of communities = {}, communities: ", bestModularity,
						nm.getCommunities().size());
				for (Community c : nm.getCommunities()) {
					StringBuilder sb = new StringBuilder("{");
					for (Node n : c.getNodes())
						if (n instanceof NodeGroup)
							for (Node n2 : ((NodeGroup) n).getNodes())
								sb.append(n2.getId()).append(", ");
						else
							sb.append(n.getId()).append(", ");
					// Remove last ", "
					sb.setLength(sb.length() - 2);
					sb.append("}");
					LOG.info(" - {}: {}", c.getId(), sb.toString());
				}
				LOG.info("Test {}: Got {} with {} ameliorations, Done in {} ms", (i + 1), modularity, nm.getRetry(),
						(after - before));
			}
		}
		LOG.info("Stopped");
		t.interrupt();

//		for (int i = 0; i < nm.getNumberOfNodes(); i++) {
//			Node n = nm.getFakeNode(i + 1);
//			LOG.info("- " + n);
//		}
//		ReadFile rf = new ReadFile("src/main/resources/graphs/files/File1.txt");
//		Modularity mod = new Modularity(rf);

//		LOG.info("{} edges", nm.getFileParser().getNbEdges());
//		LOG.info("{} vertices", nm.getFileParser().getNbVertices());
//
//		LOG.info("HeadTab: ");
//		for (Integer elt : nm.getFileParser().getHeadTab())
//			System.out.print(elt + " ");
//		System.out.println();
//		LOG.info("SuccTab: ");
//		for (Integer elt : nm.getFileParser().getSuccTab())
//			System.out.print(elt + " ");
//		System.out.println();
//		LOG.info("Table de dégré: ");
//		for (Integer elt : nm.getFileParser().getDegTab())
//			System.out.print(elt + " ");
//		System.out.println();
//		LOG.info("Matrice adjacente initiale: ");
//		for (int[] elt : nm.getModularity().getMatrixAdj()) {
//			for (Integer e : elt)
//				System.out.print(e + " ");
//			System.out.println();
//		}
//		System.out.println();
//		LOG.info("Matrice de probabilité initiale: ");
//		for (double[] elt : nm.getModularity().getMatrixProb()) {
//			for (double e : elt)
//				System.out.printf("%.2f ", e);
//			System.out.println();
//		}
//		System.out.println();

		/*
		 * ArrayList<Integer> sommet = new ArrayList<Integer>(); sommet.add(16);
		 * sommet.add(4); sommet.add(15); sommet.add(12); sommet.sort(null);
		 * 
		 * LOG.info("Matrice pour les sommets selectionnés: "); for (int[] elt :
		 * nm.getModularity().verticeToMatrixAdj(sommet)) { for (Integer e : elt)
		 * System.out.print(e + " "); System.out.println(); } System.out.println();
		 * System.out.println(); for (double[] elt :
		 * nm.getModularity().verticeToMatrixProb(sommet)) { for (double e : elt)
		 * System.out.printf("%.2f ", e); System.out.println(); }
		 */
//		Community p1 = new Community(1);
//		p1.addNode(new Node(72));
//		p1.addNode(new Node(13));
//		p1.addNode(new Node(15));
//		p1.addNode(new Node(80));
//		p1.addNode(new Node(81));
//		p1.addNode(new Node(19));
//		p1.addNode(new Node(83));
//		p1.addNode(new Node(20));
//		p1.addNode(new Node(86));
//		p1.addNode(new Node(27));
//		p1.addNode(new Node(30));
//		p1.addNode(new Node(95));
//		p1.addNode(new Node(31));
//		p1.addNode(new Node(32));
//		p1.addNode(new Node(35));
//		p1.addNode(new Node(100));
//		p1.addNode(new Node(36));
//		p1.addNode(new Node(37));
//		p1.addNode(new Node(102));
//		p1.addNode(new Node(39));
//		p1.addNode(new Node(43));
//		p1.addNode(new Node(44));
//		p1.addNode(new Node(55));
//		p1.addNode(new Node(56));
//		p1.addNode(new Node(62));
//		Community p2 = new Community(2);
//		p2.addNode(new Node(48));
//		p2.addNode(new Node(16));
//		p2.addNode(new Node(65));
//		p2.addNode(new Node(33));
//		p2.addNode(new Node(3));
//		p2.addNode(new Node(101));
//		p2.addNode(new Node(7));
//		p2.addNode(new Node(40));
//		p2.addNode(new Node(107));
//		p2.addNode(new Node(61));
//		p2.addNode(new Node(14));
//		Community p3 = new Community(3);
//		p3.addNode(new Node(50));
//		p3.addNode(new Node(115));
//		p3.addNode(new Node(84));
//		p3.addNode(new Node(68));
//		p3.addNode(new Node(54));
//		p3.addNode(new Node(89));
//		p3.addNode(new Node(74));
//		p3.addNode(new Node(59));
//		p3.addNode(new Node(111));
//		p3.addNode(new Node(47));
//		Community p4 = new Community(4);
//		p4.addNode(new Node(1));
//		p4.addNode(new Node(69));
//		p4.addNode(new Node(5));
//		p4.addNode(new Node(70));
//		p4.addNode(new Node(8));
//		p4.addNode(new Node(9));
//		p4.addNode(new Node(10));
//		p4.addNode(new Node(12));
//		p4.addNode(new Node(78));
//		p4.addNode(new Node(79));
//		p4.addNode(new Node(17));
//		p4.addNode(new Node(22));
//		p4.addNode(new Node(23));
//		p4.addNode(new Node(24));
//		p4.addNode(new Node(25));
//		p4.addNode(new Node(91));
//		p4.addNode(new Node(29));
//		p4.addNode(new Node(94));
//		p4.addNode(new Node(105));
//		p4.addNode(new Node(42));
//		p4.addNode(new Node(109));
//		p4.addNode(new Node(112));
//		p4.addNode(new Node(51));
//		p4.addNode(new Node(52));
//		Community p5 = new Community(5);
//		p5.addNode(new Node(67));
//		p5.addNode(new Node(4));
//		p5.addNode(new Node(6));
//		p5.addNode(new Node(73));
//		p5.addNode(new Node(11));
//		p5.addNode(new Node(75));
//		p5.addNode(new Node(76));
//		p5.addNode(new Node(82));
//		p5.addNode(new Node(85));
//		p5.addNode(new Node(87));
//		p5.addNode(new Node(92));
//		p5.addNode(new Node(93));
//		p5.addNode(new Node(98));
//		p5.addNode(new Node(99));
//		p5.addNode(new Node(103));
//		p5.addNode(new Node(41));
//		p5.addNode(new Node(108));
//		p5.addNode(new Node(45));
//		p5.addNode(new Node(49));
//		p5.addNode(new Node(113));
//		p5.addNode(new Node(53));
//		p5.addNode(new Node(58));
//		Community p6 = new Community(6);
//		p6.addNode(new Node(2));
//		p6.addNode(new Node(18));
//		p6.addNode(new Node(21));
//		p6.addNode(new Node(26));
//		p6.addNode(new Node(28));
//		p6.addNode(new Node(34));
//		p6.addNode(new Node(38));
//		p6.addNode(new Node(46));
//		p6.addNode(new Node(57));
//		p6.addNode(new Node(60));
//		p6.addNode(new Node(63));
//		p6.addNode(new Node(64));
//		p6.addNode(new Node(66));
//		p6.addNode(new Node(71));
//		p6.addNode(new Node(77));
//		p6.addNode(new Node(88));
//		p6.addNode(new Node(90));
//		p6.addNode(new Node(96));
//		p6.addNode(new Node(97));
//		p6.addNode(new Node(104));
//		p6.addNode(new Node(106));
//		p6.addNode(new Node(110));
//		p6.addNode(new Node(114));
//		LOG.info("M(P) = {}", nm.getModularity().resultOfModularity(Arrays.asList(p1, p2, p3, p4, p6, p5)));
		// 0.5783420133113694
//		System.out.printf("M(P) =%.3f ", nm.getModularity().resultOfModularity(Arrays.asList(p1, p2, p3, p4, p5, p6)));
	}

	public static void main(String[] args) {

		new Main();
	}
}
