
public class Main {

    public static void main(String[] args) throws Exception {
        Labirinto maze = Labirinto.deArquivo("labirinto.dat");
        PathSearch busca = new PathSearch();

        PathSearch.Resultado dijkstra = busca.dijkstra(maze);
        PathSearch.Resultado aStar = busca.aStar(maze);

        printResult("Dijkstra", dijkstra);
        printResult("A*", aStar);
        if (aStar.isFound()) {
            maze.imprimirCaminho(aStar.getPath());
        }
    }

    private static void printResult(
        String name,
        PathSearch.Resultado result
    ) {
        System.out.println("\n--- " + name + " ---");

        if (!result.isFound()) {
            System.out.println("Nenhum caminho encontrado.");
            return;
        }

        System.out.println("Caminho:");

        for (Cell cell : result.getPath()) {
            System.out.print(cell + " ");
        }

        System.out.println();
        System.out.println("Custo: " + result.getCost());
        System.out.println("Nós expandidos: " + result.getExpandedNodes());

        double elapsedMs = result.getElapsedNanos() / 1_000_000.0;

        System.out.printf("Tempo: %.3f ms%n", elapsedMs);
    }
}
