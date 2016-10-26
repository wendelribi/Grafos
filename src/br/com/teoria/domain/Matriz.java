package br.com.teoria.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Matriz extends Grafo {

	private int[][] matriz;
	private long memoriaUtilizadaAntes;
	private long memoriaUtilizadaDepois;
	public Matriz(String caminhoArquivoLeitura) throws IOException {
		
		super(caminhoArquivoLeitura);
		memoriaUtilizadaAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		try{
		matriz = new int[numeroVertices][numeroVertices];
		}catch (OutOfMemoryError e) {
			JOptionPane.showMessageDialog(null, "Excedeu memoria");
			System.exit(0);
		}
		zeraMatriz();
		
		inicializaGrafo();
		memoriaUtilizadaDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

	}

	private void zeraMatriz() {
		for (int i = 0; i < numeroVertices; i++) {
			for (int j = 0; j < numeroVertices; j++) {
				matriz[i][j] = 0;
			}
		}
	}

	public long getMemoriaUtilizadaAntes() {
		return memoriaUtilizadaAntes;
	}

	public long getMemoriaUtilizadaDepois() {
		return memoriaUtilizadaDepois;
	}

	public void setMemoriaUtilizadaAntes(long memoriaUtilizadaAntes) {
		this.memoriaUtilizadaAntes = memoriaUtilizadaAntes;
	}

	public void setMemoriaUtilizadaDepois(long memoriaUtilizadaDepois) {
		this.memoriaUtilizadaDepois = memoriaUtilizadaDepois;
	}

	@Override
	protected void insereArestas(int v1, int v2) {
		matriz[v1][v2] = 1;
	}

	@Override
	public Map<Integer, Set<No>> buscaLargura(int verticeInicial) {
		long tempoInicial = System.currentTimeMillis();
		int nivel = 1;
		No no = new No(verticeInicial);
		Set<No> listaFechada = new HashSet<>();
		Set<No> setFilhos = new HashSet<>();
		Set<No> listaAberta = new HashSet<>();
		Map<Integer, Set<No>> arvore = new HashMap<>();

		no.setPai(no);

		setFilhos.add(no);

		arvore.put(nivel, (Set<No>) setFilhos);
		nivel++;
		listaAberta.add(no);
		listaFechada.add(no);

		while (!listaAberta.isEmpty()) {
			setFilhos = new HashSet<>();
			for (No pai : listaAberta) {
				for (int i = 0; i < numeroVertices; i++) {
					if (matriz[pai.getValor()][i] == 1) {
						no = new No(i, pai);
						if (!listaFechada.contains(no)) {
							setFilhos.add(no);

						}
					}
				}
			}

			listaFechada.addAll(listaAberta);
			listaAberta = new HashSet<>();
			listaAberta.addAll(setFilhos);
			if (!setFilhos.isEmpty()) {
				arvore.put(nivel, setFilhos);
				nivel++;
			}

		}
		long tempoFinal = System.currentTimeMillis();
		double tempo = ((tempoFinal-tempoInicial)/1000); 
		System.out.println(tempo);
		JOptionPane.showMessageDialog(null, "Executou a busca em: "+tempo);
		return arvore;
	}

	@Override
	public List<List<No>> componentesConexos() {
		List<List<No>> partesGrafo = new ArrayList<>();

		Stack<Integer> todosVertices = new Stack<>();
		Stack<Integer> sobras = null;

		for (int i = 0; i < numeroVertices; i++) {
			todosVertices.add(i);
		}

		int vertice = todosVertices.pop();
		
		List<No> componente;
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
				componente = buscaProfundidade(sobras.pop());
				if(!(componente.size()==1 || componente==null)){
					partesGrafo.add(componente);	
				}
				
			}
			todosVertices.addAll(sobras);
		}
		return partesGrafo;
	}

	@Override
	public Map<Integer, Integer> getGrau() {
		int grau = 0;
		HashMap<Integer, Integer> mapGrau = new HashMap<>();
		for (int i = 0; i < numeroVertices; i++) {
			for (int j = 0; j < numeroVertices; j++) {
				if (matriz[i][j] == 1) {
					grau++;
				}
			}
			mapGrau.put(i, grau);
			grau = 0;
		}

		return mapGrau;
	}

	@Override
	public List<No> buscaProfundidade(int verticeInicial) {
		long tempoInicial = System.currentTimeMillis();
		No no = new No(verticeInicial);
		List<No> listArvore = new ArrayList<>();
		Stack<No> pilha = new Stack<>();
		no.setPai(no);
		listArvore.add(no);
		pilha.push(no);
		while (!pilha.isEmpty()) {
			for (int i = 0; i < numeroVertices; i++) {
				if (matriz[no.getValor()][i] == 1) {
					No filho = new No(i);
					filho.setPai(new No(no.getValor()));
					if (!((listArvore.contains(filho)) || (pilha.contains(filho)))) {
						pilha.push(filho);
					}
				}
			}
			no = pilha.pop();
			if (!listArvore.contains(no)) {
				listArvore.add(no);
			}
		}
		long tempoFinal = System.currentTimeMillis();
		double tempo = ((tempoFinal-tempoInicial)/1000); 
		System.out.println(tempo);
		JOptionPane.showMessageDialog(null, "Executou a busca em: "+tempo);
		return listArvore;
	}

	@Override
	public void imprimeGrafo() {
		for (int i = 0; i < numeroVertices; i++) {

			for (int j = 0; j < numeroVertices; j++) {
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}
}
