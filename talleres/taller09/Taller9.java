package Taller9;

import java.util.*;
/**
 * @author Juan Pablo Restrepo, Juan José Sánchez
 */
public class Taller9 {

    public static int levenshtein(String a, String b) {
        int[][] distan = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            distan[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            distan[0][j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
        for (int j = 1; j <= b.length(); j++) {
        int minimo = Math.min(distan[i - 1][j], distan[i][j - 1]); 
        if (minimo < distan[i - 1][j-1]) {
        distan[i][j] = minimo + 1;
        }else{
         minimo = distan[i - 1][j-1];
         distan[i][j] = b.charAt(j-1) == a.charAt(i-1) ? minimo : minimo + 1;
        }
       }
      }
        for (int i = 0; i <= a.length(); i++) {
        for (int j = 0; j <= b.length(); j++) {
        System.out.print(distan[i][j] + " ");
       }
        System.out.println("");
       }
        return distan[a.length()][b.length()];
    }
}
