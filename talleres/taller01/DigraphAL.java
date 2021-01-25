import java.util.ArrayList;

/**
 * Implementacion de un grafo dirigido usando listas de adyacencia
 * @author Mauricio Toro, Mateo Agudelo, Juan Pablo Restrepo, Juan José Sánchez
 */
public class DigraphAL extends Digraph {
	
private ArrayList<LinkedList<Pair<Integer,Integer> Lista;
	
	/**
	* Constructor para el grafo dirigido
	* @param vertices el numero de vertices que tendra el grafo dirigido
	*/
	public DigraphAL(int size) {
		super(size);
		Lista = new ArrayList<>();
        for (int i = 0; i < size + 1; i++) {
        Lista[i] = new LinkedList<Pair<Integer,Integer>>();
	}

	/**
	* Metodo para añadir un arco nuevo, donde se representa cada nodo con un entero
	* y se le asigna un peso a la longitud entre un nodo fuente y uno destino	
	* @param source desde donde se hara el arco
	* @param destination hacia donde va el arco
	* @param weight el peso de la longitud entre source y destination
	*/
	public void addArc(int source, int destination, int weight) {
	ArrayList<LinkedList<Pair<Integer,Integer> UbicacionSource = Lista.get(source);
	Pair<Integer,Integer> ParNuevo = new Pair(destination, weight);
        UbicacionSource.add(ParNuevo);
	}

	/**
	* Metodo para obtener una lista de hijos desde un nodo, es decir todos los nodos
	* asociados al nodo pasado como argumento
	* @param vertex nodo al cual se le busca los asociados o hijos
	* @return todos los asociados o hijos del nodo vertex, listados en una ArrayList
	* Para más información de las clases:
 	* @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html"> Ver documentacion ArrayList </a>
	*/
	public ArrayList<Integer> getSuccessors(int vertex) {
	ArrayList<LinkedList<Pair<Integer, Integer> Destino = Lista[vertex];
                ArrayList<Integer> Sucesor = new ArrayList<>();
                if(Destino.isEmpty())return Sucesor; 
                for(int i = 0; i < Destino.size(); i++){
                   Sucesor.add(Destino.get(i).first);
                }
                return Sucesor;	
	}

	/**
	* Metodo para obtener el peso o longitud entre dos nodos
	* @param source desde donde inicia el arco
	* @param destination  donde termina el arco
	* @return un entero con dicho peso
	*/	
	public int getWeight(int source, int destination) {
        ArrayList<LinkedList<Pair<Integer, Integer> Destino = Lista[source];
                 for (int i = 0; i < Destino.size(); i++)
                 if (Destino.get(i).first == destination)
                 return Destino.get(i).second;
                 return 0;
	}
}
