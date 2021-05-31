/**
 * Clase para representar parejas.
 *
 * @author Juan José Sánchez
 */
public class Pair<F, S> {
	public final F first; // Primer elemento de la pareja.
	public final S second;// Segundo elemento de la pareja.

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

}