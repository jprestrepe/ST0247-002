package lab1.punto1;
import java.util.ArrayList;
/**
 * Abstract class for implementations of Digraphs
 * 
 * @author Mauricio Toro
 * @version 1
 */
public abstract class Graph
{
   protected int size;
   public  Graph(int vertices) 
   {
       size = vertices;
   }
   public  abstract void addArc(int source, int destino, double Peso, String nombre);
   public abstract ArrayList<Integer> getSiguientes(int vertice);
   public abstract Pareja getPeso(int source, int destino);
   public  int size() {return size;}
}