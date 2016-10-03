package br.com.teoria.grafo;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import br.com.teoria.domain.Grafo;
import br.com.teoria.domain.Grafo2;

public class Executa {

	public static void main(String[] args) {
		Grafo2 g;
		int[][] matriz;

		// g = new Grafo("C:\\Users\\wendel\\as_graph.txt", 2);

		try {
			g = new Grafo2("C:\\Users\\wendel\\arquivo.txt", null);
			
			
			/*for(Integer chave : g.getGrau().keySet()){
				System.out.println(chave+": "+g.getGrau().get(chave));
			}*/
			System.out.println("Quantidade: "+g.getQtdVertices());
			//System.out.println(g.buscaLargura(1));
			
		
			
			/*System.out.println("MATRIZ DISFARCADA");
			g.imprimeMatrizDisfarcada();*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
