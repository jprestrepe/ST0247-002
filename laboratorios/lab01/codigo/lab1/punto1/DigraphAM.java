package lab1.punto1;

import java.util.ArrayList;

public class DigraphAM extends Graph {
    Pareja[][] matrix;

    public DigraphAM(int size) {
        super(size);
        matrix = new Pareja[size][size];
    }

    public void addArc(int source, int destino, double Peso, String nombre) {
        Pareja n = new Pareja(nombre, Peso);
        matrix[source][destino] = n;
    }

    public ArrayList<Integer> getSiguientes(int vertex) {
        ArrayList<Integer> Siguientes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (matrix[vertex][i].peso != 0) {
                Siguientes.add(i);
            }
        }
        if (Siguientes.size() == 0) {
            return null;
        }
        return Siguientes;
    }

    public Pareja getPeso(int source, int destino) {
        Pareja n = matrix[source][destino];
        return n;
    }
}
