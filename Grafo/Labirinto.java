import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Labirinto {
    private final int[][] celulas;
    private final Grafo grafo;
    private final Vertice[][] mapaVertices;
    private final Vertice inicio;
    private final List<Vertice> saidas;

    private Labirinto(int[][] celulas) {
        this.celulas = celulas;
        this.grafo = new Grafo();
        this.mapaVertices = new Vertice[celulas.length][celulas[0].length];
        this.saidas = new ArrayList<>();

        Vertice inicioEncontrado = null;

        for (int linha = 0; linha < celulas.length; linha++) {
            for (int coluna = 0; coluna < celulas[linha].length; coluna++) {
                int valor = celulas[linha][coluna];

                if (valor == 1) continue;

                Cell celula = new Cell(linha, coluna, valor);
                Vertice vertice = grafo.inserirVertice(celula);

                mapaVertices[linha][coluna] = vertice;

                if (valor == 2) {
                    if (inicioEncontrado != null) {
                        throw new IllegalArgumentException(
                            "O labirinto deve possuir apenas um início."
                        );
                    }

                    inicioEncontrado = vertice;
                } else if (valor == 3) {
                    saidas.add(vertice);
                }
            }
        }

        if (inicioEncontrado == null) {
            throw new IllegalArgumentException(
                "O labirinto não possui posição inicial."
            );
        }

        if (saidas.isEmpty()) {
            throw new IllegalArgumentException(
                "O labirinto não possui saída."
            );
        }

        this.inicio = inicioEncontrado;

        int[][] direcoes = {
            {0, 1},   // dir
            {1, -1},  // baixo-esq
            {1, 0},   // baixo
            {1, 1}    // baixo-dir
        };

        for (int linha = 0; linha < celulas.length; linha++) {
            for (int coluna = 0; coluna < celulas[linha].length; coluna++) {
                Vertice atual = mapaVertices[linha][coluna];

                if (atual == null)  continue;

                for (int[] direcao : direcoes) {
                    int proximaLinha = linha + direcao[0];
                    int proximaColuna = coluna + direcao[1];

                    if (!posicaoValida(proximaLinha, proximaColuna)) continue;

                    Vertice vizinho = mapaVertices[proximaLinha][proximaColuna];

                    if (vizinho == null) continue;

                    double peso = direcao[0] == 0
                        || direcao[1] == 0
                        ? 1.0
                        : Math.sqrt(2.0);

                    grafo.inserirAresta(atual, vizinho, peso);
                }
            }
        }
    }

    public static Labirinto deArquivo(String caminhoArquivo) throws IOException {
        List<String> linhas = Files.readAllLines(Path.of(caminhoArquivo));
        List<int[]> linhasMatriz = new ArrayList<>();

        for (String linha : linhas) {
            String linhaAparada = linha.trim();

            if (linhaAparada.isEmpty()) continue;

            linhasMatriz.add(interpretarLinha(linhaAparada));
        }

        if (linhasMatriz.isEmpty()) {
            throw new IllegalArgumentException(
                "O arquivo do labirinto está vazio."
            );
        }

        int colunas = linhasMatriz.get(0).length;
        int[][] celulas = new int[linhasMatriz.size()][colunas];

        for (int linha = 0; linha < linhasMatriz.size(); linha++) {
            if (linhasMatriz.get(linha).length != colunas) {
                throw new IllegalArgumentException(
                    "Todas as linhas devem possuir o mesmo número de colunas."
                );
            }

            celulas[linha] = linhasMatriz.get(linha);
        }

        return new Labirinto(celulas);
    }

    private static int[] interpretarLinha(String linha) {
        List<Integer> valores = new ArrayList<>();

        if (linha.matches(".*\\s+.*")) {
            String[] partes = linha.split("\\s+");

            for (String parte : partes) {
                valores.add(Integer.parseInt(parte));
            }
        } else {
            for (char caractere : linha.toCharArray()) {
                if (!Character.isDigit(caractere)) {
                    throw new IllegalArgumentException(
                        "Valor inválido no arquivo do labirinto."
                    );
                }

                valores.add(Character.getNumericValue(caractere));
            }
        }

        int[] linhaValores = new int[valores.size()];

        for (int i = 0; i < valores.size(); i++) {
            int valor = valores.get(i);

            if (valor < 0 || valor > 3) {
                throw new IllegalArgumentException(
                    "Os valores do labirinto devem ser 0, 1, 2 ou 3."
                );
            }

            linhaValores[i] = valor;
        }

        return linhaValores;
    }

    private boolean posicaoValida(int linha, int coluna) {
        return linha >= 0
            && linha < celulas.length
            && coluna >= 0
            && coluna < celulas[0].length;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public Vertice getInicio() {
        return inicio;
    }

    public List<Vertice> getSaidas() {
        return new ArrayList<>(saidas);
    }

    public Cell getCelula(Vertice vertice) {
        return (Cell) vertice.getElemento();
    }

    public int getLinhas() {
        return celulas.length;
    }

    public int getColunas() {
        return celulas[0].length;
    }

    public int getValor(int linha, int coluna) {
        return celulas[linha][coluna];
    }

    public Vertice getVertice(int linha, int coluna) {
        return mapaVertices[linha][coluna];
    }
    public void imprimirCaminho(List<Cell> caminho) {
        boolean[][] mapaCaminho = new boolean[celulas.length][celulas[0].length];

        for (Cell celula : caminho) {
            mapaCaminho[celula.getRow()][celula.getColumn()] = true;
        }

        System.out.println("\nLabirinto com caminho:");

        for (int linha = 0; linha < celulas.length; linha++) {
            for (int coluna = 0; coluna < celulas[linha].length; coluna++) {
                int valor = celulas[linha][coluna];

                if (valor == 2) {
                    System.out.print("S ");
                } else if (valor == 3) {
                    System.out.print("E ");
                } else if (valor == 1) {
                    System.out.print("# ");
                } else if (mapaCaminho[linha][coluna]) {
                    System.out.print("* ");
                } else {
                    System.out.print(". ");
                }
            }

            System.out.println();
        }
    }
}