package lab2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

public class DigraphAL extends Digraph {
    ArrayList<LinkedList<Pair<Integer,Integer>>> listArray;
    /**
     * Constructor para el grafo dirigido
     * @param vertices el numero de vertices que tendra el grafo dirigido
     *
     */
    public DigraphAL(int size) {
        super(size);
        listArray = new ArrayList<>();
        for(int i = 0; i < size; i++){
            listArray.add(new LinkedList<Pair<Integer, Integer>>());  
        }
    }


    public void addArc(int source, int destination, int weight){        
        listArray.get(source).add(new Pair<Integer,Integer>(destination,weight));           
    }

    public ArrayList<Integer> getSuccessors(int vertex) {
        ArrayList<Integer>  successors = new ArrayList<Integer>();
        if(listArray.get(vertex).size() == 0){
            return null;
        }else{    
            LinkedList<Pair<Integer, Integer>> destinations = listArray.get(vertex);
            for(int i = 0; i < destinations.size(); i++){
                successors.add(destinations.get(i).first);  
            } 
        }
        Collections.sort(successors);
        return successors;
    }

    public int getWeight(int source, int destination) {
        if(listArray.get(source).size() != 0){
            LinkedList<Pair<Integer, Integer>> destinations = listArray.get(source);
            for(int i = 0; i < destinations.size(); i++){
                if(destinations.get(i).first == destination){
                    return destinations.get(i).second;
                }
            } 
        }
        return 0;
    }
}
