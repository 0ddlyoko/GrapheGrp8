package graphe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 *class to read a file and initialize the successor table and the head table
 */
public class ReadFile {
	private int[] headTab;
	private int[] succTab;
	private int nbEdges;
	private int nbVertices;
	
	/**
	 * 
	 * @param filePath String
	 */
	ReadFile(String filePath){
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            nbEdges = Integer.parseInt(in.readLine().split(" ")[0]);
            nbVertices = Integer.parseInt(in.readLine().split(" ")[0]);
            headTab = StringTabToIntTab(in.readLine().split(" "));
            succTab = StringTabToIntTab(in.readLine().split(" "));
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
	}
	/**
	 * Convert a String Table to an Integer Table
	 * @param tab table of String
	 * @return table of integers
	 */
	private int[] StringTabToIntTab(String[] tab){
		int[] res = new int[tab.length];
		int i = 0;
		for(String elt : tab) {
			res[i] = Integer.parseInt(elt);
			i++;
		}
		return res;
	}
	
	public int[] getHeadTab() {
		return headTab;
	}

	public int getNbEdges() {
		return nbEdges;
	}

	public int getNbVertices() {
		return nbVertices;
	}
	
	public int[] getSuccTab() {
		return succTab;
	}

}
