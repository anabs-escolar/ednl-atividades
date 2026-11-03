

public class ArvoreB {
    private NoB raiz;
    private final int t;

    public ArvoreB(int t){
        if (t<2){
            throw new IllegalArgumentException(
                "O grau minimo deve ser pelo menos 2"
            );
        }
        this.t = t;
        this.raiz = new NoB(t, true);
    }

    public boolean find(int k){
        return find(raiz, k);
    }

    private boolean find(NoB no, int k){
        if (no == null) return false;

        int i = 0;
        while (i < no.numChaves && k > no.chaves[i]) 
            i++;

        if (i< no.numChaves && k == no.chaves[i])
            return true;

        if (no.folha) return false;

        return find(no.filhos[i], k);
    }

    public void insert(int k){
        if(find(k)) return;
        if (raiz.numChaves == 2*t-1){
            NoB n_raiz =  new NoB(t, false);
            n_raiz.filhos[0] = raiz;
            
            splitChild(n_raiz, 0);
            raiz = n_raiz;
        }
        insertNonFull(raiz, k);
    }

    private void insertNonFull(NoB no, int k){
        int i = no.numChaves -1;
        if (no.folha){
            while(i>=0 && k<no.chaves[i]){
                no.chaves[i+1] = no.chaves[i];
                i--;
            }
            no.chaves[i+1] = k;
            no.numChaves++;
        } else {
            while(i>=0 && k<no.chaves[i]) i--;
            i++;
            if (no.filhos[i].numChaves == 2*t-1){
                splitChild(no, i);
                if (k > no.chaves[i]) i++;
            }
            insertNonFull(no.filhos[i], k);
        }
    }
    private void splitChild(NoB pai, int index){
        NoB full = pai.filhos[index];
        NoB n_no = new NoB(t, full.folha);

        n_no.numChaves = t-1;

        for(int i = 0; i < t-1; i++){
            n_no.chaves[i] = full.chaves[i+t];
        }

        if (!full.folha){
            for(int i=0; i<t; i++){
                n_no.filhos[i] = full.filhos[i+t];
                full.filhos[i+t] = null;
            }
        }

        int mediana = full.chaves[t-1];
        full.numChaves = t - 1;

        for (int i = pai.numChaves; i > index; i--)
            pai.filhos[i+1] = pai.filhos[i];
        
        pai.filhos[index+1] = n_no;

        for (int i = pai.numChaves -1; i>= index; i--)
            pai.chaves[i+1] = pai.chaves[i];


        pai.chaves[index] = mediana;
        pai.numChaves++;
    }

   
    public void imprimir() {
        imprimir(raiz);
        System.out.println();
    }

    private void imprimir(NoB no) {
        int i;
        for (i = 0; i < no.numChaves; i++) {
            if (!no.folha) 
                imprimir(no.filhos[i]);
            System.out.print(no.chaves[i] + " ");
        }
        if (!no.folha) 
            imprimir(no.filhos[i]);
        
    }
}