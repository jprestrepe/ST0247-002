import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

/**
 * Clase que dibuja las rutas.
 *
 * @author Juan José Sánchez
 */
public class draw
{
    /**
     *  Metodo que crea la imagen final.
     *
     * @param s Las soluciones del algoritmo.
     * @param fileName nombre del archivo.
     */
    public static void  drawRoutes(solucion s, String fileName) {

        int VRP_Y = 1440;
        int VRP_INFO = 560;
        int X_GAP = 2000;
        int margin = 20;
        int marginnodo;


        int XXX = VRP_INFO + X_GAP;
        int YYY = VRP_Y;


        BufferedImage output = new BufferedImage(XXX, YYY, BufferedImage.TYPE_INT_RGB);
        Graphics2D graficos = output.createGraphics();
        Color fondo = new Color(225,254,255);
        graficos.setColor(fondo);
        graficos.fillRect(0, 0, XXX, YYY);
        graficos.setColor(Color.BLACK);


        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (int k = 0; k < s.Vehiculos.length ; k++)
        {
            for (int i = 0; i < s.Vehiculos[k].Route.size(); i++)
            {
                nodo n = s.Vehiculos[k].Route.get(i);
                if (n.nodo_X > maxX) maxX = n.nodo_X;
                if (n.nodo_X < minX) minX = n.nodo_X;
                if (n.nodo_Y > maxY) maxY = n.nodo_Y;
                if (n.nodo_Y < minY) minY = n.nodo_Y;
            }
        }

        int mX = XXX - 2 * margin;
        int mY = VRP_Y - 2 * margin;

        int A, B;
        if ((maxX - minX) > (maxY - minY))
        {
            A = mX;
            B = (int)((double)(A) * (maxY - minY) / (maxX - minX));
            if (B > mY)
            {
                B = mY;
                A = (int)((double)(B) * (maxX - minX) / (maxY - minY));
            }
        }
        else
        {
            B = mY;
            A = (int)((double)(B) * (maxX - minX) / (maxY - minY));
            if (A > mX)
            {
                A = mX;
                B = (int)((double)(A) * (maxY - minY) / (maxX - minX));
            }
        }

        // draw Route
        for (int i = 0; i < s.Vehiculos.length ; i++)
        {
            for (int j = 1; j < s.Vehiculos[i].Route.size() ; j++) {
                nodo n;
                n = s.Vehiculos[i].Route.get(j-1);

                int ii1 = (int) ((double) (A) * ((n.nodo_X - minX) / (maxX - minX) - 0.5) + (double) mX / 2) + margin;
                int jj1 = (int) ((double) (B) * (0.5 - (n.nodo_Y - minY) / (maxY - minY)) + (double) mY / 2) + margin;

                n = s.Vehiculos[i].Route.get(j);
                int ii2 = (int) ((double) (A) * ((n.nodo_X - minX) / (maxX - minX) - 0.5) + (double) mX / 2) + margin;
                int jj2 = (int) ((double) (B) * (0.5 - (n.nodo_Y - minY) / (maxY - minY)) + (double) mY / 2) + margin;

                graficos.setStroke(new BasicStroke(3.5F));
                graficos.setColor(new Color(202,202,202));
                graficos.drawLine(ii1, jj1, ii2, jj2);
            }
        }

        for (int i = 0; i < s.Vehiculos.length ; i++)
        {
            for (int j = 0; j < s.Vehiculos[i].Route.size() ; j++) {
                marginnodo = 2;
                nodo n = s.Vehiculos[i].Route.get(j);

                int ii = (int) ((double) (A) * ((n.nodo_X  - minX) / (maxX - minX) - 0.5) + (double) mX / 2) + margin;
                int jj = (int) ((double) (B) * (0.5 - (n.nodo_Y - minY) / (maxY - minY)) + (double) mY / 2) + margin;
                if(n.IsDepot){
                    marginnodo = 3;
                    graficos.setColor(Color.BLACK);
                    graficos.fillRect(ii - 3 * marginnodo, jj - 3 * marginnodo, 6 * marginnodo, 6 * marginnodo); //2244
                    String id = Integer.toString(n.nodoId);
                    graficos.setColor(Color.BLACK);
                    graficos.drawString(id, ii + 6 * marginnodo, jj + 6 * marginnodo);
                }else if(n.IsStation){
                    marginnodo = 3;
                    Color color = new Color(255,231,0);
                    graficos.setColor(color);
                    graficos.fillOval(ii - 3 * marginnodo, jj - 3 * marginnodo, 6 * marginnodo, 6 * marginnodo); //2244
                    String id = Integer.toString(n.nodoId);
                    graficos.setColor(Color.BLACK);
                    graficos.drawString(id, ii + 6 * marginnodo, jj + 6 * marginnodo);
                }else{
                    graficos.setColor(Color.RED);
                    graficos.fillOval(ii - 3 * marginnodo, jj - 3 * marginnodo, 6 * marginnodo, 6 * marginnodo); //2244
                    String id = Integer.toString(n.nodoId);
                    graficos.setColor(Color.BLACK);
                    graficos.drawString(id, ii + 6 * marginnodo, jj + 6 * marginnodo);
                }
            }

        }
        marginnodo = 5;
        graficos.setFont(new Font("Arial Bold",Font.PLAIN,40));
        graficos.setColor(Color.BLACK);
        graficos.drawString("Nodo deposito", 10 * marginnodo+5, 125 * marginnodo);
        graficos.fillRect(10 * marginnodo-30,75* marginnodo-25,25,25);
        graficos.drawString("Nodo estación",10 * marginnodo+5,75 * marginnodo);
        Color color = new Color(255,231,0);
        graficos.setColor(color);
        graficos.fillRect(10* marginnodo-30,125* marginnodo-25,25,25);
        graficos.setColor(Color.BLACK);
        graficos.drawString("Nodo cliente", 10 * marginnodo+5, 175 * marginnodo);
        graficos.setColor(Color.RED);
        graficos.fillRect(10* marginnodo-30,175* marginnodo-25,25,25);
        fileName = fileName + ".png";
        File f = new File(fileName);
        try
        {
            ImageIO.write(output, "PNG", f);
        } catch (IOException ex) {
        }
    }
}