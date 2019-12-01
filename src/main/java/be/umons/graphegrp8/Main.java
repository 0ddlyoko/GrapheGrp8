package be.umons.graphegrp8;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.umons.graphegrp8.file.ReadOtherFile;
import be.umons.graphegrp8.node.Community;
import be.umons.graphegrp8.node.Node;
import be.umons.graphegrp8.node.NodeManager;

public class Main {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Main() {
		ReadOtherFile rf = new ReadOtherFile();
		rf.parse(new File("src/main/resources/graphs/files/others/Karate.txt"));
		//ReadOtherFile rf = new ReadOtherFile();
		//rf.parse(new File("src/main/resources/graphs/files/others/File0.txt"));
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

/*		ArrayList<Integer> sommet = new ArrayList<Integer>();
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
*/
//		Community p1 = new Community(1);
//		p1.addNode(new Node(1));
//		p1.addNode(new Node(2));
//		p1.addNode(new Node(3));
//		Community p2 = new Community(2);
//		p2.addNode(new Node(4));
//		p2.addNode(new Node(5));
//		p2.addNode(new Node(6));
//		System.out.printf("P=[P1={1,2,3},P2={4,5,6}\nM(P) =%.3f ", nm.getModularity().resultOfModularity(Arrays.asList(p1, p2)));
		
		// TODO Save the modularity
		LOG.info("Modularity = {}, # of communities = {}, communities: ", nm.getModularity().resultOfModularity(nm.getCommunities()), nm.getCommunities().size());
		for (Community c : nm.getCommunities()) {
			StringBuilder sb = new StringBuilder("{");
			for (Node n : c.getNodes())
				sb.append(n.getId()).append(", ");
			// Remove last ", "
			sb.setLength(sb.length() - 2);
			sb.append("}");
			LOG.info(" - {}: {}", c.getId(), sb.toString());
		}
	}

	public static void main(String[] args) {
		
		new Main();
	}
}
