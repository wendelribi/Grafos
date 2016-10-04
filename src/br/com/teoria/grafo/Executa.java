package br.com.teoria.grafo;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import br.com.teoria.domain.Grafo;
import br.com.teoria.domain.Grafo;
import br.com.teoria.domain.No;

public class Executa {

	public static void main(String[] args) {
		Grafo g;
		int[][] matriz;

		// g = new Grafo("C:\\Users\\wendel\\as_graph.txt", 2);

		try {
			g = new Grafo("C:\\Users\\wendel\\arquivo.txt", "C:\\Users\\wendel\\saida.txt");
			Map<Integer, Set<No>> arvore = g.buscaLargura(5);
			for(Integer s : arvore.keySet()){
				System.out.print(s);
				for(No n : arvore.get(s)){
					System.out.print("->"+"("+n.getPai().getValor()+")"+n.getValor());
				}
				System.out.println();
			}
			
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
