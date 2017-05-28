package br.com.bluesoft.desafio.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Produto {

    @Id
    private String gtin;

    private String nome;

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Produto [gtin=");
		builder.append(gtin);
		builder.append(", nome=");
		builder.append(nome);
		builder.append("]");
		return builder.toString();
	}

	public Produto(String gtin, String nome) {
		this.gtin = gtin;
		this.nome = nome;
	}

	public Produto() {
		super();
	}
    
}
