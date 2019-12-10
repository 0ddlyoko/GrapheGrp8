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
 * MIT License
 * 
 * Copyright (c) 2019 GrapheGrp8
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
