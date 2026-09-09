public class Main {
    public static void main(String[] args) {
        
        System.out.println("--- Criando a Árvore e inserindo valores base ---");
        // O construtor exige um primeiro nó. Inserimos o 10.
        ArvoreAVL avl = new ArvoreAVL("Nó 10", 10);
        
        // Inserções que não causam desbalanceamento crítico
        avl.insert("Nó 5", 5);
        avl.insert("Nó 15", 15);
        avl.insert("Nó 2", 2);
        avl.insert("Nó 8", 8);
        avl.insert("Nó 22", 22);
        
        System.out.println("Árvore Inicial (Balanceada):");
        avl.mostrar();
        System.out.println("-------------------------------------------------");
        
        // Inserção do 25 - Causará Rotação Simples à Esquerda no nó 15
        System.out.println("\nInserindo o 25 (Gera desbalanceamento e rotação)...");
        avl.insert("Nó 25", 25);
        
        System.out.println("Árvore após inserção do 25 e Rotação Simples Esquerda no 15:");
        avl.mostrar();
        System.out.println("-------------------------------------------------");
        
        // Removendo o 5
        System.out.println("\nRemovendo o nó 5...");
        // Como o método 'remove' pede o Nó como argumento, usamos o 'find' para achá-lo primeiro
        ArvoreBinariaPesquisa.No noParaRemover = avl.find(5, avl.root()); 
        
        if (noParaRemover != null) {
            avl.remove(noParaRemover);
        }
        
        System.out.println("Árvore após a remoção do 5:");
        avl.mostrar();
        System.out.println("-------------------------------------------------");
    }
}