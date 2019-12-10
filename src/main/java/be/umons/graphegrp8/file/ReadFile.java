package be.umons.graphegrp8.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
