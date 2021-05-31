/**
 * Clase para representar los 3 tipos de nodos que se presentan en el mapa.
 *
 * @author Juan José Sánchez
 */
public class nodo {
    public int nodoId;
    public float nodo_X, nodo_Y;
    public double demand;
    public boolean visitado;
    public boolean IsDepot;
    boolean IsStation;
    int tipo; // Tipo de estación.
    float funCarga[];

    public nodo(float depot_x, float depot_y) {
        this.nodoId = 0;
        this.nodo_X = depot_x;
        this.nodo_Y = depot_y;
        this.IsDepot = true;
        this.IsStation = false;
        this.visitado = true;
    }

    public nodo(int id, float x, float y) {
        this.nodoId = id;
        this.nodo_X = x;
        this.nodo_Y = y;
        this.demand = 0;
        this.visitado = false;
        this.IsStation = false;
        this.IsDepot = false;
    }

    public nodo(int id, float x, float y, int tipo, float funCarga[]) {
        this.nodoId = id;
        this.nodo_X = x;
        this.nodo_Y = y;
        this.visitado = false;
        this.IsStation = true;
        this.tipo = tipo;
        this.funCarga = funCarga;
    }

    public Pair<Double, Double> caldemand(int currPos, double[][] distanceMatrix, double r, double speed) {
        double distancia = distanceMatrix[currPos][nodoId];
        double tiempo = distancia / speed;
        double consumo = r * distancia;
        return new Pair<Double, Double>(consumo, tiempo);
    }

    public double tiempoRecarga(double cantidadH) {
        return funCarga[tipo] * cantidadH;
    }
}