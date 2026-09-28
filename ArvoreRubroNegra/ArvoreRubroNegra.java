import java.util.ArrayList;
import java.util.Iterator;

public class ArvoreRubroNegra {

    private NoRB raiz;
    private int tam;

    public ArvoreRubroNegra() {
        this.raiz = null;
        this.tam = 0;
    }

    public NoRB root() {
        return raiz;
    }

    public void setRoot(NoRB r) {
        this.raiz = r;
    }

    public int size() {
        return tam;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public NoRB parent(NoRB v) {
        return v.parent();
    }

    public NoRB leftChild(NoRB v) {
        return v.left();
    }

    public NoRB rightChild(NoRB v) {
        return v.right();
    }

    public boolean hasLeft(NoRB v) {
        return v.left() != null;
    }

    public boolean hasRight(NoRB v) {
        return v.right() != null;
    }

    public boolean isRoot(NoRB v) {
        return v == raiz;
    }

    public boolean isRubro(NoRB v){
        return v != null && no.isRubro();
    }

    public boolean isExternal(NoRB v) {
        return v != null
                && v.left() == null
                && v.right() == null;
    }

    public boolean isInternal(NoRB v) {
        return v != null && (v.left() != null || v.right() != null);
    }

    public Iterator children(NoRB v) {
        return v.children();
    }

    public NoRB find(int k, NoRB v) {

        if (v == null) {
            return null;
        }

        if (k < v.key()) {
            return find(k, v.left());

        } else if (k == v.key()) {
            return v;

        } else {
            return find(k, v.right());
        }
    }

    public void insert(Object o, int k) {
        if (root() == null){
            //arvore vazia
            NoRB novo = new NoRB(null, o, k, true);
            raiz = novo;
            tam++;
            novo.setNegro();
            return;
        }

        NoRB atual = raiz;
        NoRB pai = null;

        while (atual != null){
            pai = atual;
            if (k < atual.key()) {
                atual = atual.left();
            } else {
                atual = atual.right();
            }
        }
        // novo rubro
        NoRB n = new NoRB(pai, o, k, true);

        if (k < pai.key()){
            pai.setLeft(n);
        } else {
            pai.setRight(n);
        }

        tam++;
        correcaoInsert(n);
    }

    private void correcaoInsert(NoRB z) {
        while (z != raiz && z.parent() != null && z.parent().isRubro()) {
            NoRB pai = z.parent();
            NoRB avo = pai.parent();
            
            if (pai == avo.left()){
                NoRB tio = avo.right();
                if (isRubro(tio)){
                    pai.setNegro();
                    tio.setNegro();
                    avo.setRubro();
                    z = avo;
                } else {
                    if (z == pai.right()){
                        z = pai;
                        rotacaoEsquerda(z);
                        pai = z.parent();
                        avo = pai.parent();
                
                    }
                    pai.setNegro();
                    avo.setRubro();
                    rotacaoDireita(avo);
                }
            } else {
                NoRB tio = avo.left();
                if (isRubro(tio)) {
                    pai.setNegro();
                    tio.setNegro();
                    avo.setRubro();
                    z = avo;
                } else {
                    if (z == pai.left()) {
                        z = pai;
                        rotacaoDireita(z);
                        pai = z.parent();
                        avo = pai.parent();
                    }
                    pai.setNegro();
                    avo.setRubro();
                    rotacaoEsquerda(avo);
                }
            }
        }
        raiz.setNegro();
    }

    // =========================================================
    // ROTAÇÃO À ESQUERDA
    // =========================================================

    private void rotacaoEsquerda(NoRB x) {

    }

    // =========================================================
    // ROTAÇÃO À DIREITA
    // =========================================================

    private void rotacaoDireita(NoRB x) {

    }

    // =========================================================
    // REMOÇÃO
    // =========================================================

    public Object remove(NoRB v) {
        return null;
    }

    // =========================================================
    // CORREÇÃO DA REMOÇÃO
    // =========================================================

    private void correcaoRemove() {

    }

    // =========================================================
    // ELEMENTOS
    // =========================================================

    public Iterator elements() {
        ArrayList<Object> els = new ArrayList<>();

        Iterator it = nos();

        while (it.hasNext()) {
            NoRB no = (NoRB) it.next();
            els.add(no.element());
        }

        return els.iterator();
    }

    public Iterator nos() {
        ArrayList<NoRB> lista = new ArrayList<>();

        addNos(root(), lista);

        return lista.iterator();
    }

    private void addNos(NoRB no, ArrayList<NoRB> lista) {

        if (no == null) {
            return;
        }

        lista.add(no);

        addNos(no.left(), lista);
        addNos(no.right(), lista);
    }

    // =========================================================
    // ALTURA
    // =========================================================

    public int depth(NoRB v) {
        if (v == null || v == raiz) {
            return 0;
        }

        return 1 + depth(v.parent());
    }

    public int height() {

        int altura = 0;

        Iterator it = nos();

        while (it.hasNext()) {

            NoRB no = (NoRB) it.next();

            int profundidade = depth(no);

            if (profundidade > altura) {
                altura = profundidade;
            }
        }

        return altura;
    }
}