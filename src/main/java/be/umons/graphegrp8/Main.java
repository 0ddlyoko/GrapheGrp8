package be.umons.graphegrp8;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.umons.graphegrp8.file.ReadOtherFile;
import be.umons.graphegrp8.node.Node;
import be.umons.graphegrp8.node.NodeManager;

public class Main {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Main() {
//		ReadFile rf = new ReadFile();
//		rf.parse(new File("src/main/resources/graphs/files/File1.txt"));
		ReadOtherFile rf = new ReadOtherFile();
		rf.parse(new File("src/main/resources/graphs/files/others/File1.txt"));
		NodeManager nm = new NodeManager(rf);
		nm.load();
		nm.start();
		for (int i = 0; i < nm.getNumberOfNodes(); i++) {
			Node n = nm.getFakeNode(i + 1);
			LOG.info("- " + n);
		}
//		ReadFile rf = new ReadFile("src/main/resources/graphs/files/File1.txt");
//		Modularity mod = new Modularity(rf);

		LOG.info("{} edges", nm.getFileParser().getNbEdges());
		LOG.info("{} vertices", nm.getFileParser().getNbVertices());

		LOG.info("HeadTab: ");
		for (Integer elt : nm.getFileParser().getHeadTab())
			System.out.print(elt + " ");
		System.out.println();
		LOG.info("SuccTab: ");
		for (Integer elt : nm.getFileParser().getSuccTab())
			System.out.print(elt + " ");
		System.out.println();
		LOG.info("Table de dégré: ");
		for (Integer elt : nm.getFileParser().getDegTab())
			System.out.print(elt + " ");
		System.out.println();
		LOG.info("Matrice adjacente initiale: ");
		for (int[] elt : nm.getModularity().getMatrixAdj()) {
			for (Integer e : elt)
				System.out.print(e + " ");
			System.out.println();
		}
		System.out.println();
		LOG.info("Matrice de probabilité initiale: ");
		for (double[] elt : nm.getModularity().getMatrixProb()) {
			for (double e : elt)
				System.out.printf("%.2f ", e);
			System.out.println();
		}
		System.out.println();

		ArrayList<Integer> sommet = new ArrayList<Integer>();
		sommet.add(16);
		sommet.add(4);
		sommet.add(15);
		sommet.add(12);
		sommet.sort(null);

		LOG.info("Matrice pour les sommets selectionnés: ");
		for (int[] elt : nm.getModularity().verticeToMatrixAdj(sommet)) {
			for (Integer e : elt)
				System.out.print(e + " ");
			System.out.println();
		}
		System.out.println();
		System.out.println();
		for (double[] elt : nm.getModularity().verticeToMatrixProb(sommet)) {
			for (double e : elt)
				System.out.printf("%.2f ", e);
			System.out.println();
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}
