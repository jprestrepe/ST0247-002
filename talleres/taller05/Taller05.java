import java.util.ArrayList;

/**
 * Clase en la cual se implementan los metodos del Taller 5
 * @author Mauricio Toro, Camilo Paez, Juan Pablo Restrepo, Juan José Sánchez
 */
public class Taller5 {

    /** 
     * Metodo que dado un grafo y un numero m, se asigna un color
     * a cada nodo, de manera que dos nodos adyacentes no poseean el mismo color
     * @param g grafo dado 
     * @param m numero de colores
     * @return true si es posible, false de lo contrario
     */
    public static boolean mColoring(Digraph g, int m) {
        if(g.size()<=m) return true;
        int[] colors = new int[g.size()];
        return mColoring(g, 0, colors, m);
    }

    /**
     * Metodo que dado un grafo y un vertice v, intenta asignar un color
     * al nodo, de manera que dos nodos adyacentes no poseean el mismo color
     * @param g grafo dado 
     * @param m numero de colores
     * @param v vertice 
     * @param colors conjunto de colores
     * @return true si es posible, false de lo contrario
     */
    private static boolean mColoring(Digraph g, int v, int[] colors, int m) {
        boolean cont = false;
        for(int i = 0; i< g.size(); i++){
            for(int z = 1; z<m+1; z++){
                if(isSafe(g, i, colors, z)){
                    colors[i]=z;
                    cont = true;
                    break;
                }else{
                    cont = false;
                }
                
            }
            if(!cont) return false;
        }
        return true;
    }

    /**
     * Metodo que dado un grafo y un vertice v, intenta asignar un color colors en la 
     * posicion c al nodo v, de manera que dos nodos adyacentes no poseean el mismo color
     * @param g grafo dado 
     * @param c indice de colores
     * @param v vertice 
     * @param colors conjunto de colores
     * @return true si es posible, false de lo contrario
     */
    private static boolean isSafe(Digraph g, int v, int[] colors, int c) {
        ArrayList<Integer> veci = g.getSuccessors(v);
        for(Integer i: veci) {
            if(colors[i]==c) return false;
        }
        return true;
    }
}
