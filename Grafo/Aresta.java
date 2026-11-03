

public class Aresta {

    private Vertice origem;
    private Vertice destino;
    private Object elemento;
    private boolean direcionada;

    public Aresta(Vertice o, Vertice d, Object elemento, boolean direcionada) {
        this.origem = o;
        this.destino = d;
        this.elemento = elemento;
        this.direcionada = direcionada;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public boolean isDirecionada() {
        return direcionada;
    }

    @Override
    public String toString() {
        return origem + (direcionada ? " -> " : " -- ") + destino + " (" + elemento + ")";
    }
}