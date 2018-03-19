Hactiverecord
=============

Inicialmente o projeto foi idealizado para ser uma implementação simples de rápida do padrão Active Record utilizando o Hibernate, atualmente foi modificado para ser utilizado com JPA, ainda é uma versão BETA e que está em desenvolvimento, caso queira colaborar faça o fork do projeto e submeta as alterações.

1) Uso

a) Crie uma classe que extenda a classe abstrata Active Record

Exemplo:
 
```
@Entity
@Table(name="categorias")
public class Categoria extends ActiveRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private int id;
	
	@Column(length=45)
	private String descricao;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
```

Executando: 

```
public static void main(String[] args) throws ActiveRecordException {
		List<Categoria> categorias = Categoria.findAll(Categoria.class);
		for (Categoria categoria : categorias) {
			System.out.println(categoria.getDescricao());
		}
}
```


==============================
Yuri Fialho - 19/03/2018
==============================