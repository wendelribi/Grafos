package br.com.teoria.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ListaAdjacencia extends Grafo {

	private Map<Integer, List<Integer>> listaAdj;

	public ListaAdjacencia(String caminhoArquivoLeitura) throws IOException {
		super(caminhoArquivoLeitura);
		listaAdj = new HashMap<Integer, List<Integer>>();
		inicializaGrafo();

	}

	@Override
	protected void insereArestas(int v1, int v2) {

		if (listaAdj.containsKey(v1)) {
			if (!(listaAdj.get(v1).contains(v2))) {
				listaAdj.get(v1).add(v2);
			}
		} else {
			ArrayList<Integer> listaVertices = new ArrayList<>();
			listaVertices.add(v2);
			listaAdj.put(v1, listaVertices);
		}
	}

	@Override
	public List<List<No>> componentesConexos() {
		List<List<No>> partesGrafo = new ArrayList<>();

		Stack<Integer> todosVertices = new Stack<>();
		Stack<Integer> sobras = null;

		todosVertices.addAll(listaAdj.keySet());

		int vertice = todosVertices.pop();

		partesGrafo.add(buscaProfundidade(vertice));
		No no = null;
		while (!todosVertices.isEmpty()) {
			boolean controle = false;
			sobras = new Stack<>();
			no = new No(todosVertices.pop());
			for (List<No> grafo : partesGrafo) {
				if (grafo.contains(no)) {
					controle = true;
				}
			}
			if (controle == false) {
				sobras.push(no.getValor());
			}
			if (!sobras.isEmpty()) {

				partesGrafo.add(buscaProfundidade(sobras.pop()));
			}
			todosVertices.addAll(sobras);
		}

		return partesGrafo;
	}

	@Override
	public Map<Integer, Integer> getGrau() {

		Map<Integer, Integer> verticesGrau = new HashMap<>();
		for (Integer chave : listaAdj.keySet()) {
			verticesGrau.put(chave, listaAdj.get(chave).size());
		}
		return verticesGrau;
	}

	@Override
	public List<No> buscaProfundidade(int verticeInicial) {
		No no = new No(verticeInicial);
		List<No> listArvore = new ArrayList<>();
		Stack<No> pilha = new Stack<>();

		if (!listaAdj.containsKey(verticeInicial)) {
			return null;
		}

		no.setPai(no);
		listArvore.add(no);
		pilha.push(no);
		while (!pilha.isEmpty()) {
			for (Integer r : listaAdj.get(no.getValor())) {
				No filho = new No(r);
				filho.setPai(new No(no.getValor()));
				if (!((listArvore.contains(filho)) || (pilha.contains(filho)))) {
					pilha.push(filho);
				}
			}
			no = pilha.pop();
			if (!listArvore.contains(no)) {
				listArvore.add(no);
			}
		}

		return listArvore;
	}

	@Override
	public Map<Integer, Set<No>> buscaLargura(int verticeInicial) {
		if (!listaAdj.containsKey(verticeInicial)) {
			System.out.println("Nao existe");
			return null;
		}
		int nivel = 1;
		No no = new No(verticeInicial);
		no.setPai(no);
		Set<No> listaAberta = new HashSet<>();
		Set<No> listaFechada = new HashSet<>();
		Set<No> listaFilhos = null;
		listaAberta.add(no);

		Map<Integer, Set<No>> arvore = new HashMap<>();
		arvore.put(0, listaAberta);
		while (!listaAberta.isEmpty()) {
			listaFilhos = new HashSet<>();
			for (No pai : listaAberta) {
				for (Integer s : listaAdj.get(pai.getValor())) {
					no = new No(s, pai);
					if (!listaFechada.contains(no)) {
						listaFilhos.add(no);
					}
				}
			}

			listaFechada.addAll(listaAberta);
			listaAberta = new HashSet<>();
			listaAberta.addAll(listaFilhos);
			if (!listaFilhos.isEmpty()) {
				arvore.put(nivel, listaFilhos);
				nivel++;
			}

		}
		return arvore;
	}

	@Override
	public void imprimeGrafo() {
		for (Integer chave : listaAdj.keySet()) {
			System.out.print(chave);
			for (Integer valor : listaAdj.get(chave)) {
				System.out.print(" -> " + valor);
			}
			System.out.println();
		}
	}

	public Map<Integer, List<Integer>> getListaAdj() {
		return listaAdj;
	}

	public void setListaAdj(Map<Integer, List<Integer>> listaAdj) {
		this.listaAdj = listaAdj;
	}

}