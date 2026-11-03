public class Main {
    public static void main(String[] args) {

        Grafo grafo = new Grafo();

        Vertice v1 = grafo.inserirVertice("Natal");
        Vertice v2 = grafo.inserirVertice("Mossoró");
        Vertice v3 = grafo.inserirVertice("Caicó");
        Vertice v4 = grafo.inserirVertice("Parnamirim");

        grafo.inserirAresta(v1, v2, 100);
        grafo.inserirAresta(v1, v2, 150);
        grafo.inserirAresta(v1, v3, 200);
        grafo.inserirArestaDirecionada(v2, v4, 50);

        grafo.mostrar();

        System.out.println("\nOrdem: " + grafo.ordem());
        System.out.println("Tamanho: " + grafo.tamanho());

        System.out.println("\nAdjacentes:");
        System.out.println(grafo.isAdjacente(v1, v2));

        System.out.println("\nGrau de Natal: " + grafo.grau(v1));
        System.out.println("Grau de entrada de Parnamirim: " + grafo.grauEntrada(v4));
        System.out.println("Grau de saída de Mossoró: " + grafo.grauSaida(v2));

        System.out.println("\nArestas incidentes em Natal:");
        for (Aresta e : grafo.arestasIncidentes(v1)) {
            System.out.println(e);
        }

        grafo.removeAresta(grafo.arestas().get(0));

        System.out.println("\nApós remover uma aresta:");
        grafo.mostrar();

        grafo.removeVertice(v3);

        System.out.println("\nApós remover Caicó:");
        grafo.mostrar();
    }
}
/*
Vértices: [Natal, Mossoró, Caicó, Parnamirim]
Arestas:
Natal -- Mossoró (100)
Natal -- Mossoró (150)
Natal -- Caicó (200)
Mossoró -> Parnamirim (50)

Matriz de Adjacência:
0       2       1       0
2       0       0       1
1       0       0       0
0       0       0       0

Ordem: 4
Tamanho: 4

Adjacentes:
true

Grau de Natal: 3
Grau de entrada de Parnamirim: 1
Grau de saída de Mossoró: 1

Arestas incidentes em Natal:
Natal -- Mossoró (100)
Natal -- Mossoró (150)
Natal -- Caicó (200)

Após remover uma aresta:
Vértices: [Natal, Mossoró, Caicó, Parnamirim]
Arestas:
Natal -- Mossoró (150)
Natal -- Caicó (200)
Mossoró -> Parnamirim (50)

Matriz de Adjacência:
0       1       1       0
1       0       0       1
1       0       0       0
0       0       0       0

Após remover Caicó:
Vértices: [Natal, Mossoró, Parnamirim]
Arestas:
Natal -- Mossoró (150)
Mossoró -> Parnamirim (50)

Matriz de Adjacência:
0       1       0
1       0       1
0       0       0
*/ 