package br.com.secoti.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="lancamentos")
@SequenceGenerator(name="idLancamento", sequenceName="lancamentos_id_seq",allocationSize=1,initialValue=1)
public class Lancamento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/* attributes */
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idLancamento")
	private Long id;
	private Double valor;
	private Date data;
	@ManyToOne
	@JoinColumn(name="conta_id")
	private Conta conta;

	/* getters and setters */
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
}