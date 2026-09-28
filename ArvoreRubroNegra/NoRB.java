import java.util.ArrayList;
import java.util.Iterator;

public class NoRB {

    private Object o;
    private int chave;

    private NoRB pai;
    private NoRB left;
    private NoRB right;

    private boolean rubro;

    public NoRB(NoRB pai, Object o, int chave, boolean rubro) {
        this.pai = pai;
        this.o = o;
        this.chave = chave;
        this.rubro = rubro;

        this.left = null;
        this.right = null;
    }

    public int key() {
        return chave;
    }

    public void setKey(int chave) {
        this.chave = chave;
    }

    public Object element() {
        return o;
    }

    public void setElement(Object o) {
        this.o = o;
    }

    public NoRB parent() {
        return pai;
    }

    public void setParent(NoRB pai) {
        this.pai = pai;
    }

    public NoRB left() {
        return left;
    }

    public void setLeft(NoRB left) {
        this.left = left;
    }

    public NoRB right() {
        return right;
    }

    public void setRight(NoRB right) {
        this.right = right;
    }

    public boolean isRubro() {
        return rubro;
    }

    public boolean isNegro() {
        return !rubro;
    }

    public void setRubro() {
        this.rubro = true;
    }

    public void setNegro() {
        this.rubro = false;
    }

    public int childrenNumber() {
        int count = 0;

        if (left != null) {
            count++;
        }

        if (right != null) {
            count++;
        }

        return count;
    }

    public Iterator children() {
        ArrayList<NoRB> filhos = new ArrayList<>();

        if (left != null) {
            filhos.add(left);
        }

        if (right != null) {
            filhos.add(right);
        }

        return filhos.iterator();
    }
}