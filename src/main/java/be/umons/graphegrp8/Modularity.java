package be.umons.graphegrp8;

import java.util.Collection;
import java.util.List;

import be.umons.graphegrp8.file.FileParser;
import be.umons.graphegrp8.node.Community;


public class Modularity {
	private int nbEdges;
	private int[][] matrixAdj;
	private double[][] matrixProb;
	
	/**
	 * constructor
	 * @param rd class to read a file and initialize the head table, the successor table and the degree table
	 */
	public Modularity(FileParser fp){
		nbEdges = fp.getNbEdges();
		initMatrixAdj(fp.getNbVertices(), fp.getHeadTab(), fp.getSuccTab());
		initMatrixProb(fp.getNbVertices(), fp.getDegTab(), nbEdges);
	}
	
	/**
	 * initial construction method of adjacency matrix
	 * @param size_ size of adjacency matrix
	 * @param head index table of vertices in the successor table
	 * @param succ successor table of vertices
	 */
	private void initMatrixAdj(int size_, int[] head, int[] succ) {
		matrixAdj = new int[size_][size_];
		for(int i = 0; i < size_; i++) {
			for(int j = head[i] - 1; j < head[i+1]-1; j++) {
				matrixAdj[i][succ[j] - 1] = 1; // -1 car les sommets dans successeur vont de 1 à n hors besoin d'indice 0 à n-1
			}
		}
	}
	/**
	 * probability matrix construction method
	 * @param size_ size of adjacency matrix
	 * @param deg degree table of vertices
	 * @param m number of edges
	 */
	private void initMatrixProb(int size_, int[] deg, int m) {
		matrixProb = new double[size_][size_];
		for(int i = 0; i < size_; i++) {
			for(int j = i + 1; j < size_; j++) {
				matrixProb[j][i] = matrixProb[i][j] = deg[i] * deg[j] / (2.0 * m);
			}
			matrixProb[i][i] = deg[i] * deg[i] / (2.0 * m);
		}
	}
	
	/**
	 * generates a probability matrix corresponding to the vertices passed in parameter
	 * @param vertice vertex table (with number 1 as the min vertex)
	 * @return probability matrix
	 */
	public double[][] verticeToMatrixProb(List<Integer> vertice){
		double[][] matrixProbTemp = new double[vertice.size()][vertice.size()];
		for (int i = 0; i < vertice.size(); i++) {
			for(int j = 0; j < vertice.size(); j++) {
				matrixProbTemp[i][j] = matrixProb[vertice.get(i) - 1][vertice.get(j) - 1];
			}
		}
		return matrixProbTemp;
		
	}
	/**
	 * generates an adjacent matrix corresponding to the vertices passed in parameter
	 * @param vertice vertex table (with number 1 as the min vertex)
	 * @return adjacent matrix
	 */
	public int[][] verticeToMatrixAdj(List<Integer> vertice){
		int[][] matrixAdjTemp = new int[vertice.size()][vertice.size()];
		int i = 0;
		for(Integer vertice_i : vertice) {
			int j = 0;
			for(Integer vertice_j : vertice) {
				matrixAdjTemp[i][j] = matrixAdj[vertice_i - 1][vertice_j - 1];
				j++;
			}
			i++;
		}
		
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
		double result = 0;
		List<Integer> vertice = community.getArrayOfNodes();
		int[][] matrixAdjTemp = verticeToMatrixAdj(vertice);
		double[][] matrixProbTemp = verticeToMatrixProb(vertice);
		for (int i = 0; i < vertice.size(); i++) {
			double colSom = 0;
			for (int j = 0; j < vertice.size(); j++) {
				colSom += matrixAdjTemp[i][j] - matrixProbTemp[i][j];
			}
			result += colSom;
		}
		return result / (2 * nbEdges);
	}
	
	public int[][] getMatrixAdj() {
		return matrixAdj;
	}

	public double[][] getMatrixProb() {
		return matrixProb;
	}
}
