package br.com.teoria.domain;

public class No {
	private Integer valor;
	private No pai;

	public No(int valor) {
		this.pai = null;
		this.valor = valor;

	}

	public No(int valor, No pai) {
		this.pai = pai;
		this.valor = valor;

	}
	
	public No getPai() {
		return pai;
	}

	public void setPai(No pai) {
		this.pai = pai;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof No) && ((No) obj).valor.equals(this.valor)) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public int hashCode() {
		return valor;
	}
	

}
