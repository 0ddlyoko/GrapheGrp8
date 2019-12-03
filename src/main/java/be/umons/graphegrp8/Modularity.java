package be.umons.graphegrp8;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.umons.graphegrp8.file.FileParser;
import be.umons.graphegrp8.node.Community;
import be.umons.graphegrp8.node.Node;
import be.umons.graphegrp8.node.NodeGroup;

public class Modularity {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
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
	 * generates a probability matrix corresponding to the vertices passed in
	 * parameter
	 * 
	 * @param vertice vertex table (with number 1 as the min vertex)
	 * @return probability matrix
	 */
	public double[][] verticeToMatrixProb(List<Integer> vertice) {
		double[][] matrixProbTemp = new double[vertice.size()][vertice.size()];
		for (int i = 0; i < vertice.size(); i++)
			for (int j = 0; j < vertice.size(); j++)
				matrixProbTemp[i][j] = matrixProb[vertice.get(i) - 1][vertice.get(j) - 1];
		return matrixProbTemp;

	}

	/**
	 * generates an adjacent matrix corresponding to the vertices passed in
	 * parameter
	 * 
	 * @param vertice vertex table (with number 1 as the min vertex)
	 * @return adjacent matrix
	 */
	public int[][] verticeToMatrixAdj(List<Integer> vertice) {
		int[][] matrixAdjTemp = new int[vertice.size()][vertice.size()];
		for (int i = 0; i < vertice.size(); i++)
			for (int j = 0; j < vertice.size(); j++)
				matrixAdjTemp[i][j] = matrixAdj[vertice.get(i) - 1][vertice.get(j) - 1];

		return matrixAdjTemp;
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
//		long before = System.currentTimeMillis();
		double result = 0;
		List<Integer> vertice = community.getArrayOfNodes();
		int[][] matrixAdjTemp = verticeToMatrixAdj(vertice);
		double[][] matrixProbTemp = verticeToMatrixProb(vertice);
//		for (int i : vertice)
//			LOG.info("A: {}", i);

//		for (int i = 0; i < vertice.size(); i++) {
//			for (int j = 0; j < vertice.size(); j++) {
////				LOG.info("A: {} - {} : {} - {}", i, j, matrixAdjTemp[i][j], matrixProbTemp[i][j]);
//				result += matrixAdjTemp[i][j] - matrixProbTemp[i][j];
//			}
//		}

		for (Node n : community.getNodes())
			if (n instanceof NodeGroup)
				for (Node n2 : ((NodeGroup) n).getNodes())
					for (Node n3 : ((NodeGroup) n).getNodes())
//						LOG.info("B: {} - {} : {} - {}", n2.getId() - 1, n3.getId() - 1,
//								matrixAdj[n2.getId() - 1][n3.getId() - 1], matrixProb[n2.getId() - 1][n3.getId() - 1]);
						result += matrixAdj[n2.getId() - 1][n3.getId() - 1]
								- matrixProb[n2.getId() - 1][n3.getId() - 1];
			else
				for (Node n2 : community.getNodes())
					result += matrixAdj[n.getId() - 1][n2.getId() - 1] - matrixProb[n.getId() - 1][n2.getId() - 1];

//		System.exit(0);

//		long after = System.currentTimeMillis();
//		LOG.info("resultOfModularity for {} nodes: {} ms", community.getNodes().size(), (after - before));
		return result / (2 * nbEdges);
	}

	/**
	 * Return the modularity of community by adding / removing specific id
	 * 
	 * @param c    The community
	 * @param node The node
	 * @param add  If true, add the modularity of id to the current modularity
	 * @return The new modularity
	 */
	public double resultOfModularity(Community c, Node node, boolean add) {
		int id = node.getId();
		double result = c.getCommunityCost() * (2 * nbEdges);
		double diff = 0;
//		LOG.info("result = {}", result);
		if (node instanceof NodeGroup) {
			// NodeGroup
			// Add every node
			for (Node n : ((NodeGroup) node).getNodes()) { // Loop over each nodes to add
				for (Node n2 : c.getNodes()) { // Loop over each nodes that is in community
					for (Node n3 : ((NodeGroup) n2).getNodes()) { // Same up
						diff += ((matrixAdj[n.getId() - 1][n3.getId() - 1] - matrixProb[n.getId() - 1][n3.getId() - 1])
								* 2);
					}
				}
				// The square
				for (Node n2 : ((NodeGroup) node).getNodes()) {
					diff += (matrixAdj[n.getId() - 1][n2.getId() - 1] - matrixProb[n.getId() - 1][n2.getId() - 1]);
				}
			}
		} else {
			for (Node n : c.getNodes())
				if (n.getId() != id)
					diff += ((matrixAdj[id - 1][n.getId() - 1] - matrixProb[id - 1][n.getId() - 1]) * 2);
			diff += (matrixAdj[id - 1][id - 1] - matrixProb[id - 1][id - 1]);
		}
//		LOG.info("diff = {}", diff);
		if (add)
			result += diff;
		else
			result -= diff;
//		LOG.info("{} {} => {}, new result = {}", add ? "+" : "-", id, c.getId(), result);
//		LOG.info("{} : {}", resultOfModularity(c), result / (2 * nbEdges));
		return result / (2 * nbEdges);
	}

	public int[][] getMatrixAdj() {
		return matrixAdj;
	}

	public double[][] getMatrixProb() {
		return matrixProb;
	}
}
