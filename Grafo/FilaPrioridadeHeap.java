import java.util.ArrayList;
import java.util.Iterator;

public class FilaPrioridadeHeap {
    private No raiz;
    private int tam;

    public FilaPrioridadeHeap(){
        tam = 0;
    }

    public class Item {
        private Object element;
        private double chave;

        public Item(Object el, double chave) {
            this.element = el;
            this.chave = chave;
        }

        public Object value() {return element;}

        public double key() {return chave;}
    }

    public class No {
        private Item element;
        private No pai;
        private ArrayList filhos = new ArrayList();

        public No(No pai, Item o) {
            this.pai = pai;
            this.element = o;
        }

        public Item element() {return element;}

        public No parent() {return pai;}

        public void setElement(Item o) {
            this.element = o;
        }

        public void addChild(No o) {
            filhos.add(o);
        }

        public void removeChild(No o) {
            filhos.remove(o);
        }

        public int childrenNumber() {
            return filhos.size();
        }

        public Iterator children() {
            return filhos.iterator();
        }
        
        // métodos para acessar o filho da esquerda e o filho da direita
        // esquerda é 0 e direita é 1

        public No left() {
            if (filhos.size() > 0)
                return (No) filhos.get(0);
            return null;
        }
        
        public No right() {
            if (filhos.size() > 1)
                return (No) filhos.get(1);
            return null;
        }
    }

    public void insert(Object o, double key) {
        Item item = new Item(o, key);
        
        if (raiz == null){
            raiz = new No(null, item);
            tam++;
            return;
        }
        // encontra pai para inserir
        No pai = seachInsertParent();
        No n_no = new No(pai, item);
        pai.addChild(n_no);
        tam++;
        // faz o uphead
        upHeap(n_no);
    }

    public Object removeMin() {
        if (raiz == null)
            return null;
        Object old = raiz.element().value();

        if (tam == 1){
            raiz = null;
            tam--;
            return old;
        }
        No ult = searchLastNode();
        raiz.setElement(ult.element());
        ult.parent().removeChild(ult);

        tam--;
        downHeap(raiz);

        return old;
    }

    public Object min() {
        if (raiz == null)
            return null;
        return raiz.element().value();
    }
    
    public int size() {
        return tam;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private void upHeap(No n){
        // enquanto pai não nulo e chave do item menor que a chave do pai
        while (n.parent() != null && n.element().key() < n.parent().element().key()){
            Item temp = n.element();

            n.setElement(n.parent().element());
            n.parent().setElement(temp);

            n = n.parent();
        }
    }

    private void downHeap(No n){
        while(true){
            No menor = n;
            // comparação nos filhos
            if (n.left() != null && n.left().element().key() < menor.element().key())
                menor = n.left();

            if (n.right() != null && n.right().element().key() < menor.element().key())
                menor = n.right();
            
            if (menor == n)
                break;

            Item tmp = n.element();
            n.setElement(menor.element());
            menor.setElement(tmp);
            n = menor;
        }
    }

    private No seachInsertParent(){
        ArrayList<No> arr = new ArrayList();
        arr.add(raiz);
        for(int i = 0; i < arr.size(); i++){
            No cur = arr.get(i);
            // retorna o pai com menos de dois filhos
            if (cur.childrenNumber() < 2)
                return cur;
    
            arr.add(cur.left());
            arr.add(cur.right());
        }

        return null;
    }

    private No searchLastNode(){
        ArrayList<No> arr = new ArrayList();
        arr.add(raiz);
        No last = null;
        for (int i = 0; i < arr.size(); i++){
            // buscando pelo No sem filho
            last = arr.get(i);

            if (last.left() != null)
                arr.add(last.left());
            
            if (last.right() != null)
                arr.add(last.right());

        }
        return last;
    }


}