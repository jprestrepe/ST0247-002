package lab1.punto1;

import java.io.*;
import java.util.*;

public class ClaseBufferedReader
{
    public  DigraphAM mapa = new DigraphAM(5);
    
    public ClaseBufferedReader(String s) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(s));
        String line = br.readLine();
        String[] splitted = line.split(" ");
        while(!splitted[0].equals( "Arcos.")){
            line = br.readLine();
            splitted = line.split(" ");            
        }
        br.readLine();
        
        
        while((line = br.readLine()) != null){
            splitted = line.split(" ");
            int n1 = Integer.parseInt(splitted[0]);
            int n2 = Integer.parseInt(splitted[1]);
            if(n1 == 10000){
                n1 = 0;
            }else if(n2 == 10000){
                n2 = 0;
            }
            double peso = Double.parseDouble(splitted[2]);
            String nombre = splitted[3];
            mapa.addArc(n1, n2, peso, nombre);
        }
        br.close();
    }
}