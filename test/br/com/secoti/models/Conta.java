package br.com.secoti.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="contas")
@SequenceGenerator(name="idConta", sequenceName="contas_id_seq", allocationSize=1, initialValue=1)
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* attributes */
	@Id
	@GeneratedValue(generator="idConta", strategy=GenerationType.SEQUENCE)
	private Long id;
	@Column(name="descricao")
	private String descricao;
	@ManyToOne
	private Usuario usuario;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="conta_id")
	private List<Lancamento> lancamentos;

	/* getters and setters */
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}
