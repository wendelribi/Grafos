package br.com.teoria.janelas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import br.com.teoria.domain.Grafo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class Inicial extends JFrame {

	private JPanel contentPane;
	private JButton btnComponentesDoGrafo;
	private JButton btnInformaesDoGrafo;
	private Grafo grafo;
	private JButton btnBuscaDePronfundidade;
	private JLabel lblBuscas;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Inicial(Grafo grafo) {
		this.grafo = grafo;
		inicializa();
		this.setLocationRelativeTo(null);
	}

	private void inicializa() {
		setBounds(new Rectangle(1000, 500, 0, 0));
		setResizable(false);
		setTitle("Grafos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)));

		btnComponentesDoGrafo = new JButton("Componentes do grafo");
		btnComponentesDoGrafo.setBounds(63, 30, 175, 23);
		btnComponentesDoGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String caminho;
				JFileChooser jf = new JFileChooser();
				jf.setAcceptAllFileFilterUsed(false);
				jf.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de texto", "txt"));

				if (jf.showSaveDialog(null) != 1) {
					caminho = jf.getSelectedFile().getPath();
					caminho += ".txt";
					try {
						grafo.gravaInfoComponentesConexos(caminho);
						Runtime.getRuntime().exec("explorer " + caminho);
					} catch (IOException e) {

						e.printStackTrace();
					}
				}

			}
		});

		btnInformaesDoGrafo = new JButton("Salvar informa\u00E7\u00F5es Grafo");
		btnInformaesDoGrafo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho;
				JFileChooser jf = new JFileChooser();
				jf.setAcceptAllFileFilterUsed(false);
				jf.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de texto", "txt"));

				if (jf.showSaveDialog(null) != 1) {
					caminho = jf.getSelectedFile().getPath();
					caminho += ".txt";

					try {
						grafo.informacoesGrafo(caminho);
						Runtime.getRuntime().exec("explorer " + caminho);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		btnInformaesDoGrafo.setBounds(248, 30, 175, 23);
		panel.setLayout(null);
		panel.add(btnComponentesDoGrafo);
		panel.add(btnInformaesDoGrafo);

		btnBuscaDePronfundidade = new JButton("Pronfundidade");
		btnBuscaDePronfundidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho;
				JFileChooser jf = new JFileChooser();
				jf.setAcceptAllFileFilterUsed(false);
				jf.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de texto", "txt"));

				if (jf.showSaveDialog(null) != 1) {
					try {
						int verticeInicial = Integer.parseInt(JOptionPane.showInputDialog("Informe um numero"));
						caminho = jf.getSelectedFile().getPath();
						caminho += ".txt";
						if((verticeInicial < grafo.getNumeroVertices() && (grafo.gravaBuscaProfundidade(caminho, verticeInicial)))){
							Runtime.getRuntime().exec("explorer " + caminho);	
						}else{
							JOptionPane.showMessageDialog(null, "Vertice não existe.");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Erro erro ao digitar numeros");
					}
				}
			}
		});
		btnBuscaDePronfundidade.setBounds(225, 144, 129, 23);
		panel.add(btnBuscaDePronfundidade);

		JButton btnLargura = new JButton("Largura");
		btnLargura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho;
				JFileChooser jf = new JFileChooser();
				jf.setAcceptAllFileFilterUsed(false);
				jf.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de texto", "txt"));

				if (jf.showSaveDialog(null) != 1) {
					try {
						int verticeInicial = Integer.parseInt(JOptionPane.showInputDialog("Informe um numero"));
						caminho = jf.getSelectedFile().getPath();
						caminho += ".txt";
						if((verticeInicial < grafo.getNumeroVertices()) &&(grafo.gravaBuscaLargura(caminho, verticeInicial))){
							Runtime.getRuntime().exec("explorer " + caminho);	
						}else{
							JOptionPane.showMessageDialog(null, "Vertice não existe.");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Erro erro ao digitar numeros");
					}
				}

			}
		});
		btnLargura.setBounds(123, 144, 89, 23);
		panel.add(btnLargura);

		lblBuscas = new JLabel("");
		lblBuscas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Buscas",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblBuscas.setBounds(100, 128, 274, 58);
		panel.add(lblBuscas);

		JLabel lblInfoma = new JLabel("");
		lblInfoma.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Informa\u00E7\u00F5es do Grafo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lblInfoma.setBounds(41, 11, 402, 58);
		panel.add(lblInfoma);
		contentPane.setLayout(gl_contentPane);
	}
}
