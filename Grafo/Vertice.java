

public class Vertice {

    private Object elemento;
    private int index;

    public Vertice(Object el, int i) {
        this.elemento = el;
        this.index = i;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return String.valueOf(elemento);
    }
}