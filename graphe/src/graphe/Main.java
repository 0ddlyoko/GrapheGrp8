package graphe;

public class Main {
	
	public static void main(String[] args) {
		
		ReadFile rf = new ReadFile("Files/File1.txt");
		System.out.println(rf.getNbEdges());
		System.out.println(rf.getNbVertices());
		
		for(Integer elt: rf.getHeadTab()) {
			System.out.print(elt+" ");
		}
		System.out.println(" ");
		for(Integer elt: rf.getSuccTab()) {
			System.out.print(elt+" ");
		}
	}

}
