package be.umons.graphegrp8;

import java.util.Collection;


import be.umons.graphegrp8.file.FileParser;
import be.umons.graphegrp8.node.Community;
import be.umons.graphegrp8.node.Node;
import be.umons.graphegrp8.node.NodeGroup;

public class Modularity {
	private int nbEdges;
	private int[][] matrixAdj;
	private double[][] matrixProb;

	/**
	 * constructor
	 * 
	 * @param rd class to read a file and initialize the head table, the successor
	 *           table and the degree table
	 */
	public Modularity(FileParser fp) {
		nbEdges = fp.getNbEdges();
		initMatrixAdj(fp.getNbVertices(), fp.getHeadTab(), fp.getSuccTab());
		initMatrixProb(fp.getNbVertices(), fp.getDegTab(), nbEdges);
	}

	/**
	 * initial construction method of adjacency matrix
	 * 
	 * @param size_ size of adjacency matrix
	 * @param head  index table of vertices in the successor table
	 * @param succ  successor table of vertices
	 */
	private void initMatrixAdj(int size_, int[] head, int[] succ) {
		matrixAdj = new int[size_][size_];
		for (int i = 0; i < size_; i++) {
			for (int j = head[i] - 1; j < head[i + 1] - 1; j++) {
				matrixAdj[i][succ[j] - 1] = 1; // -1 car les sommets dans successeur vont de 1 à n hors besoin d'indice
				// 0 à n-1
				matrixAdj[succ[j] - 1][i] = 1; // -1 car les sommets dans successeur vont de 1 à n hors besoin d'indice
				// 0 à n-1
			}
		}
	}

	/**
	 * probability matrix construction method
	 * 
	 * @param size_ size of adjacency matrix
	 * @param deg   degree table of vertices
	 * @param m     number of edges
	 */
	private void initMatrixProb(int size_, int[] deg, int m) {
		matrixProb = new double[size_][size_];
		for (int i = 0; i < size_; i++) {
			for (int j = i + 1; j < size_; j++) {
				matrixProb[j][i] = matrixProb[i][j] = deg[i] * deg[j] / (2.0 * m);
			}
			matrixProb[i][i] = deg[i] * deg[i] / (2.0 * m);
		}
	}

	/**
	 * Calculate the modularity for a partition of communities
	 * 
	 * @param partition The partition of communities
	 * @return The modularity for specific partition
	 */
	public double resultOfModularity(Collection<Community> partition) {
		double result = 0;
		for (Community community : partition)
			result += resultOfModularity(community);
		return result;
	}

	/**
	 * Calculate the modularity for specific community
	 * 
	 * @param community The community
	 * @return The modularity for specific community
	 */
	public double resultOfModularity(Community community) {
		double result = 0;

		for (Node n : community.getNodes())
			if (n instanceof NodeGroup)
				for (Node n2 : ((NodeGroup) n).getNodes())
					for (Node n3 : community.getNodes())
						for (Node n4 : ((NodeGroup) n3).getNodes())
							result += (matrixAdj[n2.getId() - 1][n4.getId() - 1]
									- matrixProb[n2.getId() - 1][n4.getId() - 1]);
			else
				for (Node n2 : community.getNodes())
					result += matrixAdj[n.getId() - 1][n2.getId() - 1] - matrixProb[n.getId() - 1][n2.getId() - 1];

		return result / (2 * nbEdges);
	}

	/**
	 * Return the modularity of community by adding / removing specific id<br />
	 * !!!! This method calculate the new modularity from the saved modularity of
	 * community.<br />
	 * If the saved community is incorrect, this method will not return a correct
	 * value !
	 * 
	 * @param community The community
	 * @param node      The node
	 * @param add       If true, add the modularity of id to the current modularity
	 * @return The new modularity
	 */
	public double resultOfModularity(Community community, Node node, boolean add) {
		int id = node.getId();
		double result = community.getCommunityCost();
		double diff = 0;
		if (node instanceof NodeGroup) {
			// NodeGroup
			// Add every node
			for (Node n : ((NodeGroup) node).getNodes()) { // Loop over each nodes to add
				for (Node n2 : community.getNodes()) { // Loop over each nodes that is in community
					for (Node n3 : ((NodeGroup) n2).getNodes()) { // Same up
						if (n2.getId() != id)
							diff += ((matrixAdj[n.getId() - 1][n3.getId() - 1]
									- matrixProb[n.getId() - 1][n3.getId() - 1]) * 2);
					}
				}
				// The square
				for (Node n2 : ((NodeGroup) node).getNodes()) {
					diff += (matrixAdj[n.getId() - 1][n2.getId() - 1] - matrixProb[n.getId() - 1][n2.getId() - 1]);
				}
			}
		} else {
			for (Node n : community.getNodes())
				if (n.getId() != id)
					diff += ((matrixAdj[id - 1][n.getId() - 1] - matrixProb[id - 1][n.getId() - 1]) * 2);
			diff += (matrixAdj[id - 1][id - 1] - matrixProb[id - 1][id - 1]);
		}
		if (add)
			result += (diff / (2 * nbEdges));
		else
			result -= (diff / (2 * nbEdges));
		return result;
	}

	public double resultOfModularity(Community community, boolean test) {
		double result = 0;

		for (Node n : community.getNodes())
			if (n instanceof NodeGroup)
				for (Node n2 : ((NodeGroup) n).getNodes())
					for (Node n3 : community.getNodes())
						for (Node n4 : ((NodeGroup) n3).getNodes())
							result += (matrixAdj[n2.getId() - 1][n4.getId() - 1]
									- matrixProb[n2.getId() - 1][n4.getId() - 1]);
			else
				for (Node n2 : community.getNodes())
					result += (matrixAdj[n.getId() - 1][n2.getId() - 1] - matrixProb[n.getId() - 1][n2.getId() - 1]);
		return result / (2 * nbEdges);
	}

	public int[][] getMatrixAdj() {
		return matrixAdj;
	}

	public double[][] getMatrixProb() {
		return matrixProb;
	}
}
