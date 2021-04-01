import java.util.ArrayList;
public class vehiculo
{
    public int VehId;
    public ArrayList<Node> Route = new ArrayList<Node>();
    public double capacity;
    public double carga;
    public int CurLoc;
    public boolean Closed;
    public double tiempoRuta;

    public vehiculo(int id, double cap)
    {
        this.VehId = id;
        this.capacity = cap;
        this.carga = 0;
        this.CurLoc = 0; //In depot Initially
        this.Closed = false;
        this.Route.clear();
        this.tiempoRuta = 0;
    }

    public void AddNode(Node Customer )//Add Customer to vehiculo Route
    {
        Route.add(Customer);
        this.CurLoc = Customer.NodeId;
    }

    public boolean CheckIfFits(double dem) //Check if we have Capacity Violation
    {
        return (this.carga + dem <= capacity);
    }

}