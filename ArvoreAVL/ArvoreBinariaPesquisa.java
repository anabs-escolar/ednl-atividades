import java.util.Iterator;
import java.util.ArrayList;

public class ArvoreBinariaPesquisa {

    // Atributos da árvore
    private No raiz;
    private int tam;

    // Construtor
    public ArvoreBinariaPesquisa(Object o, int k) {
        this.raiz = new No(null, o, k);
        this.tam = 1;
    }

    
    public No leftChild(No v){ 
        return v.left(); 
    }

    public No rightChild(No v){ 
        return v.right(); 
    }
    
    public boolean hasLeft(No v){ 
        return v.left() != null; 
    }

    public boolean hasRight(No v){ 
        return v.right() != null; 
    }

    public No find(int k, No v){
        if (isExternal(v)) return v;
        
        if (k < v.key()) return find(k, v.left());
        else if (k == v.key()) return v;
        else return find(k, v.right());
    }

    public void insert(Object o, int key){
        No cur = root();

        while(true){
            if(key < cur.key()){
                if(!hasLeft(cur)){
                    No n = new No(cur, o, key);
                    cur.setLeft(n);
                    tam++;
                    return;
                }
                cur = cur.left();
            } else {
                if(!hasRight(cur)){
                    No n = new No(cur, o, key);
                    cur.setRight(n);
                    tam++;
                    return;
                }
                cur = cur.right();
            }
        }
    }

    public Object remove(No v){
        Object old = v.element();

        // caso 1: folha
        if(isExternal(v)){
            No pai = v.parent();
            if(pai != null) {
                if(pai.left() == v) pai.setLeft(null);
                else pai.setRight(null);
            } else {
                raiz = null; // Removendo a raiz que não tem filhos
            }
            tam--;
            return old;
        }

        // caso 2: dois filhos
        if(hasLeft(v) && hasRight(v)){
            No suc = min(v.right());
            v.setElement(suc.element());
            v.setKey(suc.key());
            remove(suc); // O tam-- acontece dentro desta chamada recursiva
            return old;
        }

        // caso 3: um único filho
        No filho = hasLeft(v) ? v.left() : v.right();
        No pai = v.parent();

        if (pai != null) {
            if (pai.left() == v) pai.setLeft(filho);
            else pai.setRight(filho);
        } else {
            raiz = filho; // Removendo a raiz que tem apenas um filho
        }
        
        // Atualiza quem é o novo pai desse filho que subiu
        if (filho != null) {
            filho.setParent(pai);
        }
        
        tam--;
        return old;
    }

    private No min(No v){
        while(v.left() != null)
            v = v.left();
        return v;
    }

    // Métodos comuns de Arvore
    public No root() { 
        return raiz; 
    }

    public void setRoot(No r){
        this.raiz = r;
    }
    public No parent(No v) { 
        return v.parent(); 
    }

    public Iterator children(No v) { 
        return v.children(); 
    }

    public boolean isInternal(No v) { 
        return v.childrenNumber() > 0; 
    }

    public boolean isExternal(No v) { 
        return v.childrenNumber() == 0; 
    }

    public boolean isRoot(No v) { 
        return v == raiz; 
    }

    public void swapElements(No v, No w) {
        Object temp = v.element();
        v.setElement(w.element());
        w.setElement(temp);
    }
    
    public int depth(No v) { return profundidade(v); }

    private int profundidade(No v) {
        if (v == raiz || v == null) return 0;
        else return 1 + profundidade(v.parent());
    }

    public int height() {
        int altura = 0;
        Iterator it = Nos();

        while(it.hasNext()){
            No no = (No) it.next();
            int profundidade = depth(no);
            if (profundidade > altura) altura = profundidade;
        }
        return altura;
    }

    public Iterator elements() {
        ArrayList els = new ArrayList();
        Iterator it = Nos();
        while(it.hasNext()){
            No no = (No) it.next();
            els.add(no.element());
        }
        return els.iterator();
    }

    public Iterator Nos() {
        ArrayList nos = new ArrayList();
        addNos(root(), nos);
        return nos.iterator();
    }

    private void addNos(No n, ArrayList a){
        if(n == null) return;
        a.add(n);
        Iterator it = n.children();
        while(it.hasNext()){
            No filho = (No) it.next();
            addNos(filho, a);
        }
    }

    public int size() { return this.tam; }
    public boolean isEmpty() { return raiz == null; }

    public Object replace(No v, Object o) {
        Object old = v.element();
        v.setElement(o);
        return old;
    }

    public void mostrar() { mostrar(root(), 0); }

    private void mostrar(No n, int nivel) {
        int h = height();
        ArrayList<No> nivelNos = new ArrayList<>();
        nivelNos.add(root());

        for (int i = 0; i <= h; i++) {
            int espacos = (int)Math.pow(2, h - i);
            imprimirEspacos(espacos);
            ArrayList<No> prox = new ArrayList<>();

            for (No no : nivelNos) {
                if (no != null) {
                    System.out.print(no.key());
                    prox.add(no.left());
                    prox.add(no.right());
                } else {
                    System.out.print(" ");
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
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    public class No {
        private Object o;
        private int chave;
        private No pai;
        private No left;
        private No right;
        private int fb; // Fator de Balenceamento

        public No(No pai, Object o, int k) {
            this.pai = pai;
            this.o = o;
            this.chave = k;
            this.left = null;
            this.right = null;
            this.fb = 0;
        }

        public int key() { return chave; }
        public void setKey(int k) { this.chave = k; }

        public Object element() { return o; }
        public void setElement(Object o) { this.o = o; }

        public No parent() { return pai; }
        public void setParent(No pai) { this.pai = pai; }

        public No left() { return left; }
        public void setLeft(No left) { this.left = left; }

        public No right() { return right; }
        public void setRight(No right) { this.right = right; }

        public int getFb() { return fb; }
        public void setFb(int fb) { this.fb = fb; }

        public int childrenNumber() {
            int count = 0;
            if (this.left != null) count++;
            if (this.right != null) count++;
            return count;
        }

        public Iterator children() {
            ArrayList<No> filhosTemp = new ArrayList<>();
            if (this.left != null) filhosTemp.add(this.left);
            if (this.right != null) filhosTemp.add(this.right);
            return filhosTemp.iterator();
        }
    }
}