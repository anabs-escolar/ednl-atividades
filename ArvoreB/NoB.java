

public class NoB {
    int[] chaves;
    NoB[] filhos;
    int numChaves;
    int t; // grau minino
    boolean folha;

    public NoB(int t, boolean folha){
        this. t = t;
        this.folha = folha;
        this.chaves = new int[2*t-1];
        this.filhos = new NoB[2*t];
        this.numChaves = 0; 
    }
}