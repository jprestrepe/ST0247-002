import java.io.*;

/**
 * Esta clase tiene los metodos para iniciar y lee los datos del archivo que
 * entra
 *
 * @author Juan José Sánchez
 */
public class ruteoVehiculos {
    int n, m, u, breaks;
    double r, speed, Tmax, Smax, st_customer, Q;
    Digraph mapa;
    short tipoEstacion[];
    float funCarga[];
    String filename;
    static Pair<Float, Float>[] coordenadas;
    double HorasSolucion;
    int cantVehiculos = 0;

    double tiempoSolucion;

    public static void main(String[] args) {
        ruteoVehiculos problema = new ruteoVehiculos("prueba2.txt");
        int dataSize = 1024 * 1024;
        problema.solucionar();
        Runtime runtime = Runtime.getRuntime();
        System.out.println(
                "Memoria usada: " + (runtime.totalMemory() / dataSize - runtime.freeMemory() / dataSize) + "MB");

    }

    /**
     * En este metodo, se pasan los datos a la clase solucion, para asi, solucionar
     * el problema.
     */
    public void solucionar() {

        int numClientes = m;
        int numeroVehiculos = 50;
        double capVehiculo = Q;

        float coordeDepX = coordenadas[0].first;
        float coordeDepY = coordenadas[0].second;

        nodo[] nodos = new nodo[n];
        nodos[0] = new nodo(coordeDepX, coordeDepY);
        for (int i = 1; i <= numClientes; i++) {
            nodos[i] = new nodo(i, coordenadas[i].first, coordenadas[i].second);
        }
        for (int i = numClientes + 1, j = 0; i < n; i++, j++) {
            nodos[i] = new nodo(i, coordenadas[i].first, coordenadas[i].second, tipoEstacion[j], this.funCarga

            );
        }

        double[][] distanceMatrix = new double[n][n];
        double Delta_x, Delta_y;
        for (int i = 0; i <= n - 1; i++) {
            for (int j = i + 1; j <= n - 1; j++) {

                Delta_x = (nodos[i].nodo_X - nodos[j].nodo_X);
                Delta_y = (nodos[i].nodo_Y - nodos[j].nodo_Y);

                double distance = Math.sqrt((Delta_x * Delta_x) + (Delta_y * Delta_y));

                distance = Math.round(distance * 100.0) / 100.0;

                distanceMatrix[i][j] = distance;
                distanceMatrix[j][i] = distance;
            }
        }

        System.out.println("VRP para: " + numClientes + " clientes y " + u
                + " estaciones con " + capVehiculo + " watts de capacidad por vehiculo. \n");

        solucion s = new solucion(n - 1, numeroVehiculos, capVehiculo);

        HorasSolucion = s.solucionG(nodos, distanceMatrix, r, speed, Tmax);// Solución greedy

        s.imprimirSolucion("Greedy");
        cantVehiculos = s.cantVehiculos;
        draw.drawRoutes(s, "Solucion");
    }

    /**
     * Método constructor, este lee los datos del archivo
     * 
     * @param filename nombre del archivo a leer.
     */
    public ruteoVehiculos(String filename) {
        this.filename = filename;
        BufferedReader lector;
        String linea;
        String lineaPartida[];
        try {
            lector = new BufferedReader(new FileReader(filename));
            double[] valores = new double[10];
            for (int i = 0; i < 10; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                valores[i] = Float.parseFloat(lineaPartida[2]);
            }

            n = (int) valores[0];
            m = (int) valores[1];
            u = (int) valores[2];
            breaks = (int) valores[3];
            r = valores[4];
            speed = valores[5];
            Tmax = valores[6];
            Smax = valores[7];
            st_customer = valores[8];
            Q = valores[9];

            lector.readLine();
            lector.readLine();
            lector.readLine();

            coordenadas = new Pair[n];
            mapa = new DigraphAM(n);
            for (int i = 0; i <= m; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                coordenadas[Integer.parseInt(lineaPartida[0])] = new Pair(Float.parseFloat(lineaPartida[2]),
                        Float.parseFloat(lineaPartida[3]));
            }
            tipoEstacion = new short[u];
            for (int i = 0; i < u; i++) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                coordenadas[Integer.parseInt(lineaPartida[0])] = new Pair(Float.parseFloat(lineaPartida[2]),
                        Float.parseFloat(lineaPartida[3]));
                tipoEstacion[i] = Short.parseShort(lineaPartida[5]);
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    mapa.addArc(i, j, Math.sqrt(Math.pow(coordenadas[i].first - coordenadas[j].first, 2)
                            + Math.pow(coordenadas[i].second - coordenadas[j].second, 2)));
                }
            }

            funCarga = new float[3];
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                funCarga[i] = Float.parseFloat(lineaPartida[3]);
            }
            lector.readLine();
            lector.readLine();
            lector.readLine();
            for (int i = 0; i < 3; ++i) {
                linea = lector.readLine();
                lineaPartida = linea.split(" ");
                funCarga[i] = Float.parseFloat(lineaPartida[3]) / funCarga[i];
            }
            tiempoSolucion = Double.MAX_VALUE;
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String toString() {
        return "ruteoVehiculos{" + "r=" + r + ", speed=" + speed + ", Tmax=" + Tmax + ", Smax=" + Smax
                + ", st_customer=" + st_customer + ", Q=" + Q + ", tiempoSolucion=" + tiempoSolucion + '}';
    }

}