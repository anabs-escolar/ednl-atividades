
public class Main {
    public static void main(String[] args) {
        ArvoreB arvore = new ArvoreB(2);
        int[] valores = {10, 20, 5, 6, 12, 30, 7, 17};

        for (int valor : valores) {
            arvore.insert(valor);
        }

        arvore.imprimir();

        System.out.println(arvore.find(12));
        System.out.println(arvore.find(99));
    }
}
