package cn.lynx.ctripmall.db;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import cn.lynx.ctripmall.db.model.CtripEntity;

public class CtripDBMgr {
	private static CtripDBMgr instance = new CtripDBMgr();
	
	@PersistenceUnit(unitName = "CtripDB")
	private EntityManagerFactory emf;
	
	private CtripDBMgr() {
	}
	
	public static CtripDBMgr getInstance() {
		return instance;
	}

	public <T extends CtripEntity> T saveEntity(T t) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		if (t.getUuid() == null) {
			String uuid = UUID.randomUUID().toString();
			t.setUuid(uuid);
			em.persist(t);
		} else {
			t = em.merge(t);
		}
		em.getTransaction().commit();
		em.close();
		
		return t;
	}
	
	public <T extends CtripEntity> List<T> findEntities(Class<T> clazz, Map<String, Object> props, int maxNum) {
		EntityManager em = emf.createEntityManager();
		
		return null;
	}
}
