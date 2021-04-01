import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;

public class Solucion{
    int NoOfVehicles;
    int NoOfCustomers;
    vehiculo[] Vehicles;
    double Cost;

    // Tabu Variables
    public vehiculo[] VehiclesForBestSolution;
    double BestSolutionCost;

    public ArrayList<Double> PastSolutions;

    public Solucion(int CustNum, int VechNum, double VechCap) {
        this.NoOfVehicles = VechNum;
        this.NoOfCustomers = CustNum;
        this.Cost = 0;
        Vehicles = new vehiculo[NoOfVehicles];
        VehiclesForBestSolution = new vehiculo[NoOfVehicles];
        PastSolutions = new ArrayList<>();

        for (int i = 0; i < NoOfVehicles; i++) {
            Vehicles[i] = new vehiculo(i + 1, VechCap);
            VehiclesForBestSolution[i] = new vehiculo(i + 1, VechCap);
        }
    }

    public boolean UnassignedCustomerExists(Node[] Nodes) {
        for (int i = 1; i < Nodes.length; i++) {
            if (!Nodes[i].IsRouted)
                return true;
        }
        return false;
    }

    public void SolucionConGreedy(Node[] Nodes, double[][] CostMatrix, double r, double speed, double Tmax) {

        double CandCost, EndCost, tiempoRuta = 0, demand = 0;
        int VehIndex = 0;
        while (UnassignedCustomerExists(Nodes)) {
            int CustIndex = 0;
            Node Candidate = null;
            double minCost = (float) Double.MAX_VALUE;

            if (Vehicles[VehIndex].Route.isEmpty()) {
                Vehicles[VehIndex].AddNode(Nodes[0]);
            }
            double[][] pCostMatrix = CostMatrix;

            for (int i = 1; i <= NoOfCustomers; i++) {
                if (Nodes[i].IsRouted == false) {
                    if (Vehicles[VehIndex]
                            .CheckIfFits(Nodes[i].caldemand(Vehicles[VehIndex].CurLoc, pCostMatrix, r, speed).first)) {
                        CandCost = CostMatrix[Vehicles[VehIndex].CurLoc][i];
                        if (minCost > CandCost) {
                            minCost = CandCost;
                            tiempoRuta = Nodes[i].caldemand(Vehicles[VehIndex].CurLoc, pCostMatrix, r, speed).second;
                            demand = Nodes[i].caldemand(Vehicles[VehIndex].CurLoc, pCostMatrix, r, speed).first;
                            CustIndex = i;
                            Candidate = Nodes[i];
                        }
                    }
                }
            }

            if (Candidate == null) {
                // Not a single Customer Fits
                if (VehIndex + 1 < Vehicles.length) // We have more vehicles to assign
                {
                    if (Vehicles[VehIndex].CurLoc != 0) {// End this route
                        EndCost = CostMatrix[Vehicles[VehIndex].CurLoc][0];
                        Vehicles[VehIndex].AddNode(Nodes[0]);
                        this.Cost += EndCost;
                    }
                    VehIndex = VehIndex + 1; // Go to next vehiculo
                } else // We DO NOT have any more vehiculo to assign. The problem is unsolved under
                       // these parameters
                {
                    System.out.println("\nThe rest customers do not fit in any vehiculo\n"
                            + "The problem cannot be resolved under these constrains");
                    System.exit(0);
                }
            } else {
                if (!Candidate.IsStation && !Candidate.IsDepot) {
                    tiempoRuta += 0.5;
                }
                if (Nodes[Vehicles[VehIndex].CurLoc].IsStation == true) {
                    if (Candidate.IsStation) {
                        double cantidadH = calcularHoras(demand, Nodes[CustIndex].tipo,
                                Nodes[CustIndex].pendienteFuncionCarga);
                        Vehicles[VehIndex].tiempoRuta += cantidadH;
                        Vehicles[VehIndex].carga -= Nodes[CustIndex].calcularTiempoRecarga(cantidadH);
                    } else {
                        double cantidadH = calcularHoras(Vehicles[VehIndex].carga + demand,
                                Nodes[Vehicles[VehIndex].CurLoc].tipo,
                                Nodes[Vehicles[VehIndex].CurLoc].pendienteFuncionCarga);
                        Vehicles[VehIndex].tiempoRuta += cantidadH;
                        Vehicles[VehIndex].carga -= Nodes[Vehicles[VehIndex].CurLoc].calcularTiempoRecarga(cantidadH);
                    }
                }
                if (Vehicles[VehIndex].tiempoRuta + tiempoRuta > Tmax) {
                    Vehicles[VehIndex].carga += Nodes[0].caldemand(Vehicles[VehIndex].CurLoc, pCostMatrix, r,
                            speed).first;
                    Vehicles[VehIndex].tiempoRuta += Nodes[0].caldemand(Vehicles[VehIndex].CurLoc, pCostMatrix, r,
                            speed).second;
                    EndCost = CostMatrix[Vehicles[VehIndex].CurLoc][0];
                    Vehicles[VehIndex].AddNode(Nodes[0]);
                    this.Cost += EndCost;
                    if (VehIndex + 1 < Vehicles.length) {
                        VehIndex = VehIndex + 1;
                    }
                } else {
                    Vehicles[VehIndex].AddNode(Candidate);// If a fitting Customer is Found
                    Vehicles[VehIndex].carga += demand;
                    Vehicles[VehIndex].tiempoRuta += tiempoRuta;
                    Nodes[CustIndex].IsRouted = true;
                    this.Cost += minCost;
                }
            }
        }
        EndCost = CostMatrix[Vehicles[VehIndex].CurLoc][0];
        Vehicles[VehIndex].AddNode(Nodes[0]);
        this.Cost += EndCost;
        System.out.println("El tiempo de la ruta es " + Vehicles[VehIndex].tiempoRuta);
    }

    public double calcularHoras(double carga, int tipoEstacion, float[] pendienteCarga) {
        return carga / pendienteCarga[tipoEstacion];
    }

    public int findStation(Node[] nodes, double[][] CostMatrix, int CurLoc) {
        int index = 0;
        double costoMin = Double.MAX_VALUE;
        double distancia;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].IsStation && CurLoc != i) {
                distancia = CostMatrix[CurLoc][i];
                if (distancia < costoMin) {
                    costoMin = distancia;
                    index = i;
                }
            }
        }
        return index;
    }

    public void SaveBestSolution() {
        BestSolutionCost = Cost;
        for (int j = 0; j < NoOfVehicles; j++) {
            VehiclesForBestSolution[j].Route.clear();
            if (!Vehicles[j].Route.isEmpty()) {
                int RoutSize = Vehicles[j].Route.size();
                for (int k = 0; k < RoutSize; k++) {
                    Node n = Vehicles[j].Route.get(k);
                    VehiclesForBestSolution[j].Route.add(n);
                }
            }
        }
    }

    public void SolutionPrint(String Solution_Label)// Print Solucion In console
    {
        for (int j = 0; j < NoOfVehicles; j++) {
            if (!Vehicles[j].Route.isEmpty()) {
                System.out.print("[");
                int RoutSize = Vehicles[j].Route.size();
                for (int k = 0; k < RoutSize; k++) {
                    if (k == RoutSize - 1) {
                        System.out.print("{" + Vehicles[j].Route.get(k).NodeId + "}");
                    } else {
                        System.out.print("{" + Vehicles[j].Route.get(k).NodeId + "}" + ",");
                    }
                }
                System.out.println("]");
            }
        }
    }
}