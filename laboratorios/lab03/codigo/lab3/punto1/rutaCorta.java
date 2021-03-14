package lab3.punto1;

import java.util.ArrayList;

public class rutaCorta {

    public static boolean hayCaminoDFS(Digraph g, int v, int w) {
        boolean[] visitados = new boolean[g.size()];
        return hayCaminoDFS(g, v, w, visitados);
    }

    private static boolean hayCaminoDFS(Digraph g, int v, int w, boolean[] visitados) {
        visitados[v] = true;
        if (v == w)
            return true;
        if (g.getSuccessors(v) == null)
            return false;
        ArrayList<Integer> f = g.getSuccessors(v);
        if (f.contains(w))
            return true;
        for (int i = 0; i < f.size(); i++) {
            if (!(visitados[f.get(i)] == true)) {
                if (hayCaminoDFS(g, f.get(i), w, visitados))
                    return true;
            }
        }
        return false;
    }

    public static int costoMinimo(Digraph g, int inicio, int fin) {
        if (inicio == fin)
            return 0;
        if (!(hayCaminoDFS(g, inicio, fin))) {
            return -1;
        }
        int costoAc = 0;
        int minAc = Integer.MAX_VALUE;
        return costoMinimo(g, inicio, fin, costoAc, minAc);
    }

    private static int costoMinimo(Digraph g, int inicio, int fin, int costoAc, int minAc) {
        if (inicio == fin) {
            minAc = Math.min(minAc, costoAc);
            return minAc;
        }
        ArrayList<Integer> sucesores = g.getSuccessors(inicio);

        for (int i = 0; i < sucesores.size(); i++) {
            if (!(hayCaminoDFS(g, sucesores.get(i), fin))) {
                continue;
            } else if (costoAc >= minAc) {
                continue;
            } else {
                costoAc = costoAc + g.getWeight(inicio, sucesores.get(i));
                minAc = costoMinimo(g, sucesores.get(i), fin, costoAc, minAc);
            }
            costoAc = costoAc - g.getWeight(inicio, sucesores.get(i));
        }

        return minAc;
    }

    public static void main(String[] args) {
        Digraph g = new DigraphAM(8);
        g.addArc(0, 6, 90);
        g.addArc(0, 3, 80);
        g.addArc(0, 1, 20);
        g.addArc(1, 5, 10);
        g.addArc(2, 7, 20);
        g.addArc(2, 3, 10);
        g.addArc(3, 6, 20);
        g.addArc(4, 6, 30);
        g.addArc(5, 2, 10);
        g.addArc(5, 3, 40);
        int minimo = costoMinimo(g, 0, 6);
        System.out.println(minimo);
    }
}