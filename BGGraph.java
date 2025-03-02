import java.util.LinkedList;

public class BGGraph {
	
	int vertices;
	LinkedList<Integer> adjacencyList[];
	
	@SuppressWarnings("unchecked")
	public BGGraph(int vertices) {
		
		this.vertices = vertices;
		adjacencyList = new LinkedList[vertices];
		
		for (int i = 0; i < vertices; i++) {
			adjacencyList[i] = new LinkedList<>();
		}
	}
	
	public void addEdge(int source, int target) {
		adjacencyList[source].add(target);
		adjacencyList[target].add(source);
	}
	
	public void removeEdge(int source, int target) {
		System.out.println(source + ", " + target);
		System.out.println(adjacencyList[source].indexOf(target) + ", " + adjacencyList[target].indexOf(source));
		adjacencyList[source].remove(adjacencyList[source].indexOf(target));
		adjacencyList[target].remove(adjacencyList[target].indexOf(source));
	}

}