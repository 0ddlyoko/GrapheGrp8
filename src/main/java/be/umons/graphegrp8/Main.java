package be.umons.graphegrp8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.umons.graphegrp8.file.FileParser;
import be.umons.graphegrp8.file.ReadFile;
import be.umons.graphegrp8.file.ReadOtherFile;
import be.umons.graphegrp8.node.Community;
import be.umons.graphegrp8.node.Node;
import be.umons.graphegrp8.node.NodeGroup;
import be.umons.graphegrp8.node.NodeManager;

public class Main {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private int i = 0;

	public Main(String[] args) {
		if (args.length <= 1) {
			LOG.error("Syntax: java -jar <jarName> <input> <count> <output> [other]");
			System.exit(0);
		}
		String strInput = args[0];
		Integer count = 0;
		try {
			count = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			LOG.error("Please enter a correct number");
			System.exit(0);
		}
		String strOutput = args[2];
		File output = new File(strOutput);
		if (output.isDirectory()) {
			LOG.error("Output is a directory !");
			System.exit(0);
		}
		if (!output.exists()) {
			try {
				if (!output.createNewFile()) {
					LOG.error("Cannot create output file !");
					System.exit(0);
				}
			} catch (IOException ex) {
				LOG.error("Cannot create output file !", ex);
				System.exit(0);
			}
		}
		FileParser fp = (args.length == 4 && "other".equalsIgnoreCase(args[3])) ? new ReadOtherFile() : new ReadFile();

		LOG.info("");
		LOG.info("Projet de Graphe développé par Giacomello Nathan, Coyez François et Kamta Boris");
		LOG.info("Implémentation de l'algorithme de Louvain");
		LOG.info("");

		LOG.info("Parsing file ...");
		long before = System.currentTimeMillis();
		fp.parse(new File(strInput));
		LOG.info("Done in {} ms", (System.currentTimeMillis() - before));
		// ReadOtherFile rf = new ReadOtherFile();
		// rf.parse(new File("src/main/resources/graphs/files/others/File0.txt"));
		double bestModularity = -1;

		long total = 0;
		NodeManager nm = new NodeManager(fp);
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
			total += (after - before);
			if (modularity > bestModularity) {
				bestModularity = modularity;
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
		LOG.info("Moyenne: {} ms", (total / count));
		LOG.info("TEST SORTIE OUTPUT DEMANDEE");

		LOG.info("{}", bestModularity);
		LOG.info("{}", nm.getCommunities().size());
		for (Community c : nm.getCommunities()) {
			StringBuilder sb2 = new StringBuilder();
			int countVertige = 0;
			for (Node n : c.getNodes()) {
				if (n instanceof NodeGroup) {
					for (Node n2 : ((NodeGroup) n).getNodes()) {
						countVertige++;
						sb2.append(n2.getId()).append(" ");
					}
				} else {
					countVertige++;
					sb2.append(n.getId()).append(" ");
				}
			}
			LOG.info("{}", countVertige);
			LOG.info("{}", sb2.toString());
		}

		try {
			BufferedWriter fileOut = new BufferedWriter(new FileWriter(output));
			fileOut.write("" + bestModularity);
			fileOut.newLine();
			fileOut.write("" + nm.getCommunities().size());
			fileOut.newLine();
			for (Community c : nm.getCommunities()) {
				StringBuilder sb = new StringBuilder();
				int countVertige = 0;
				for (Node n : c.getNodes()) {
					if (n instanceof NodeGroup) {
						for (Node n2 : ((NodeGroup) n).getNodes()) {
							countVertige++;
							sb.append(n2.getId()).append(" ");
						}
					} else {
						countVertige++;
						sb.append(n.getId()).append(" ");
					}
				}
				fileOut.write("" + countVertige);
				fileOut.newLine();
				fileOut.write(sb.toString());
				fileOut.newLine();
			}
			LOG.info("FICHIER RESULTAT FAIT");
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LOG.info("Stopped");
		t.interrupt();
	}

	public static void main(String[] args) {
		new Main(args);
	}
}
