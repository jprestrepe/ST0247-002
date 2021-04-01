/**
 * Clase que contiene toda la info (menos las cordenadas) de las estaciones
 */
public class NodoEstacion{
    int bLenta; 
    int bMedia; 
    int bRapida;  
    double cargaRapida;
    double cargaMedia;
    double cargaLenta;

    /**
     * Constructor l
     *  el cual se encarga de que tipo de carga tiene la estacion
     * @param cargaLenta    
     * @param cargaMedia
     * @param cargaRapida
     */
    public NodoEstacion(double cargaLenta, double cargaMedia, double cargaRapida) {
        this.cargaLenta = cargaLenta;
        this.cargaMedia = cargaMedia;
        this.cargaRapida = cargaRapida;
    }

    /**
     * Constructor g
     *  Nivel de la bateria en watts hora para cada tipo de estación y para punto de soporte
     * @param bLenta
     * @param bMedia
     * @param bRapida
     */
    public NodoEstacion(int bLenta, int bMedia, int bRapida){
        this.bRapida = bRapida;
        this.bLenta = bLenta;
        this.bMedia = bMedia;
    }
    
    public String toStringg() {
        return (cargaLenta + " " + cargaMedia + " " + cargaRapida);
    }

    public double getCargaRapida() {
        return cargaRapida;
    }

    public double getCargaMedia() {
        return cargaMedia;
    }

    public String toStringl(){
        return (bLenta + " " + bMedia + " " + bRapida);
    }
    
    public int getBLenta(){
        return bLenta;
    }
    public int getBMedia(){
        return bMedia;
    }
    public int getBRapida(){
        return bRapida;
    }
}
