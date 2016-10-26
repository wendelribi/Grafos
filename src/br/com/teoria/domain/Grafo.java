package br.com.teoria.domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

abstract public class Grafo {
	private String caminhoArquivoLeitura;
	private String caminhoArquivoEscrita;
	protected Integer numeroVertices;
	protected Integer numeroArestas;
	private BufferedReader arquivoLeitura;
	private boolean inicializado;

	public Grafo(String caminhoArquivoLeitura) throws IOException {
		inicializado = false;
		this.caminhoArquivoLeitura = caminhoArquivoLeitura;
		this.numeroVertices = getNumeroVertices();
	}

	abstract protected void insereArestas(int v1, int v2);

	abstract public Map<Integer, Set<No>> buscaLargura(int verticeInicial);

	abstract public List<List<No>> componentesConexos();

	abstract public Map<Integer, Integer> getGrau();

	abstract public List<No> buscaProfundidade(int verticeInicial);

	abstract public void imprimeGrafo();

	public String getCaminhoArquivoLeitura() {
		return caminhoArquivoLeitura;
	}

	public void setCaminhoArquivoLeitura(String caminhoArquivoLeitura) {
		this.caminhoArquivoLeitura = caminhoArquivoLeitura;
	}

	public String getCaminhoArquivoEscrita() {
		return caminhoArquivoEscrita;
	}

	public void setCaminhoArquivoEscrita(String caminhoArquivoEscrita) {
		this.caminhoArquivoEscrita = caminhoArquivoEscrita;
	}

	public Integer getNumeroArestas() {
		return numeroArestas;
	}

	public void setNumeroArestas(Integer numeroArestas) {
		this.numeroArestas = numeroArestas;
	}

	public BufferedReader getArquivoLeitura() {
		return arquivoLeitura;
	}

	public void setArquivoLeitura(BufferedReader arquivoLeitura) {
		this.arquivoLeitura = arquivoLeitura;
	}

	public void setNumeroVertices(Integer numeroVertices) {
		this.numeroVertices = numeroVertices;
	}

	protected void inicializaGrafo() throws IOException {
		int v1, v2, qtdArestas = 0;

		String linha;

		BufferedReader arquivo = getArquivoLeitura();

		linha = arquivo.readLine();

		while ((linha != null) && !(linha.isEmpty())) {
			qtdArestas += 1;
			v1 = Integer.parseInt(linha.split("[ ]")[0]);
			v2 = Integer.parseInt(linha.split("[ ]")[1]);
			insereArestas(v1, v2);
			insereArestas(v2, v1);
			linha = arquivo.readLine();
		}
		setNumeroArestas(qtdArestas);
	}

