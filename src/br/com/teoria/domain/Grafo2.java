package br.com.teoria.domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo2 {

	private Integer numVertices;
	private String caminhoArquivoEntrada;
	private String caminhoArquivoSaida;
	private BufferedReader br;
	private BufferedWriter bw;
	private int matriz[][] = null;
	private Map<Integer, List<Integer>> listaAdj;
	/*
	 * Implementação de um grafo não dirigido
	 */
	
	 /* 
	 * Caminho do arquivo de entrada e saida
	 */
	public Grafo2(String caminhoArquivoEntrada, String caminhoArquivoSaida) throws IOException {

		this.caminhoArquivoEntrada = caminhoArquivoEntrada;
		this.numVertices = getNumVertices();
		this.caminhoArquivoSaida = caminhoArquivoSaida;
		// inicializaMatriz();

		mapeiaArestasListaAdj();
	}
	/*
	 * Retorna uma matriz de adjacencia
	 */
	public int[][] getMatriz() {
		for (Integer i : listaAdj.keySet()) {
			for (Integer m : listaAdj.get(i)) {
				matriz[i][m] = 1;
			}
		}
		return matriz;
	}
	/*
	 * Esse metodo organiza a forma que as arestas irão ser construidas 
	 * dentro da lista de adjacencia. ligando x -> y e y -> x.
	 * Em outras palavras, ira ligar um vertice a outro indo e voltando.
	 * Me pergunte se tiver alguma duvida
	 * 
	 * Autor: Wendel Ribeiro
	 */
	private void verticeVertice(int v1, int v2) {
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
	 *  A partir da Stream que esta direcionada para o arquivo com os vertices e as arestas,
	 *  esse metodo insere em um HashMap as arestas.
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
		matriz = new int[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				matriz[i][j] = 0;
			}
		}
	}
	/*
	 * Retorna a quantidade de vertices lidos do arquivo
	 */
	public int getNumVertices() throws IOException {
		br = new BufferedReader(new FileReader(caminhoArquivoEntrada));
		return (Integer.parseInt(br.readLine()));
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
		}else{
			System.out.println("Matriz vazia");
		}
	}
	
	
	public Map<Integer, Integer> getGrau(){
		Map<Integer, Integer> verticesGrau = new HashMap<>();
		for(Integer chave : listaAdj.keySet()){
			verticesGrau.put(chave, listaAdj.get(chave).size());
		}
		return verticesGrau;
	}
	/*
	 * Quantidade de vertices
	 */
	public int getQtdVertices(){
		return getGrau().size();
	}
	
	public int buscaLargura(int verticeInicial){
		int profundidade = 0;
		
		Set<Integer> setVertices;
		Set<Integer> setAberto = new HashSet<>();
		Set<Integer> setFechado = new HashSet<>();
		Map<Integer, Set<Integer>> arvore = new HashMap<>();
		
		//setVertices.add(verticeInicial);
		setAberto.add(verticeInicial);
		arvore.put(profundidade, setAberto);
		while(setAberto.size()!=0){
			//setVertices = new HashSet<>();
			for(Integer i: arvore.get(profundidade)){
				
				for(Integer g : listaAdj.get(i)){
					if(!setFechado.contains(g)){
						//setVertices.add(g);
						setAberto.add(g);	
					}
				}
				setAberto.remove(i);
				
				setFechado.add(i);
				
			}
			
			profundidade++;
			
			arvore.put(profundidade, setAberto);
			
		}
		
		return profundidade;
		
	}
	
	/*public Map<Integer, Set<Integer>> buscaLargura(int veticeInicial){
		int profundidade=0;
		HashMap<Integer, Set<Integer>> arvore = new HashMap<>();
		
		Set<Integer> listaFechada = new HashSet<>();
		Set<Integer> listaAberta = new HashSet<>();
		Set<Integer> listaAtual ;
		
		listaAberta.add(veticeInicial);
		
		arvore.put(0, listaAberta);
		listaFechada.add(veticeInicial);
		for(int p : arvore.keySet()){
			
		
			for(Integer i : arvore.get(p)){
				listaAtual = new HashSet<>();
				for(Integer k: mapVertices.get(i)){
					if(!listaFechada.contains(k)){
						listaAberta.add(k);
						listaAtual.add(k);
					}
				}
	
				profundidade++;
				arvore.put(profundidade, listaAtual);
				listaFechada.add(i);
				listaAberta.remove(i);
				
			}
		}
		return arvore;
	}*/
	
}
