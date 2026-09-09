import java.util.ArrayList;

public class ArvoreAVL extends ArvoreBinariaPesquisa {

    public ArvoreAVL(Object o, int k) {
        super(o, k);
    }

    public No find(int k, No n) {
        return super.find(k, n);
    }

    public void insert(Object o, int key) {
        if (isEmpty()) {
            super.insert(o, key);
            return;
        }

        No cur = root();
        No n = null; // novo no
        
        while (true) {
            if (key < cur.key()) {
                if (!hasLeft(cur)) {
                    n = new No(cur, o, key);
                    cur.setLeft(n);
                    break; 
                }
                cur = cur.left();
            } else {
                if (!hasRight(cur)) {
                    n = new No(cur, o, key);
                    cur.setRight(n);
                    break;
                }
                cur = cur.right();
            }
        }

        insertBalance(n);
    }

    private void insertBalance(No no) {
        while (no.parent() != null) {
            No pai = no.parent();

            if (pai.left() == no) pai.setFb(pai.getFb() + 1);
            else pai.setFb(pai.getFb() - 1);

            if (pai.getFb() == 0) break;

            if (pai.getFb() == 2) {
                if (pai.left().getFb() >= 0) rotacaoSimplesDir(pai);
                else rotacaoDuplaDir(pai);
                break;
            } else if (pai.getFb() == -2) {
                if (pai.right().getFb() <= 0) rotacaoSimplesEsq(pai);
                else rotacaoDuplaEsq(pai);
                break;
            }
            no = pai;
        }
    }

    public Object remove(No no) {
        if (hasLeft(no) && hasRight(no)) {
            return super.remove(no);
        }
        
        No pai = no.parent(); 
        boolean removeEsq = (pai != null && pai.left() == no);

        Object old = super.remove(no);

        if (pai != null) {
            removeBalance(pai, removeEsq);
        }

        return old;
    }
    
    private void removeBalance(No pai, boolean foiEsq) {
        while (pai != null) {
            if (foiEsq) pai.setFb(pai.getFb() - 1);
            else pai.setFb(pai.getFb() + 1);

            No avo = pai.parent();
            boolean paiEraEsq = (avo != null && avo.left() == pai); // Corrigido erro de sintaxe

            if (pai.getFb() == 2) {
                No filhoEsq = pai.left();
                if (filhoEsq.getFb() >= 0) rotacaoSimplesDir(pai);
                else rotacaoDuplaDir(pai);
            } else if (pai.getFb() == -2) {
                No filhoDir = pai.right();
                if (filhoDir.getFb() <= 0) rotacaoSimplesEsq(pai);
                else rotacaoDuplaEsq(pai);
            }

            int fb = (pai.parent() != avo) ? pai.parent().getFb() : pai.getFb();
            if (fb == 1 || fb == -1) {
                break;
            }   

            pai = avo;
            foiEsq = paiEraEsq;
        }
    }

    public void mostrar() {
        int h = height();
        ArrayList<No> nivelNos = new ArrayList<>();
        nivelNos.add(root());

        for (int i = 0; i <= h; i++) {
            int espacos = (int)Math.pow(2, h - i);
            imprimirEspacos(espacos);
            ArrayList<No> prox = new ArrayList<>();

            for (No no : nivelNos) {
                if (no != null) {
                    System.out.print(no.key() + "[" + no.getFb() + "]");
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

    private void rotacaoSimplesEsq(No b) {
        No a = b.right();
        No paiB = b.parent();
        
        No filhoAEsq = a.left();
        b.setRight(filhoAEsq); 
        if (filhoAEsq != null) {
            filhoAEsq.setParent(b);
        }
        
        a.setLeft(b); 
        b.setParent(a);

        a.setParent(paiB);
        if (paiB != null) {
            if (paiB.left() == b) paiB.setLeft(a);
            else paiB.setRight(a);
        } else {
            setRoot(a);
        }
        
        int fbB = b.getFb();
        int fbA = a.getFb();

        int fbBNovo = fbB + 1 - Math.min(fbA, 0);
        int fbANovo = fbA + 1 + Math.max(fbBNovo, 0);

        b.setFb(fbBNovo);
        a.setFb(fbANovo);
    }

    private void rotacaoSimplesDir(No b) {
        No paiB = b.parent();
        No a = b.left();
        
        No filhoADir = a.right();
        b.setLeft(filhoADir); 
        if (filhoADir != null) {
            filhoADir.setParent(b);
        }
        
        a.setRight(b); 
        b.setParent(a);

        a.setParent(paiB);
        if (paiB != null) {
            if (paiB.left() == b) paiB.setLeft(a);
            else paiB.setRight(a);
        } else {
            setRoot(a);
        }
        
        int fbB = b.getFb();
        int fbA = a.getFb();

        int fbBNovo = fbB - 1 - Math.max(fbA, 0);
        int fbANovo = fbA - 1 + Math.min(fbBNovo, 0);

        b.setFb(fbBNovo);
        a.setFb(fbANovo);
    }

    private void rotacaoDuplaEsq(No b) {
        No filhoBDir = b.right();
        rotacaoSimplesDir(filhoBDir);
        rotacaoSimplesEsq(b);
    }

    private void rotacaoDuplaDir(No b) {
        No filhoBEsq = b.left();
        rotacaoSimplesEsq(filhoBEsq);
        rotacaoSimplesDir(b);
    }
    

    
}