	public boolean gravaBuscaProfundidade(String caminhoArquivoSaida, int verticeInicial) throws IOException {
		List<No> listaBusca;
		BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoArquivoSaida));
		long tempoInicial = System.currentTimeMillis();

		
		if((listaBusca = buscaProfundidade(verticeInicial))==null){
			return false; 
		}

		long tempoFinal = System.currentTimeMillis();
		double tempo = ((tempoFinal - tempoInicial) / 1000);
		System.out.println(((tempoFinal - tempoInicial) / 1000));

		for (No n : listaBusca) {
			arquivo.append(n.getValor() + " -> PAI(" + n.getPai().getValor() + ")");
			arquivo.newLine();
		}
		arquivo.close();
		JOptionPane.showMessageDialog(null, "Executou a busca em: " + tempo);
		return true;
	}

	public boolean gravaBuscaLargura(String caminhoArquivoSaida, int verticeInicial) throws IOException {
		BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoArquivoSaida));
		Map<Integer, Set<No>> mapBusca;
		long tempoInicial = System.currentTimeMillis();
		if((mapBusca = buscaLargura(verticeInicial))==null){
			return false; 
		}
		long tempoFinal = System.currentTimeMillis();
		double tempo = ((tempoFinal - tempoInicial) / 1000);
		System.out.println(((tempoFinal - tempoInicial) / 1000));
		for (Integer i : mapBusca.keySet()) {
			arquivo.append("Nivel " + i);
			for (No no : mapBusca.get(i)) {
				arquivo.append("->" + no.getValor());
			}
			arquivo.newLine();
		}
		arquivo.close();
		JOptionPane.showMessageDialog(null, "Executou a busca em: " + tempo);
		return true;
	}

	public void infComponentesConexos(String caminhoArquivoInformacao) throws IOException {

		int i = 1;
		BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoArquivoInformacao));

		List<List<No>> compConex = componentesConexos();
		arquivo.append("Quantidade de componentes: " + compConex.size());
		arquivo.newLine();
		for (List<No> c : compConex) {
			StringBuilder s = new StringBuilder();
			s.append("Quantidade de vertices " + c.size() + " Componente " + i + ": ");

			for (No no : c) {
				s.append(no.getValor() + " ");

			}
			arquivo.append(s);
			arquivo.newLine();
			i++;
		}
		arquivo.close();
	}

	public void informacoesGrafo(String caminhoArquivoSaida) throws IOException {

		boolean primeiroAcesso = true;

		ArrayList<Integer> listaMaiores = new ArrayList<>();
		ArrayList<Integer> listaMenores = new ArrayList<>();

		BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoArquivoSaida));

		arquivo.append("# n = " + getNumeroVertices());
		arquivo.newLine();
		arquivo.append("# m = " + getNumeroArestas());
		arquivo.newLine();

		for (Integer chave : getGrau().keySet()) {
			if (primeiroAcesso) {
				listaMaiores.add(chave);
				listaMenores.add(chave);
				primeiroAcesso = false;
			}
			if (getGrau().get(chave) > getGrau().get(listaMaiores.get(0))) {
				listaMaiores = new ArrayList<>();
				listaMaiores.add(chave);
			} else if (getGrau().get(chave) == getGrau().get(listaMaiores.get(0))) {
				listaMaiores.add(chave);
			}

			if (getGrau().get(chave) < getGrau().get(listaMenores.get(0))) {
				listaMenores = new ArrayList<>();
				listaMenores.add(chave);
			} else if (getGrau().get(chave) == getGrau().get(listaMenores.get(0))) {
				listaMenores.add(chave);
			}
			arquivo.append(chave + " " + getGrau().get(chave));
			arquivo.newLine();
		}
		if (listaMaiores.size() > 1) {
			arquivo.append("Lista dos vertices de MAIOR Grau: ");
		} else {
			arquivo.append("Vertice de MAIOR Grau: ");
		}
		for (Integer maior : listaMaiores) {
			arquivo.append(maior + " ");
		}

		arquivo.newLine();
		if (listaMenores.size() > 1) {
			arquivo.append("Lista dos vertices de MENOR Grau: ");
		} else {
			arquivo.append("Vertice de MENOR Grau: ");
		}

		for (Integer menor : listaMenores) {
			arquivo.append(menor + " ");
		}
		arquivo.close();

	}

	public int getNumeroVertices() throws IOException {
		
		if (inicializado) {
			return numeroVertices;
		} else {
			inicializado = true;
			arquivoLeitura = new BufferedReader(new FileReader(caminhoArquivoLeitura));
			try{
				numeroVertices = Integer.parseInt(arquivoLeitura.readLine());	
			}catch (Exception e) {
				
				throw new NumberFormatException("Seu arquivo esta fora do padrão");
			}
			
			return numeroVertices;
		}
	}

	public void gravaInfoComponentesConexos(String caminhoArquivoSaida) throws IOException {
		StringBuilder caminhoMaiorMenor = new StringBuilder();
		caminhoMaiorMenor.append("maiorMenor");
		caminhoMaiorMenor.append(caminhoArquivoSaida);
		BufferedWriter arquivo = new BufferedWriter(new FileWriter(caminhoArquivoSaida));
		List<No> maior = new ArrayList<>();
		List<No> menor = new ArrayList<>();
		List<List<No>> partesGrafo = componentesConexos();
		int i = 1, numMaior = 0, numMenor = 0;
		arquivo.append("Quantidade de componentes: " + partesGrafo.size());
		arquivo.newLine();
		arquivo.newLine();
		maior = partesGrafo.get(0);
		menor = partesGrafo.get(0);
		for (List<No> listaNo : partesGrafo) {
			if (listaNo.size() > maior.size()) {
				maior = listaNo;
				numMaior = i;
			}
			if (listaNo.size() < menor.size()) {
				menor = listaNo;
				numMenor = i;
			}
			arquivo.append("Quantidade de vertices: " + listaNo.size());
			arquivo.newLine();
			arquivo.append("Componente " + i + "={");
			for (No n : listaNo) {
				arquivo.append(" " + n.getValor());
			}
			arquivo.append(" }");
			arquivo.newLine();
			i++;
		}
		arquivo.newLine();
		arquivo.newLine();
		arquivo.append("Maior componente " + numMaior + ":");
		for (No n : maior) {
			arquivo.append(" " + n.getValor());
		}
		arquivo.newLine();
		arquivo.append("Menor componente " + numMenor + ":");
		for (No n : menor) {
			arquivo.append(" " + n.getValor());
		}

		arquivo.close();

	}
}
