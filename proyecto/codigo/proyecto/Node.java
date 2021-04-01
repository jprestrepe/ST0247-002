public class Node
{
    public int NodeId;
    public float Node_X ,Node_Y;    //Node Coordinates
    public double demand;   //Node Demand if Customer
    public boolean IsRouted;
    public boolean IsDepot;
    boolean IsStation;   //True if it Depot Node
    int tipo;
    float pendienteFuncionCarga[];

    /**
     * Constructor para el Deposito
     * @param depot_x   Coordenada X
     * @param depot_y   Coordenada Y
     */
    public Node(float depot_x,float depot_y) //Cunstructor for depot
    {
        this.NodeId = 0;
        this.Node_X = depot_x;
        this.Node_Y = depot_y;
        this.IsDepot = true;
        this.IsStation = false;
    }

    /**
     * Constructor para Clientes
     * @param id    numero del cliente
     * @param x     Cordenada en X
     * @param y     Cordenada en Y
     */
    public Node(int id ,float x, float y)
    {
        this.NodeId = id;
        this.Node_X = x;
        this.Node_Y = y;
        this.demand =0;
        this.IsStation = false;
        this.IsDepot = false;
    }

    /**
     * Constructor para Estaciones
     * @param id    numero de la Estacion
     * @param x     Cordenada en X
     * @param y     Cordenada en Y
     * @param tipo      0 carga rapida, 1 carga media, 2 carga lenta
     * @param pendienteFuncionCarga
     */
    public Node(int id ,float x, float y, int tipo,float pendienteFuncionCarga[])
    {
        this.NodeId = id;
        this.Node_X = x;
        this.Node_Y = y;
        this.IsRouted = false;
        this.IsStation = true;
        this.tipo = tipo;
        this.pendienteFuncionCarga = pendienteFuncionCarga;
    }

    public Pair<Double,Double> caldemand(int currPos, double[][] distanceMatrix, double r, double speed){
        double distancia = distanceMatrix[currPos][NodeId];
        double tiempo = distancia/speed;
        double consumo = r * distancia;
        this.demand = consumo;
        return new Pair<Double, Double>(consumo,tiempo);
    }

    /**
     * Metodo que calcula el tiempo que se demora recargando 
     * @param cantidadH     cantidad de horas por el tipo de estacion
     * @return      devuelve el tiempo que le demora al camion cargarse copletamente
     */
    public double calcularTiempoRecarga(double cantidadH){
        return pendienteFuncionCarga[tipo]*cantidadH;
    }
}