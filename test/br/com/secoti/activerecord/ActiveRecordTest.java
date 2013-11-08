package br.com.secoti.activerecord;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import br.com.secoti.activerecord.exception.ActiveRecordException;
import br.com.secoti.models.Usuario;
import br.com.secoti.util.HibernateUtil;

/**
 * Classe respons‡vel pelos teste no ActiveRecord
 * 
 * @author Yuri Fialho
 * @since 07/11/2013
 *
 */
public class ActiveRecordTest {
	
	private Session session;
	
	@Before
	public void setUp() throws Exception {
		this.session = HibernateUtil.getSession();
	}

	//@Test
	public void testFindById() {
		try {
			Usuario usuario = Usuario.findById(Usuario.class, 5L, this.session);
			assertNotNull(usuario);
			assertEquals(usuario.getId(), new Long(5));
		} catch (ActiveRecordException e) {
			fail("Erro ao recuperar objeto ["+e.getMessage()+"]");
		}
	}

	@Test
	public void testFind() {
		try {
		Usuario usuario = new Usuario(this.session);
			//usuario.setId(5L);
		usuario.setNome("Yuri Fialho");
				List<Usuario> us = usuario.find();
				for(Usuario u : us) {
					System.out.println(u.getId() + " - " + u.getNome());
				}
			} catch (ActiveRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	//@Test
	public void testSave() {
		try {
			this.session.beginTransaction();
			Usuario usuario = new Usuario(this.session);
				usuario.setNome("LETICIA");
				usuario.save();
			assertNotNull(usuario.getId());
				usuario.setNome("MIRELLA");
				usuario.save();
			assertEquals(usuario.getNome(), "MIRELLA");
			this.session.getTransaction().commit();
		} catch (ActiveRecordException e) {
			this.session.getTransaction().rollback();
			fail("Erro ao salvar objeto ["+e.getMessage()+"]");
		}
	}
}