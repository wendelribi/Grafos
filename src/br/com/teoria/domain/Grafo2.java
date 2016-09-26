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
	private Map<Integer, List<Integer>> mapVertices;

	public Grafo2(String caminhoArquivoEntrada, String caminhoArquivoSaida) throws IOException {

		this.caminhoArquivoEntrada = caminhoArquivoEntrada;
		this.numVertices = getNumVertices();
		this.caminhoArquivoSaida = caminhoArquivoSaida;
		// inicializaMatriz();

		mapeiaVertices();
	}

	/*public int[][] getMatriz() {
		for (Integer i : mapVertices.keySet()) {
			for (Integer m : mapVertices.get(i)) {
				matriz[i][m] = 1;
			}
		}
		return matriz;
	}*/

	private void verticeVertice(int v1, int v2) {
		insereVertices(v1, v2);
		insereVertices(v2, v1);
	}

	private void insereVertices(int v1, int v2) {
		if (mapVertices.containsKey(v1)) {
			if (!(mapVertices.get(v1).contains(v2))) {
				mapVertices.get(v1).add(v2);
			}
		} else {
			ArrayList<Integer> listaVertices = new ArrayList<>();
			listaVertices.add(v2);
			mapVertices.put(v1, listaVertices);
		}
	}

	private void mapeiaVertices() throws IOException {
		int v1, v2;
		String linha;
		mapVertices = new HashMap<>();
		linha = br.readLine();
		while ((linha != null) && !(linha.isEmpty())) {
			v1 = Integer.parseInt(linha.split("[ ]")[0]);
			v2 = Integer.parseInt(linha.split("[ ]")[1]);
			verticeVertice(v1, v2);
			linha = br.readLine();
		}
	}

	private void inicializaMatriz() {
		matriz = new int[numVertices][numVertices];
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				matriz[i][j] = 0;
			}
		}
	}

	public int getNumVertices() throws IOException {
		br = new BufferedReader(new FileReader(caminhoArquivoEntrada));
		return (Integer.parseInt(br.readLine()));
	}

	public String getCaminhoArquivo() {
		return caminhoArquivoEntrada;
	}

	public void imprimeMatrizDisfarcada() {
		System.out.print(" ");
		for (int i = 0; i < numVertices; i++) {
			System.out.print(" " + (i + 1));
		}
		System.out.println();
		for (int i = 0; i < numVertices; i++) {
			System.out.print(i + 1 + " ");
			for (int k = 0; k < numVertices; k++) {
				if (mapVertices.containsKey(i) && mapVertices.get(i).contains(k)) {
					System.out.print(1 + " ");
				} else {
					System.out.print(0 + " ");
				}

			}
			System.out.println();
		}
	}

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
	
	
	/*public void buscaProfundidade(int veticeInicial){
		List<Integer> listaAberta;
		ArrayList<Integer> listaFechada = new ArrayList<>();
		listaFechada.add(veticeInicial);
		listaAberta = mapVertices.get(veticeInicial);
		for(Integer i : listaAberta){
			System.out.println(i);
		}
	}*/
	
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
				
				for(Integer g : mapVertices.get(i)){
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
