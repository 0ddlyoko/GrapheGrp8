package be.umons.graphegrp8;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Main() {
		NodeManager nm = new NodeManager(new File("src/main/resources/graphs/files/File1.txt"));
//		ReadFile rf = new ReadFile("src/main/resources/graphs/files/File1.txt");
//		Modularity mod = new Modularity(rf);

		LOG.info("{} edges", nm.getReadFile().getNbEdges());
		LOG.info("{} vertices", nm.getReadFile().getNbVertices());

		LOG.info("HeadTab: ");
		for (Integer elt : nm.getReadFile().getHeadTab())
			System.out.print(elt + " ");
		System.out.println();
		LOG.info("SuccTab: ");
		for (Integer elt : nm.getReadFile().getSuccTab())
			System.out.print(elt + " ");
		System.out.println();
		LOG.info("Table de dégré: ");
		for (Integer elt : nm.getReadFile().buildTabDegree())
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
