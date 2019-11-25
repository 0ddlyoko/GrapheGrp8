package graphe;

import java.util.ArrayList;

public class Main {
	
	public static void test() {
		
		
		
		ReadFile rf = new ReadFile("Files/File1.txt");
		Modularity mod = new Modularity(rf);
		
		
		System.out.println(rf.getNbEdges());
		System.out.println(rf.getNbVertices());
		
		for(Integer elt: rf.getHeadTab()) {
			System.out.print(elt+" ");
		}
		System.out.println("\n");
		for(Integer elt: rf.getSuccTab()) {
			System.out.print(elt+" ");
		}
		System.out.println("\n Table de dégré");
		for(Integer elt: rf.buildTabDegree()) {
			System.out.print(elt+" ");
		}
		
		System.out.println("\n Matrice adjacente initiale");
		for(int[] elt: mod.getMatrixAdj()) {
			for(Integer e : elt) {
				System.out.print(e+" ");
			}
			System.out.println(" ");
		}
		
		System.out.println("\n Matrice de probabilité initiale");
		for(double[] elt: mod.getMatrixProb()) {
			for(double e : elt) {
				System.out.printf("%.2f ",e);
			}
			System.out.println(" ");
		}
		
		ArrayList<Integer> sommet= new ArrayList<Integer>();
		sommet.add(16);
		sommet.add(4);
		sommet.add(15);
		sommet.add(12);
		sommet.sort(null);
		
		System.out.println("\n matrice pour les sommets selectionnés");
		for(int[] elt: mod.verticeToMatrixAdj(sommet)) {
			for(Integer e : elt) {
				System.out.print(e+" ");
			}
			System.out.println(" ");
		}
		
		System.out.println("\n");
		for(double[] elt: mod.verticeToMatrixProb(sommet)) {
			for(double e : elt) {
				System.out.printf("%.2f ",e);
			}
			System.out.println(" ");
		}
		
	}
	public static void main(String[] args) {
		test();
	
	}

}
