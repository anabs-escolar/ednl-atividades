public class Main {
    public static void main(String[] args) {
        ArvoreRubroNegra arvore = new ArvoreRubroNegra();

        int[] inserir = {10, 5, 15, 1, 7, 12, 20, 3, 6, 8};

        for (int valor : inserir) {
            arvore.insert(valor, valor);
        }

        System.out.println("Após inserções:");
        arvore.mostrar();

        int[] remover = {1, 20, 5, 10};

        for (int valor : remover) {
            NoRB no = arvore.find(valor, arvore.root());
            arvore.remove(no);
        }

        System.out.println("\nApós remoções:");
        arvore.mostrar();

        System.out.println("\nTamanho: " + arvore.size());
        System.out.println("Raiz: " + arvore.root().key());
    }
}
