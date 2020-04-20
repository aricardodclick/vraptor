package br.com.caelum.goodbuy.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.goodbuy.modelo.Produto;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class GenericDao<T> {
	
	private Class classePersistente;
	private T t;	
	private final Session session;
	private T generico = null;
	
	public GenericDao(Session session, T t){
		this.session = session;
		this.t = t;
		this.classePersistente = (Class) t;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listaTudo(){
		return session.createCriteria(classePersistente).list();
		//return this.session.createCriteria(Produto.class).list();
	}
	
	public void salva(T objeto) {
		Transaction tx = session.beginTransaction();
		session.save(objeto);
		tx.commit();
	}
	

	public void atualiza(T objeto) {
		Transaction tx = session.beginTransaction();
		session.update(objeto);
		tx.commit();
	}
	
	public T carrega(Long id){
			return (T) session.load(classePersistente, id);
		}
	
	//public Produto carrega(Long id){
		//return (Produto) session.load(Produto.class, id);
	//}
	
	public void remove(T objeto) {
		Transaction tx = session.beginTransaction();
		session.delete(objeto);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	public List<T> busca(String nome) {
		return  session.createCriteria(classePersistente).add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE))
		.list();
	}
	
	
}
