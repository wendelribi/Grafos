package br.com.teoria.domain;

public class No {
	private Integer posicao;
	private Integer valor;
	private Integer qtdVertices;

	public No(int valor) {
		this.valor = valor;
		this.qtdVertices=1;
	}

	public void addQtdV(){
		qtdVertices++;
	}
	
	public void setValor(int valor){
		this.valor = valor;
	}
	
	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	@Override
	public boolean equals(Object obj) {
		if((obj instanceof Integer) && ((Integer)obj).equals(this.posicao)){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return posicao;
	}
	
}
