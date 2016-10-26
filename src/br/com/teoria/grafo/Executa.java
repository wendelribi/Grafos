package br.com.teoria.grafo;

import java.io.IOException;
import java.util.List;

import br.com.teoria.domain.ListaAdjacencia;
import br.com.teoria.domain.Matriz;
import br.com.teoria.domain.No;


public class Executa {

	public static void main(String[] args) throws IOException {
		long ant, dps;
		ant = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Matriz matriz = new Matriz("C:\\Users\\wendel\\collaboration_graph.txt");
		
		//ListaAdjacencia lista = new ListaAdjacencia("C:\\Users\\wendel\\collaboration_graph.txt");
		/*matriz.gravaBuscaLargura("C:\\Users\\wendel\\buscaLarguraInformacao.txt", 2);
		matriz.gravaBuscaLargura("C:\\Users\\wendel\\buscaProfundidadesInformacao.txt", 2);*/
		
		//matriz.gravaInfoComponentesConexos("C:\\Users\\wendel\\componetesConexos.txt");
		dps = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println((dps - ant)/1024);
		//System.out.println(.size());
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
