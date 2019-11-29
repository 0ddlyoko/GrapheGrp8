package be.umons.graphegrp8.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 
 * class to read a file and initialize the head table, the successor table and
 * the degree table
 */
public class ReadFile implements FileParser {
	protected int[] headTab;
	protected int[] succTab;
	protected int[] degTab;

	protected int nbEdges;
	protected int nbVertices;

	@Override
	public void parse(File file) {
		try {
			Pattern space = Pattern.compile(" ");

			BufferedReader in = new BufferedReader(new FileReader(file));
			nbEdges = Integer.parseInt(space.split(in.readLine().trim())[0]);
			nbVertices = Integer.parseInt(space.split(in.readLine().trim())[0]);
			headTab = StringTabToIntTab(space.split(in.readLine().trim()));
			succTab = StringTabToIntTab(space.split(in.readLine().trim()));
			degTab = buildTabDegree();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Convert a String Table to an Integer Table
	 * 
	 * @param tab table of String
	 * @return table of integers
	 */
	protected int[] StringTabToIntTab(String[] tab) {
		int[] res = new int[tab.length];
		int i = 0;
		for (String elt : tab) {
			res[i] = Integer.parseInt(elt);
			i++;
		}
		return res;
	}

	/**
	 * method of constructing the degree table
	 * 
	 * @return table of sommets degree
	 */
	public int[] buildTabDegree() {
		int[] tab = new int[nbVertices];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = headTab[i + 1] - headTab[i];
		}
		return tab;
	}

	@Override
	public int[] getHeadTab() {
		return headTab;
	}

	@Override
	public int getNbEdges() {
		return nbEdges;
	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public int[] getSuccTab() {
		return succTab;
	}

	@Override
	public int[] getDegTab() {
		return degTab;
	}
}