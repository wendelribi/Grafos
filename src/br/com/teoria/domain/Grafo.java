package br.com.teoria.domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Grafo {

	private Integer numVertices;
	private String caminhoArquivoEntrada;
	private String caminhoArquivoSaida;
	private BufferedReader br;
	private BufferedWriter bw;
	private Integer matriz[][] = null;
	private Map<Integer, List<Integer>> listaAdj;
	private Integer qtdArestas = null;

	/*
	 * Implementação de um grafo não dirigido
	 */

	/*
	 * Caminho do arquivo de entrada e saida
	 */
	public Grafo(String caminhoArquivoEntrada, String caminhoArquivoSaida) throws IOException {
		qtdArestas = 0;
		this.caminhoArquivoEntrada = caminhoArquivoEntrada;
		this.numVertices = getNumVertices();
		this.caminhoArquivoSaida = caminhoArquivoSaida;
		// inicializaMatriz();

		mapeiaArestasListaAdj();
		infGraf();
	}

	/*
	 * Retorna uma matriz de adjacencia
	 */
	public Integer[][] getMatriz() {
		for (Integer i : listaAdj.keySet()) {
			for (Integer m : listaAdj.get(i)) {
				matriz[i][m] = 1;
			}
		}
		return matriz;
	}

	/*
	 * Esse metodo organiza a forma que as arestas irão ser construidas dentro
	 * da lista de adjacencia. ligando x -> y e y -> x. Em outras palavras, ira
	 * ligar um vertice a outro indo e voltando. Me pergunte se tiver alguma
	 * duvida
	 * 
	 * Autor: Wendel Ribeiro
	 */
	private void verticeVertice(int v1, int v2) {
		qtdArestas += 1;
		insereArestas(v1, v2);
		insereArestas(v2, v1);
	}

	/*
	 * Insere arestas apartir de dois vertices
	 */

	private void insereArestas(int v1, int v2) {

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

	/*
	 * A partir da Stream que esta direcionada para o arquivo com os vertices e
	 * as arestas, esse metodo insere em um HashMap as arestas.
	 */
	private void mapeiaArestasListaAdj() throws IOException {
		int v1, v2;
		String linha;

		listaAdj = new HashMap<>();
		linha = br.readLine();
		while ((linha != null) && !(linha.isEmpty())) {
			v1 = Integer.parseInt(linha.split("[ ]")[0]);
			v2 = Integer.parseInt(linha.split("[ ]")[1]);

			verticeVertice(v1, v2);

			linha = br.readLine();
		}
	}

	/*
	 * Inicializa a matriz de acordo com a quantidade de vertices definina
	 */
	private void inicializaMatriz() {
		matriz = new Integer[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				matriz[i][j] = 0;
			}
		}
	}

	/*
	 * Retorna a quantidade de vertices lidos do arquivo
	 */
	private int getNumVertices() throws IOException {
		br = new BufferedReader(new FileReader(caminhoArquivoEntrada));
		return (Integer.parseInt(br.readLine()));
	}

	public int getQtdArestas() {
		return qtdArestas;
	}

	/*
	 * Retorna o caminho do arquivo txt de arestas e vertices
	 */

	public String getCaminhoArquivo() {
		return caminhoArquivoEntrada;
	}

	/*
	 * Representação falsa de uma matriz a partir de uma lista de adjacencia
	 * 
	 */
	public void imprimeMatrizDisfarcada() {
		System.out.print(" ");
		for (int i = 0; i < numVertices; i++) {
			System.out.print(" " + (i + 1));
		}
		System.out.println();
		for (int i = 0; i < numVertices; i++) {
			System.out.print(i + 1 + " ");
			for (int k = 0; k < numVertices; k++) {
				if (listaAdj.containsKey(i) && listaAdj.get(i).contains(k)) {
					System.out.print(1 + " ");
				} else {
					System.out.print(0 + " ");
				}

			}
			System.out.println();
		}
	}

	/*
	 * Imprime uma matriz inicializada e mapeada com vertices
	 */
	public void imprimeMatriz() {
		if (matriz != null) {
			System.out.print(" ");
			for (int i = 0; i < numVertices; i++) {
				System.out.print(" " + (i + 1));
			}
			System.out.println();
			for (int i = 0; i < numVertices; i++) {
				System.out.print(i + 1 + " ");
				for (int k = 0; k < numVertices; k++) {
					if (i < 9) {
						System.out.print(matriz[i][k] + " ");
					} else {
						System.out.print(matriz[i][k] + "  ");
					}

				}
				System.out.println();
			}
		} else {
			System.out.println("Matriz vazia");
		}
	}

	public Map<Integer, Integer> getGrau() {
		Map<Integer, Integer> verticesGrau = new HashMap<>();
		for (Integer chave : listaAdj.keySet()) {
			verticesGrau.put(chave, listaAdj.get(chave).size());
		}
		return verticesGrau;
	}

	/*
	 * Quantidade de vertices
	 */
	public int getQtdVertices() {
		return getGrau().size();
	}

	/*
	 * Loucura - Busca em largura implementada com Map, set e uma classe No.
	 */
	public Map<Integer, Set<No>> buscaLargura(int verticeInicial) {
		if(!listaAdj.containsKey(verticeInicial)){
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

	private void infGraf() throws IOException {
		BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoArquivoSaida));
		arquivo.append("# n = " + getQtdVertices());
		arquivo.newLine();
		arquivo.append("# m = " + getQtdArestas());
		arquivo.newLine();
		for (Integer chave : getGrau().keySet()) {
			arquivo.append(chave + " " + getGrau().get(chave));
			arquivo.newLine();
		}
		arquivo.close();
	}
	
	public void verificaGrafo(){
		Integer vertice = null;
		for(Integer i : listaAdj.keySet()){
			System.out.println(i);
			vertice = i;
			break;
		}
		if(vertice!=null){
			System.out.println(vertice);
		}
		
		
		
	}

	public List<No> buscaProfundidade(int verticeInicial) {
		if(!listaAdj.containsKey(verticeInicial)){
			return null;
		}
		No no = new No(verticeInicial);
		no.setPai(no);
		List<No> listArvore = new ArrayList<>();
		listArvore.add(no);
		Stack<No> pilha = new Stack<>();
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
}
