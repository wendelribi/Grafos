package br.com.teoria.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class Grafo {
	private String caminho;
	private Integer vertices;
	private Map<Integer, List<Integer>> arestas;
	private Integer escolha;
	private int[][] matriz;

	/**
	 * O parametro caminho indica o caminho do arquivo que contem valores para o
	 * grafo. Parametro escolha possui duas opções, 1 para representar o grafo
	 * com matriz de adjacencia e 2 para lista de adjacencia
	 * 
	 * @param caminho
	 * @param escolha
	 */
	public Grafo(String caminho, int escolha) {
		this.caminho = caminho;
		this.escolha = escolha;
		long ant, dps;
		Runtime rt = Runtime.getRuntime();
		ant = rt.totalMemory();
		// System.out.println(ant);
		if (escolha == 1) {
			matrizAdjacencia();
		} else {
			listaAdjacencia();
		}

		dps = rt.freeMemory();
		System.out.println(((dps - ant) / 1024) / 1024);
	}

	private void matrizAdjacencia() {
		boolean primeiraLinha = true;
		int[] a = new int[2];

		String linha;
		int qtdVertices;
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(caminho));

			while ((linha = br.readLine()) != null) {
				if (primeiraLinha) {
					vertices = Integer.parseInt(linha);
					matriz = new int[vertices][vertices];
					for (int i = 0; i < vertices; i++) {
						for (int j = 0; j < vertices; j++) {
							matriz[i][j] = 0;
						}
					}
					primeiraLinha = false;
				} else {
					a[0] = Integer.parseInt(linha.split(" ")[0]);
					a[1] = Integer.parseInt(linha.split(" ")[1]);
					addAresta(a[0], a[1]);
				}
			}
			br.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private void listaAdjacencia() {
		int i = 0;
		int[] a = new int[2];
		String linha;
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(caminho));

			arestas = new HashMap<>();

			while ((linha = br.readLine()) != null) {
				if (i == 0) {
					this.vertices = Integer.parseInt(linha);
					i++;
				} else {
					a[0] = Integer.parseInt(linha.split(" ")[0]);
					a[1] = Integer.parseInt(linha.split(" ")[1]);
					addAresta(a[0], a[1]);
				}
			}
			br.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void addAresta(int v1, int v2) {
		if (escolha == 1) {
			matriz[v1][v2] = 1;
		} else {
			putArestaLista(v1, v2);
			putArestaLista(v2, v1);
		}
	}

	private void putArestaLista(int v1, int v2) {
		List<Integer> listV1;
		if (!arestas.containsKey(v1)) {
			listV1 = new ArrayList<>();
			listV1.add(v2);
			arestas.put(v1, listV1);
		} else {
			listV1 = arestas.get(v1);
			if (!listV1.contains(v2)) {
				arestas.get(v1).add(v2);
			}
		}

	}

	public void imprimeGrafo() {
		if (escolha == 1) {
			imprimeMatriz();
		} else {
			imprimeLista();
		}
	}

	private void imprimeMatriz() {
		System.out.print(" ");
		for(int i = 0; i< vertices; i++){
			System.out.print(" "+(i+1));
		}
		System.out.println();
		for (int i = 0; i < vertices; i++) {
			System.out.print(i + 1 + " ");
			for (int k = 0; k < vertices; k++) {
				if(i<9){
					System.out.print(matriz[i][k] + " ");	
				}else{
					System.out.print(matriz[i][k] + "  ");
				}
				
			}
			System.out.println();
		}
	}

	private void imprimeLista() {
		for (Integer chave : arestas.keySet()) {
			System.out.print(chave);
			for (Integer valor : arestas.get(chave)) {
				System.out.print("->" + valor);
			}
			System.out.println();
		}
	}

	public void imprimeAresta(int v) {
		if (arestas.containsKey(v)) {
			for (Integer i : arestas.get(v)) {
				System.out.println(i);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Nao existe Vertice");
		}
	}

}
