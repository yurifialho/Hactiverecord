package br.com.secoti.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.secoti.activerecord.ActiveRecord;
import br.com.secoti.activerecord.exception.ActiveRecordException;
import br.com.secoti.util.HibernateUtil;

@Entity
@Table(name = "usuarios")
@SequenceGenerator(name="idUsuario", sequenceName="usuarios_id_seq",allocationSize=1,initialValue=1)
public class Usuario extends ActiveRecord<Usuario>{

	private static final long serialVersionUID = 1L;
	
	/* attributes */
	@Id
	@GeneratedValue(generator="idUsuario", strategy=GenerationType.SEQUENCE)
	private Long id;
	@Column(name="nome")
	private String nome;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="usuario_id")
	private List<Conta> contas;
	
	/* constructors */
	
	public Usuario() {
		super(null);
	}
	
	public Usuario(Session session) {
		super(session);
	}
	/* getters and setters */
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Conta> getContas() {
		return contas;
	}
	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
	
	public static void main(String[] args) throws HibernateException, ActiveRecordException {
		//Session s = HibernateUtil.getSession();
		//	s.load(Usuario.class, 5L);
		Usuario u = Usuario.findById(Usuario.class, 5L, HibernateUtil.getSession());
	}
}