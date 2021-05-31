import java.util.ArrayList;

/**
 * Implementación del grafo.
 *
 * @author Juan José Sánchez
 */
public abstract class Digraph {

	protected int size;

	/**
	 * Constructor para el grafo.
	 * 
	 * @param vertices el numero de vertices que tendra el grafo.
	 */
	public Digraph(int vertices) {
		size = vertices;
	}

	public abstract void addArc(int source, int destination, double weight);

	public abstract ArrayList<Integer> getSuccessors(int vertex);

	public abstract double getWeight(int source, int destination);

	public int size() {
		return size;
	}
}