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
//		rf.parse(new File("src/main/resources/graphs/files/File5.txt"));
		rf.parse(new File("src/main/resources/graphs/files/others/TheInternet.txt"));
		LOG.info("Done in {} ms", (System.currentTimeMillis() - before));
		// ReadOtherFile rf = new ReadOtherFile();
		// rf.parse(new File("src/main/resources/graphs/files/others/File0.txt"));
		int count = 10;
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
//		Arrays.asList(48, 16, 33, 65, 3, 101, 7, 40, 107, 61, 14).forEach(nb -> {
//			p1.addNode(new Node(nb));
//		});
//		Community p2 = new Community(2);
//		Arrays.asList(34, 2, 38, 104, 106, 90, 26, 46, 110).forEach(nb -> {
//			p2.addNode(new Node(nb));
//		});
//		Community p3 = new Community(3);
//		Arrays.asList(50, 115, 84, 68, 54, 89, 74, 47, 111).forEach(nb -> {
//			p3.addNode(new Node(nb));
//		});
//		Community p4 = new Community(4);
//		Arrays.asList(112, 52, 69, 22, 23, 8, 9, 109, 78, 79).forEach(nb -> {
//			p4.addNode(new Node(nb));
//		});
//		Community p5 = new Community(5);
//		Arrays.asList(113, 49, 67, 87, 58, 76, 92, 93, 45).forEach(nb -> {
//			p5.addNode(new Node(nb));
//		});
//		Community p6 = new Community(6);
//		Arrays.asList(1, 5, 70, 105, 42, 10, 12, 17, 51, 24, 25, 91, 29, 94).forEach(nb -> {
//			p6.addNode(new Node(nb));
//		});
//		Community p7 = new Community(6);
//		Arrays.asList(32, 35, 100, 37, 39, 72, 43, 44, 13, 15, 19, 86, 55, 27, 62).forEach(nb -> {
//			p7.addNode(new Node(nb));
//		});
//		Community p8 = new Community(6);
//		Arrays.asList(80, 81, 83, 20, 36, 102, 56, 30, 31, 95).forEach(nb -> {
//			p8.addNode(new Node(nb));
//		});
//		Community p9 = new Community(6);
//		Arrays.asList(82, 99, 4, 53, 85, 6, 103, 41, 73, 11, 75, 108).forEach(nb -> {
//			p9.addNode(new Node(nb));
//		});
//		Community p10 = new Community(6);
//		Arrays.asList(96, 64, 97, 66, 98, 71, 77, 114, 18, 21, 88, 57, 59, 28, 60, 63).forEach(nb -> {
//			p10.addNode(new Node(nb));
//		});
//		LOG.info("M(P) = {}", nm.getModularity().resultOfModularity(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)));
		// 0.5783420133113694
//		System.out.printf("M(P) =%.3f ", nm.getModularity().resultOfModularity(Arrays.asList(p1, p2, p3, p4, p5, p6)));
	}

	public static void main(String[] args) {

		new Main();
	}
}
