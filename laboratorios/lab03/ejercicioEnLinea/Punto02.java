import java.util.ArrayList;
import javafx.util.Pair;
public class Punto02{
  
    public static boolean hayCaminoDFS(Digraph g, int v, int w) {
        boolean[] visitados = new boolean[g.size()];
        return hayCaminoDFS(g, v, w, visitados);
    }
  
    private static boolean hayCaminoDFS(Digraph g, int v, int w, boolean[] visitados) {
        visitados[v] = true;
        if(v == w)return true;
        if(g.getSuccessors(v) == null) return false;
        ArrayList<Integer> hola = g.getSuccessors(v);
        if(hola.contains(w))return true;
        for(int i = 0; i < hola.size(); i++){
            if(!(visitados[hola.get(i)] == true)){ 
                if(hayCaminoDFS(g, hola.get(i), w, visitados))return true;
            }
        }
        return false;    
    }
  
    public static Pair<String, Integer> costoMinimo(Digraph g, int inicio, int fin){
        if(inicio == fin)return null;
        if(!(hayCaminoDFS(g,inicio,fin))){
            return null;
        }
        int costoAc = 0;
        int minAc = Integer.MAX_VALUE;
        ArrayList<String> pareja = new ArrayList<>();
        pareja.add("sd");
        pareja.add("ds");
        String ruta = String.valueOf(inicio);
        costoMinimo(g, inicio, fin, costoAc, minAc, pareja, ruta);
        Pair<String, Integer> camino = new Pair<String, Integer>(pareja.get(0), Integer.parseInt(pareja.get(1)));
        return camino;
    }
  
    private static int costoMinimo(Digraph g, int ini, int fin, int costoAc, int minAc, ArrayList<String> camino,String ruta){
        if(ini == fin){
            if(costoAc < minAc){
                minAc = Math.min(minAc, costoAc);
                camino.set(0, ruta);
                camino.set(1, String.valueOf(minAc));
                return minAc;                
            }
            minAc = Math.min(minAc, costoAc);
            return minAc;
        }
        ArrayList<Integer> sucesores = g.getSuccessors(ini);
        for(int i = 0; i < sucesores.size(); i++){
            if(!(hayCaminoDFS(g, sucesores.get(i), fin))){
                continue;
            }else if(costoAc >= minAc){
                continue;
            }else{ 
                costoAc = costoAc + g.getWeight(ini, sucesores.get(i));
                ruta = ruta + String.valueOf(sucesores.get(i));
                minAc = costoMinimo(g, sucesores.get(i), fin,costoAc, minAc,camino, ruta);                   
            }
            costoAc = costoAc - g.getWeight(ini,sucesores.get(i));   
            ruta = ruta.substring(0,ruta.length()-1);
        }       
        return minAc;
    }
  
    public static void main(String[] args){
          
        Digraph g = new DigraphAM(6);
        g.addArc(0,1, 5);
        g.addArc(0,4, 5);
        g.addArc(0,2, 5);
        g.addArc(1, 5, 2);
        g.addArc(1,3, 20);
        g.addArc(4,3, 25);
        g.addArc(5,3, 3);
        g.addArc(2,3, 10);
      
        Pair<String, Integer> camino = costoMinimo(g, 0,6);
        System.out.println(camino.getKey() + " " + camino.getValue());
    }
}
