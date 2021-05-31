import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;

/**
 * Clase encargada de encontrar las rutas para los vehículos.
 *
 * @author Juan José Sánchez
 */
public class solucion {
    int numeroVehiculos;
    int numClientes;
    vehiculo[] Vehiculos;
    double Costo;
    final double constanteDeCarga = 0.51;
    int cantVehiculos = 0;

    public solucion(int ClienNum, int VechNum, double VechCap) {
        this.numeroVehiculos = VechNum;
        this.numClientes = ClienNum;
        this.Costo = 0;
        Vehiculos = new vehiculo[numeroVehiculos];

        for (int i = 0; i < numeroVehiculos; i++) {
            Vehiculos[i] = new vehiculo(i + 1, VechCap);
        }
    }

    public boolean nodoSinSerVisitado(nodo[] nodos) {
        for (int i = 1; i < nodos.length; i++) {
            if (!nodos[i].visitado)
                return true;
        }
        return false;
    }

    public double solucionG(nodo[] nodos, double[][] CostMatrix, double r, double speed, double Tmax) {

        double CostoCandidato, CostoFinal, tiempoRuta = 0, demanda = 0;
        int VehIndex = 0;
        while (nodoSinSerVisitado(nodos)) {
            int CustIndex = 0;
            nodo Candidato = null;
            double minCost = (float) Double.MAX_VALUE;

            if (Vehiculos[VehIndex].Route.isEmpty()) {
                Vehiculos[VehIndex].Addnodo(nodos[0]); // Se acabo de iniciar una ruta por eso se pone el nodo deposito.
            }
            double[][] auxCostMatrix = CostMatrix;

            for (int i = 1; i <= numClientes; i++) { // Empezamos en 1 porque 0 es el deposito
                if (nodos[i].visitado == false) {
                    if (Vehiculos[VehIndex].CheckIfFits(
                            nodos[i].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r, speed).first)) {
                        CostoCandidato = CostMatrix[Vehiculos[VehIndex].CurLoc][i];
                        if (minCost > CostoCandidato) { // Si se encuentra una mejor solución.
                            minCost = CostoCandidato;
                            tiempoRuta = nodos[i].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r, speed).second;
                            demanda = nodos[i].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r, speed).first;
                            CustIndex = i;
                            Candidato = nodos[i];
                        }
                    } else {
                        int estacionCercanaAlCandidato = econtrarEstacionDeCarga(auxCostMatrix, nodos, i);
                        if (Vehiculos[VehIndex].CheckIfFits(nodos[estacionCercanaAlCandidato]
                                .caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r, speed).first)) {
                            CostoCandidato = CostMatrix[Vehiculos[VehIndex].CurLoc][estacionCercanaAlCandidato];
                            if (minCost > CostoCandidato) { // Si se encuentra una mejor solución.
                                minCost = CostoCandidato;
                                tiempoRuta = nodos[estacionCercanaAlCandidato].caldemand(Vehiculos[VehIndex].CurLoc,
                                        auxCostMatrix, r, speed).second;
                                demanda = nodos[estacionCercanaAlCandidato].caldemand(Vehiculos[VehIndex].CurLoc,
                                        auxCostMatrix, r, speed).first;
                                CustIndex = estacionCercanaAlCandidato;
                                Candidato = nodos[estacionCercanaAlCandidato];
                            }
                        } else {
                            int estacionCercanaAlAnterior = econtrarEstacionDeCarga(auxCostMatrix, nodos,
                                    estacionCercanaAlCandidato);
                            if (Vehiculos[VehIndex].CheckIfFits(nodos[estacionCercanaAlAnterior]
                                    .caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r, speed).first)) {
                                CostoCandidato = CostMatrix[Vehiculos[VehIndex].CurLoc][estacionCercanaAlAnterior];
                                if (minCost > CostoCandidato) { // Si se encuentra una mejor solución.
                                    minCost = CostoCandidato;
                                    tiempoRuta = nodos[estacionCercanaAlAnterior].caldemand(Vehiculos[VehIndex].CurLoc,
                                            auxCostMatrix, r, speed).second;
                                    demanda = nodos[estacionCercanaAlAnterior].caldemand(Vehiculos[VehIndex].CurLoc,
                                            auxCostMatrix, r, speed).first;
                                    CustIndex = estacionCercanaAlAnterior;
                                    Candidato = nodos[estacionCercanaAlAnterior];
                                }
                            }
                        }
                    }
                }
            }

            if (Candidato == null) {
                if (VehIndex + 1 < Vehiculos.length) {
                    if (Vehiculos[VehIndex].CurLoc != 0) {
                        CostoFinal = CostMatrix[Vehiculos[VehIndex].CurLoc][0];
                        Vehiculos[VehIndex].Addnodo(nodos[0]);
                        this.Costo += CostoFinal;
                    }
                    VehIndex = VehIndex + 1; // Se mueve al siguiente vehículo.
                } else {
                    System.out.println("\nEl resto de clientes no encaja con un vehículo\n"
                            + "No se puede resolver el problema con estas condiciones.");
                    System.exit(0);
                }
            } else {
                if (!nodos[Vehiculos[VehIndex].CurLoc].IsStation && !nodos[Vehiculos[VehIndex].CurLoc].IsDepot) {
                    Vehiculos[VehIndex].tiempoRuta += 0.5;
                }
                if (Vehiculos[VehIndex].tiempoRuta + tiempoRuta + Candidato.caldemand(0, auxCostMatrix, r, speed).second
                        + constanteDeCarga >= Tmax) { // Si el vehículo viola el limite del tiempo, entonces finalice la
                                                      // ruta.
                    Vehiculos[VehIndex].carga += nodos[0].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r,
                            speed).first;
                    Vehiculos[VehIndex].tiempoRuta += nodos[0].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r,
                            speed).second;
                    CostoFinal = CostMatrix[Vehiculos[VehIndex].CurLoc][0];
                    Vehiculos[VehIndex].Addnodo(nodos[0]);
                    this.Costo += CostoFinal;
                    if (VehIndex + 1 < Vehiculos.length) { // Si todavia hay más vehículos.
                        VehIndex = VehIndex + 1; // Se mueve al siguiente vehículo.
                    }
                } else {// Si no hay un candidato que no viola la primera restricción.
                    if (Vehiculos[VehIndex].carga + demanda < Vehiculos[VehIndex].capacity) {
                        Vehiculos[VehIndex].carga += demanda;
                        Vehiculos[VehIndex].Addnodo(Candidato);
                        Vehiculos[VehIndex].tiempoRuta += tiempoRuta;
                        if (nodos[Vehiculos[VehIndex].CurLoc].IsStation == true) {
                            // Si no recargue todo lo que falte en la batería.
                            double cantidadH = calcularHoras(Vehiculos[VehIndex].carga,
                                    nodos[Vehiculos[VehIndex].CurLoc].tipo, nodos[Vehiculos[VehIndex].CurLoc].funCarga);
                            Vehiculos[VehIndex].tiempoRuta += cantidadH;
                            Vehiculos[VehIndex].carga -= nodos[Vehiculos[VehIndex].CurLoc].tiempoRecarga(cantidadH);
                        }
                        nodos[CustIndex].visitado = true;
                        this.Costo += minCost;
                    } else {// Si no se viola la restricción de la carga de la bateria.
                        int id = econtrarEstacionDeCarga(auxCostMatrix, nodos, Vehiculos[VehIndex].CurLoc);
                        if (nodos[id].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r, speed).second
                                + Vehiculos[VehIndex].tiempoRuta
                                + nodos[id].caldemand(0, auxCostMatrix, r, speed).second < Tmax) {
                            if (Vehiculos[VehIndex].CheckIfFits(
                                    nodos[id].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r, speed).first)) {
                                demanda = nodos[id].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r,
                                        speed).first;
                                tiempoRuta = nodos[id].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix, r,
                                        speed).second;
                                Vehiculos[VehIndex].carga += demanda;
                                Vehiculos[VehIndex].tiempoRuta += tiempoRuta;
                                nodos[id].visitado = true;
                                this.Costo += minCost;
                                double cantidadH = calcularHoras((Vehiculos[VehIndex].carga), nodos[id].tipo,
                                        nodos[id].funCarga);
                                Vehiculos[VehIndex].tiempoRuta += cantidadH;
                                Vehiculos[VehIndex].carga -= nodos[id].tiempoRecarga(cantidadH);
                                Vehiculos[VehIndex].Addnodo(nodos[id]);
                            } else { // Si no significa que deberia volver al deposito porque no puede moverse a una
                                     // estación a cargar.
                                Vehiculos[VehIndex].carga += nodos[0].caldemand(Vehiculos[VehIndex].CurLoc,
                                        auxCostMatrix, r, speed).first;
                                Vehiculos[VehIndex].tiempoRuta += nodos[0].caldemand(Vehiculos[VehIndex].CurLoc,
                                        auxCostMatrix, r, speed).second;
                                CostoFinal = CostMatrix[Vehiculos[VehIndex].CurLoc][0];
                                Vehiculos[VehIndex].Addnodo(nodos[0]);
                                this.Costo += CostoFinal;
                                if (VehIndex + 1 < Vehiculos.length) { // Si todavia hay más vehículos.
                                    VehIndex = VehIndex + 1; // Se mueve al siguiente vehículo.
                                }
                            }
                        } else { // Si no significa que se esta violando la condción del tiempo, entonces deberia
                                 // devolverse al deposito.
                            Vehiculos[VehIndex].carga += nodos[0].caldemand(Vehiculos[VehIndex].CurLoc, auxCostMatrix,
                                    r, speed).first;
                            Vehiculos[VehIndex].tiempoRuta += nodos[0].caldemand(Vehiculos[VehIndex].CurLoc,
                                    auxCostMatrix, r, speed).second;
                            CostoFinal = CostMatrix[Vehiculos[VehIndex].CurLoc][0];
                            Vehiculos[VehIndex].Addnodo(nodos[0]);
                            this.Costo += CostoFinal;
                            if (VehIndex + 1 < Vehiculos.length) { // Si todavia hay más vehículos.
                                VehIndex = VehIndex + 1; // Se mueve al siguiente vehículo.
                            }
                        }
                    }
                }
            }
        }
        // Se finaliza la ruta.
        CostoFinal = CostMatrix[Vehiculos[VehIndex].CurLoc][0];
        Vehiculos[VehIndex].Addnodo(nodos[0]);
        this.Costo += CostoFinal;
        double tiempoFinal = 0;
        for (vehiculo a : Vehiculos) {
            tiempoFinal += a.tiempoRuta;
        }
        return tiempoFinal;
    }

    public void imprimirSolucion(String nombre) {
        for (int j = 0; j < numeroVehiculos; j++) {
            if (!Vehiculos[j].Route.isEmpty()) {
                System.out.print("Vehículo " + j + ":");
                cantVehiculos++;
                int longitudRuta = Vehiculos[j].Route.size();
                System.out.print("[");
                for (int k = 0; k < longitudRuta; k++) {
                    System.out.print("{");
                    if (k == longitudRuta - 1) {
                        System.out.print(Vehiculos[j].Route.get(k).nodoId + "}");
                    } else {
                        System.out.print(Vehiculos[j].Route.get(k).nodoId + "},");
                    }
                }
                System.out.print("]");
                System.out.println();
            }
        }
    }

    public int econtrarEstacionDeCarga(double[][] auxCostMatrix, nodo[] nodos, int PosActual) {
        int mejorEstacion = -1;
        double mejorDistancia = Double.MAX_VALUE;
        for (int i = 0; i < nodos.length; i++) {
            if (nodos[i].IsStation && i != PosActual) {
                if (auxCostMatrix[PosActual][i] < mejorDistancia) {
                    mejorEstacion = nodos[i].nodoId;
                    mejorDistancia = auxCostMatrix[PosActual][i];
                }
            }
        }
        return mejorEstacion;
    }

    public double calcularHoras(double carga, int tipoEstacion, float[] pendienteCarga) {
        return carga / pendienteCarga[tipoEstacion];
    }
}