package be.umons.graphegrp8.file;

import java.io.File;

public interface FileParser {
	/**
	 * Parse the file
	 * 
	 * @param file The file to parse
	 */
	public void parse(File file);

	/**
	 * @return the number of edges
	 */
	public int getNbEdges();

	/**
	 * @return the number of vertices
	 */
	public int getNbVertices();

	/**
	 * @return an array containing the Head
	 */
	public int[] getHeadTab();

	/**
	 * @return an array containing the Succ
	 */
	public int[] getSuccTab();

	/**
	 * @return an array containing degrees of edges
	 */
	public int[] getDegTab();
}
