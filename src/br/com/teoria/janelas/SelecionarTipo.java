package br.com.teoria.janelas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.teoria.domain.ListaAdjacencia;
import br.com.teoria.domain.Matriz;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.ComponentOrientation;
import java.awt.Point;
import java.awt.Frame;
import java.awt.Window.Type;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JTextField;

public class SelecionarTipo extends JFrame {

	private JPanel contentPane;
	private JTextField txtCaminhoArquivo;
	private String caminhoArquivo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelecionarTipo frame = new SelecionarTipo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SelecionarTipo() {
		
		setMaximumSize(new Dimension(500, 220));
		setTitle("Grafos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 223);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setMaximumSize(new Dimension(500, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton bntMatrizAdj = new JButton("Matriz de Adjacencia");
		bntMatrizAdj.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//matriz de adjcencia
				JFrame janelaEscolhida;
				try {
					janelaEscolhida = new Inicial(new Matriz(caminhoArquivo));
					janelaEscolhida.setTitle("Matriz de Adjacencia");
					janelaEscolhida.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		bntMatrizAdj.setBounds(10, 39, 198, 133);
		
		JButton bntListAdj = new JButton("Lista de adjacencia");
		bntListAdj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Lista de adjcencia
				
				JFrame janelaEscolhida;
				try {
					janelaEscolhida = new Inicial(new ListaAdjacencia(caminhoArquivo));
					janelaEscolhida.setTitle("Lista de Adjacencia");
					janelaEscolhida.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		bntListAdj.setBounds(287, 38, 198, 135);
		contentPane.setLayout(null);
		contentPane.add(bntMatrizAdj);
		contentPane.add(bntListAdj);
		
		txtCaminhoArquivo = new JTextField();
		txtCaminhoArquivo.setEnabled(false);
		txtCaminhoArquivo.setColumns(10);
		txtCaminhoArquivo.setBounds(35, 6, 258, 20);
		contentPane.add(txtCaminhoArquivo);
		
		JButton button = new JButton("Selecionar aquivo...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String caminho;
				JFileChooser jf = new JFileChooser();
				jf.setAcceptAllFileFilterUsed(false);
				jf.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de texto", "txt"));
				if (jf.showOpenDialog(null) != 1) {
					caminho = jf.getSelectedFile().getPath();
					txtCaminhoArquivo.setText(caminho);
					caminhoArquivo = caminho;
					bntListAdj.setEnabled(true);
					bntMatrizAdj.setEnabled(true);
				}
			}
		});
		button.setBounds(303, 5, 129, 23);
		contentPane.add(button);
		bntListAdj.setEnabled(false);
		bntMatrizAdj.setEnabled(false);
		this.setLocationRelativeTo(null);
	}
}
