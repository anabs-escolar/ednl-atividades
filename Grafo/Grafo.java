import java.util.ArrayList;
import java.util.List;

public class Grafo {

    private final List<Vertice> vertices;
    private final List<Aresta> arestas;
    private final List<List<List<Aresta >>> matriz;

    public Grafo() {
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
        matriz = new ArrayList<>();
    }

    // Métodos auxiliares

    private void validarVertice(Vertice  v) {
        if (v == null || !vertices.contains(v)) 
            throw new IllegalArgumentException("Vértice inválido.");
    }

    private void validarAresta(Aresta  e) {
        if (e == null || !arestas.contains(e)) 
            throw new IllegalArgumentException("Aresta inválida.");
    }

    private void validarIncidencia(Vertice  v, Aresta  e) {
        validarVertice(v);
        validarAresta(e);

        if (e.getOrigem() != v && e.getDestino() != v) 
            throw new IllegalArgumentException("A aresta não é incidente ao vértice.");
    }

    // Métodos de acesso

    public List<Vertice> vertices() {
        return new ArrayList<>(vertices);
    }

    public List<Aresta> arestas() {
        return new ArrayList<>(arestas);
    }

    public int ordem() {
        return vertices.size();
    }

    public int tamanho() {
        return arestas.size();
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public List<Vertice> finalVertices(Aresta e) {
        validarAresta(e);
        List<Vertice > finais = new ArrayList<>();
        finais.add(e.getOrigem());
        finais.add(e.getDestino());
        return finais;
    }

    public Vertice oposto(Vertice v, Aresta  e) {
        validarIncidencia(v, e);
        if (e.getOrigem() == v) 
            return e.getDestino();
        return e.getOrigem();
    }

    public boolean isAdjacente(Vertice v, Vertice  w) {
        validarVertice(v);
        validarVertice(w);

        return !matriz.get(v.getIndex()).get(w.getIndex()).isEmpty();
    }

    public boolean isDirecionado(Aresta  e) {
        validarAresta(e);
        return e.isDirecionada();
    }

    public List<Aresta> arestasIncidentes(Vertice  v) {
        validarVertice(v);
        List<Aresta > incidentes = new ArrayList<>();
        for (Aresta  e : arestas) {
            if (e.getOrigem() == v || e.getDestino() == v) 
                incidentes.add(e);
        }
        return incidentes;
    }

    // Métodos de atualização

    public Vertice  inserirVertice(Object elemento) {
        Vertice v = new Vertice(elemento, vertices.size());
        vertices.add(v);

        for (List<List<Aresta >> linha : matriz) 
            linha.add(new ArrayList<>());
        
        List<List<Aresta>> novaLinha = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) 
            novaLinha.add(new ArrayList<>());

        matriz.add(novaLinha);
        return v;
    }

    public Aresta inserirAresta(Vertice v, Vertice w, Object elemento) {
        return inserirAresta(v, w, elemento, false);
    }

    public Aresta inserirArestaDirecionada(Vertice v, Vertice  w, Object elemento) {
        return inserirAresta(v, w, elemento, true);
    }

    private Aresta inserirAresta(Vertice v, Vertice w, Object elemento, boolean direcionada) {
        validarVertice(v);
        validarVertice(w);

        Aresta  e = new Aresta(v, w, elemento, direcionada);

        int i = v.getIndex();
        int j = w.getIndex();

        matriz.get(i).get(j).add(e);
        if (!direcionada && i != j) 
            matriz.get(j).get(i).add(e);

        arestas.add(e);
        return e;
    }

    public Object removeVertice(Vertice v) {
        validarVertice(v);
        Object elemento = v.getElemento();
        List<Aresta> incidentes = arestasIncidentes(v);

        for (Aresta e : incidentes) 
            removeAresta(e);

        int indice = v.getIndex();
        matriz.remove(indice);

        for (List<List<Aresta>> linha : matriz) 
            linha.remove(indice);
        vertices.remove(indice);

        for (int i = indice; i < vertices.size(); i++) {
            vertices.get(i).setIndex(i);
        }
        return elemento;
    }

    public Object removeAresta(Aresta e) {
        validarAresta(e);

        Object elemento = e.getElemento();

        int i = e.getOrigem().getIndex();
        int j = e.getDestino().getIndex();

        matriz.get(i).get(j).remove(e);

        if (!e.isDirecionada() && i != j) {
            matriz.get(j).get(i).remove(e);
        }
        arestas.remove(e);
        return elemento;
    }

    // Substituição de elementos

    public Object substituir(Vertice v, Object elemento) {
        validarVertice(v);
        Object anterior = v.getElemento();
        v.setElemento(elemento);
        return anterior;
    }

    public Object substituir(Aresta e, Object elemento) {
        validarAresta(e);
        Object anterior = e.getElemento();
        e.setElemento(elemento);
        return anterior;
    }

    // Grau dos vértices

    public int grau(Vertice v) {
        validarVertice(v);
        int grau = 0;
        for (Aresta  e : arestasIncidentes(v)) 
            grau += (e.getOrigem() == v && e.getDestino() == v) ? 2 : 1;
        return grau;
    }

    public int grauEntrada(Vertice v) {
        validarVertice(v);
        int grau = 0;
        for (Aresta  e : arestas) 
            if (e.isDirecionada() && e.getDestino() == v) 
                grau++;
        return grau;
    }

    public int grauSaida(Vertice v) {
        validarVertice(v);
        int grau = 0;
        for (Aresta  e : arestas) 
            if (e.isDirecionada() && e.getOrigem() == v) 
                grau++;
        return grau;
    }

    // Exibição do grafo

    public void mostrar() {
        System.out.println("Vértices: " + vertices);
        System.out.println("Arestas:");

        for (Aresta  e : arestas)
            System.out.println(e);

        System.out.println("\nMatriz de Adjacência:");

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                System.out.print(
                        matriz.get(i).get(j).size() + "\t"
                );
            }

            System.out.println();
        }
    }
}