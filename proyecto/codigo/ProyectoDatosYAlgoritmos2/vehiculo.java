import java.util.ArrayList;

/**
 * Representación de un vehículo.
 *
 * @author Juan José Sánchez
 */
public class vehiculo {
    public int VehId;
    public ArrayList<nodo> Route = new ArrayList<nodo>();
    public double capacity;
    public double carga;
    public int CurLoc;
    public double tiempoRuta;

    public vehiculo(int id, double cap) {
        this.VehId = id;
        this.capacity = cap;
        this.carga = 0;
        this.CurLoc = 0;
        this.Route.clear();
        this.tiempoRuta = 0;
    }

    public void Addnodo(nodo Customer) {
        Route.add(Customer);
        this.CurLoc = Customer.nodoId;
    }

    public boolean CheckIfFits(double dem) {
        return (this.carga + dem <= capacity);
    }

}