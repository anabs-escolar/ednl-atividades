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
        return v != null && v.isRubro();
    }

    public boolean isNegro(NoRB v){
        return v == null || v.isNegro();
    }

    public boolean isExternal(NoRB v) {
    return v == null;
    }

    public boolean isInternal(NoRB v) {
        return v != null;
    }

    public Iterator children(NoRB v) {
        return v.children();
    }

    public NoRB find(int k, NoRB v) {
        if (v == null) 
            return null;

        if (k < v.key()) 
            return find(k, v.left());
        else if (k == v.key())
            return v;
        else
            return find(k, v.right());
        
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

    private void rotacaoEsquerda(NoRB x) {
        NoRB y = x.right();
        if (y == null) return;

        NoRB b = y.left();
        x.setRight(b);

        if (b != null)
            b.setParent(x);

        NoRB pai = x.parent();
        y.setParent(pai);

        if (pai == null)
            setRoot(y);
        else if (x == pai.left())
            pai.setLeft(y);
        else
            pai.setRight(y);

        y.setLeft(x);
        x.setParent(y);
    }

    private void rotacaoDireita(NoRB x) {
        NoRB y = x.left();
        if (y == null) return;

        NoRB b = y.right();
        x.setLeft(b);

        if (b != null)
            b.setParent(x);

        NoRB pai = x.parent();
        y.setParent(pai);

        if (pai == null)
            setRoot(y);
        else if (x == pai.left())
            pai.setLeft(y);
        else
            pai.setRight(y);

        y.setRight(x);
        x.setParent(y);
    }


    public Object remove(NoRB v) {
        if (v == null)
            return null;

        Object old = v.element();
        NoRB y = v;
        boolean yRubro = y.isRubro();

        NoRB x;
        NoRB paiX;
        boolean xEsquerdo;

        // CASO 1: sem filho esquerdo

        if (v.left() == null) {
            x = v.right();
            paiX = v.parent();
            xEsquerdo = paiX != null && v == paiX.left();
            transplant(v, v.right());
        }

        // CASO 2: sem filho direito
        else if (v.right() == null) {
            x = v.left();
            paiX = v.parent();
            xEsquerdo = paiX != null && v == paiX.left();
            transplant(v, v.left());
        }

        // CASO 3: dois filhos

        else {
            y = minimo(v.right());
            yRubro = y.isRubro();
            x = y.right();

            // sucessor é filho direto de v
            if (y.parent() == v) {
                paiX = y;
                xEsquerdo = false;
                if (x != null)
                    x.setParent(y);
            }
            // sucessor está mais abaixo

            else {
                paiX = y.parent();
                xEsquerdo = true;
                transplant(y, y.right());

                y.setRight(v.right());
                y.right().setParent(y);
            }

            // y ocupa o lugar de v
            transplant(v, y);

            y.setLeft(v.left());
            y.left().setParent(y);

            // y recebe a cor que v tinha
            if (v.isRubro())
                y.setRubro();
            else
                y.setNegro();
        }

        tam--;

        if (yRubro)
            return old;

        if (isRubro(x)) {
            x.setNegro();
            return old;
        }

        correcaoRemove(x, paiX, xEsquerdo);

        return old;
    }

    private NoRB minimo(NoRB v) {
        while (v.left() != null)
            v = v.left();

        return v;
    }
    private void transplant(NoRB u, NoRB v) {
        NoRB pai = u.parent();

        if (pai == null) {
            raiz = v;
        }
        else if (u == pai.left()) {
            pai.setLeft(v);
        }
        else {
            pai.setRight(v);
        }

        if (v != null)
            v.setParent(pai);
    }

    private void correcaoRemove(NoRB x, NoRB paiX, boolean xEsquerdo) {
        while (x != raiz && isNegro(x)) {

        if (paiX == null)
            break;

        if (xEsquerdo) {
            NoRB w = paiX.right();
            if (isRubro(w)) {
                w.setNegro();
                paiX.setRubro();
                rotacaoEsquerda(paiX);
                w = paiX.right();
            }

            NoRB esquerdoW = null;
            NoRB direitoW = null;

            if (w != null) {
                esquerdoW = w.left();
                direitoW = w.right();
            }

            if (isNegro(esquerdoW) && isNegro(direitoW)) {
                if (w != null)
                    w.setRubro();

                // CASO 2b
                if (isRubro(paiX)) {
                    paiX.setNegro();
                    break;
                }

                // CASO 2a
                x = paiX;
                paiX = x.parent();
                if (paiX != null)
                    xEsquerdo = x == paiX.left();
                continue;
            }

            // CASO 3
            if (isNegro(direitoW)) {
                if (esquerdoW != null)
                    esquerdoW.setNegro();

                if (w != null) {
                    w.setRubro();
                    rotacaoDireita(w);
                }
                w = paiX.right();
                direitoW = w != null ? w.right() : null;
            }

            // CASO 4
            if (w != null) {
                if (paiX.isRubro())
                    w.setRubro();
                else
                    w.setNegro();
            }

            paiX.setNegro();
            if (w != null && w.right() != null)
                w.right().setNegro();

            rotacaoEsquerda(paiX);

            x = raiz;
            paiX = null;
        }
        // x na dir
        else {
            NoRB w = paiX.left();
            // CASO 1
            if (isRubro(w)) {
                w.setNegro();
                paiX.setRubro();
                rotacaoDireita(paiX);

                w = paiX.left();
            }

            NoRB esquerdoW = null;
            NoRB direitoW = null;

            if (w != null) {
                esquerdoW = w.left();
                direitoW = w.right();
            }
            // CASO 2

            if (isNegro(esquerdoW) && isNegro(direitoW)) {
                if (w != null)
                    w.setRubro();
                // CASO 2b
                if (isRubro(paiX)) {
                    paiX.setNegro();
                    break;
                }
                // CASO 2a
                x = paiX;
                paiX = x.parent();
                if (paiX != null)
                    xEsquerdo = x == paiX.left();
                continue;
            }

            // CASO 3

            if (isNegro(esquerdoW)) {
                if (direitoW != null)
                    direitoW.setNegro();
                if (w != null) {
                    w.setRubro();
                    rotacaoEsquerda(w);
                }
                w = paiX.left();

                esquerdoW = w != null ? w.left() : null;
            }

            // CASO 4
            // filho esquerdo RUBRO
            if (w != null) {
                if (paiX.isRubro())
                    w.setRubro();
                else
                    w.setNegro();
            }
            paiX.setNegro();

            if (w != null && w.left() != null)
                w.left().setNegro();

            rotacaoDireita(paiX);
            x = raiz;
            paiX = null;
        }
    }

    if (x != null)
        x.setNegro();
    }


    public void mostrar() {
        mostrar(root(), 0);
    }

    private void mostrar(NoRB no, int nivel) {
        if (root() == null) {
            System.out.println("Árvore vazia");
            return;
        }
        int h = height();

        ArrayList<NoRB> nivelNos = new ArrayList<>();
        nivelNos.add(root());

        for (int i = 0; i <= h; i++) {
            int espacos = (int) Math.pow(2, h - i);
            imprimirEspacos(espacos);
            ArrayList<NoRB> prox = new ArrayList<>();
            for (NoRB n : nivelNos) {
                if (n != null) {
                    // Mostra chave + cor
                    if (n.isRubro())
                        System.out.print(n.key() + "(R)");
                    else
                        System.out.print(n.key() + "(N)");

                    prox.add(n.left());
                    prox.add(n.right());

                } else {
                    System.out.print("   ");
                    prox.add(null);
                    prox.add(null);
                }
                imprimirEspacos(espacos * 2);
            }
            System.out.println();
            nivelNos = prox;
        }
    }

    private void imprimirEspacos(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(" ");
    }

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
        if (no == null) return;
        lista.add(no);

        addNos(no.left(), lista);
        addNos(no.right(), lista);
    }

    public int depth(NoRB v) {
        if (v == null || v == raiz) 
            return 0;
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