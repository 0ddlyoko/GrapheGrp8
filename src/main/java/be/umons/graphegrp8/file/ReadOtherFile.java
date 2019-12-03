package be.umons.graphegrp8.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Class to read a file and initialize the head table, the successor table and
 * the degree table
 */
public class ReadOtherFile extends ReadFile {
	private SortedSet<Integer>[] map;

	@Override
	public void parse(File file) {
		try {
			Pattern space = Pattern.compile("	");

			BufferedReader in = new BufferedReader(new FileReader(file));
			nbEdges = 0;
			nbVertices = Integer.parseInt(in.readLine().trim());
			boolean zero = "true".equalsIgnoreCase(in.readLine().trim());
			System.out.println(zero ? "true" : "false");
			this.map = new SortedSet[nbVertices];
			String line;
			while ((line = in.readLine()) != null) {
				if ("".equalsIgnoreCase(line.trim()))
					break;
				// Split line from space
				String[] arr = space.split(line);
				Integer first = Integer.parseInt(arr[0]) + (zero ? 1 : 0);
				Integer second = Integer.parseInt(arr[1]) + (zero ? 1 : 0);
				// Second is always < than first
				if (map[first - 1] == null)
					map[first - 1] = new TreeSet<Integer>();
				if (map[second - 1] == null)
					map[second - 1] = new TreeSet<Integer>();
				map[first - 1].add(second);
				map[second - 1].add(first);
				nbEdges++;
			}

			// Create headTab
			headTab = new int[map.length + 1];
			int old = 1;
			for (int i = 0; i < map.length; i++) {
				// Single node
				if (map[i] == null)
					map[i] = new TreeSet<Integer>();
				headTab[i] = old;
				old += map[i].size();
			}
			headTab[headTab.length - 1] = old;

			succTab = new int[nbEdges * 2];
			int i = 0;
			for (int j = 0; j < map.length; j++) {
				Iterator<Integer> it = map[j].iterator();
				while (it.hasNext()) {
					int next = it.next();
					succTab[i] = next;
					i++;
				}
			}
			degTab = buildTabDegree();
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
