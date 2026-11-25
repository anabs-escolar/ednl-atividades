import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathSearch {

    public static class Resultado {
        private final boolean encontrado;
        private final List<Cell> caminho;
        private final double custo;
        private final int nosExpandidos;
        private final long tempoNanos;

        public Resultado(
                boolean encontrado,
                List<Cell> caminho,
                double custo,
                int nosExpandidos,
                long tempoNanos) {
            this.encontrado = encontrado;
            this.caminho = caminho;
            this.custo = custo;
            this.nosExpandidos = nosExpandidos;
            this.tempoNanos = tempoNanos;
        }

        public boolean isFound() {
            return encontrado;
        }

        public List<Cell> getPath() {
            return caminho;
        }

        public double getCost() {
            return custo;
        }

        public int getExpandedNodes() {
            return nosExpandidos;
        }

        public long getElapsedNanos() {
            return tempoNanos;
        }
    }

    private static class NoBusca {
        private final Vertice vertice;
        private double custoG;
        private double heuristica;
        private NoBusca pai;

        public NoBusca(Vertice vertice, double custoG, double heuristica, NoBusca pai) {
            this.vertice = vertice;
            this.custoG = custoG;
            this.heuristica = heuristica;
            this.pai = pai;
        }

        public double custoF() {
            return custoG + heuristica;
        }
    }

    private static class EntradaFila {
        private final NoBusca no;
        private final double prioridade;

        public EntradaFila(NoBusca no, double prioridade) {
            this.no = no;
            this.prioridade = prioridade;
        }
    }

    public Resultado dijkstra(Labirinto maze) {
        return buscar(maze, false);
    }

    public Resultado aStar(Labirinto maze) {
        return buscar(maze, true);
    }

    private Resultado buscar(Labirinto maze, boolean usarHeuristica) {
        long inicio = System.nanoTime();
        Grafo grafo = maze.getGrafo();

        FilaPrioridadeHeap fila = new FilaPrioridadeHeap();
        List<Vertice> fechados = new ArrayList<>();

        NoBusca noInicial = new NoBusca(
                maze.getInicio(),
                0,
                usarHeuristica ? heuristica(maze, maze.getInicio()) : 0,
                null
        );

        inserirNaFila(fila, noInicial, usarHeuristica);
        int nosExpandidos = 0;

        while (!fila.isEmpty()) {
            EntradaFila entrada = (EntradaFila) fila.removeMin();
            NoBusca atual = entrada.no;
            double prioridadeAtual = usarHeuristica ? atual.custoF() : atual.custoG;

            if (entrada.prioridade > prioridadeAtual) continue;
            if (contemVertice(fechados, atual.vertice)) continue;

            if (isSaida(maze, atual.vertice)) {
                List<Cell> caminho = reconstruirCaminho(maze, atual);

                return new Resultado(
                        true,
                        caminho,
                        atual.custoG,
                        nosExpandidos,
                        System.nanoTime() - inicio
                );
            }

            fechados.add(atual.vertice);
            nosExpandidos++;

            for (Aresta aresta : grafo.arestasIncidentes(atual.vertice)) {
                Vertice vizinho = grafo.oposto(atual.vertice, aresta);

                if (vizinho == null) continue;
                if (contemVertice(fechados, vizinho)) continue;
                
                double peso = ((Number) aresta.getElemento()).doubleValue();
                double novoCustoG = atual.custoG + peso;

                NoBusca novoNo = new NoBusca(
                        vizinho,
                        novoCustoG,
                        usarHeuristica ? heuristica(maze, vizinho) : 0,
                        atual
                );

                inserirNaFila(fila, novoNo, usarHeuristica);
            }
        }

        return new Resultado(
                false,
                new ArrayList<>(),
                Double.POSITIVE_INFINITY,
                nosExpandidos,
                System.nanoTime() - inicio
        );
    }

    private void inserirNaFila(
            FilaPrioridadeHeap fila,
            NoBusca no,
            boolean usarHeuristica) {

        double prioridade = usarHeuristica ? no.custoF() : no.custoG;

        EntradaFila entrada = new EntradaFila(no, prioridade);
        fila.insert(entrada, prioridade);
    }

    private boolean contemVertice(List<Vertice> vertices, Vertice procurado) {
        for (Vertice vertice : vertices) {
            if (vertice == procurado) return true;
        }
        return false;
    }

    private boolean isSaida(Labirinto maze, Vertice vertice) {
        for (Vertice saida : maze.getSaidas()) {
            if (saida == vertice) return true;
        }
        return false;
    }

    private List<Cell> reconstruirCaminho(Labirinto maze, NoBusca no) {
        List<Cell> caminho = new ArrayList<>();

        while (no != null) {
            caminho.add(maze.getCelula(no.vertice));
            no = no.pai;
        }

        Collections.reverse(caminho);
        return caminho;
    }

    private double heuristica(Labirinto maze, Vertice vertice) {
        Cell atual = maze.getCelula(vertice);
        double menorDistancia = Double.POSITIVE_INFINITY;

        for (Vertice saida : maze.getSaidas()) {
            Cell destino = maze.getCelula(saida);

            int dx = Math.abs(atual.getRow() - destino.getRow());
            int dy = Math.abs(atual.getColumn() - destino.getColumn());
            int menor = Math.min(dx, dy);
            int maior = Math.max(dx, dy);

            double distancia = maior + (Math.sqrt(2.0) - 1.0) * menor;
            menorDistancia = Math.min(menorDistancia, distancia);
        }
        return menorDistancia;
    }
}