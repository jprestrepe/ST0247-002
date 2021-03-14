package lab3.punto1;

import java.util.ArrayList;
import java.util.Collections;

public class NReinasBt {
    /**
     * @param c       revisa hasta esta columna
     * @param tablero tablero
     * @return true si es posible
     */
    private static boolean puedoPonerReina(int c, int[] tablero) {
        for (int i = 0; i < c; i++) {
            if (tablero[i] == tablero[c]) {
                return false;
            } else if (Math.abs(tablero[i] - tablero[c]) == Math.abs(i - c)) {
                return false;
            }
        }
        return true;
    }

    public static int nReinas(int n) {
        return nReinas(0, n, new int[n]);
    }

    private static int nReinas(int c, int n, int[] tablero) {
        int result = 0;
        if (c == n)
            return 1;
        for (int i = 0; i < n; i++) {
            tablero[c] = i;
            if (puedoPonerReina(c, tablero)) {
                result = result + nReinas(c + 1, n, tablero);
            }
        }
        return result;
    }

    public static void imprimirTablero(int[] tablero) {
        int n = tablero.length;
        System.out.print("    ");
        for (int i = 0; i < n; ++i)
            System.out.print(i + " ");
        System.out.println("\n");
        for (int i = 0; i < n; ++i) {
            System.out.print(i + "   ");
            for (int j = 0; j < n; ++j)
                System.out.print((tablero[i] == j ? "Q" : "#") + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(nReinas(9));
    }
}