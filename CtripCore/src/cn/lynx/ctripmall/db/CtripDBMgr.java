package cn.lynx.ctripmall.db;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import cn.lynx.ctripmall.db.model.CtripEntity;

public class CtripDBMgr {
	private static final String PERSISTENCE_UNIT = "cn.lynx.ctripmall.persistunit";
	private static final String PERSISTENCE_UNIT_JNDI = "osgi:service/javax.persistence.EntityManagerFactory/(osgi.unit.name="
			+ PERSISTENCE_UNIT + ")";
	private static CtripDBMgr instance = new CtripDBMgr();
	
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private UserTransaction utx;

	private CtripDBMgr() {
		try {
			InitialContext ctx = new InitialContext();
            emf = (EntityManagerFactory) ctx.lookup(PERSISTENCE_UNIT_JNDI);
            utx = (UserTransaction) ctx.lookup("osgi:service/javax.transaction.UserTransaction");
            em = emf.createEntityManager();
      } catch (NamingException e) {
            e.printStackTrace();
      }
	}
	
	public static CtripDBMgr getInstance() {
		return instance;
	}
	
	public <T extends CtripEntity> T saveEntity(T t) {
		try {
			utx.begin();
			em.joinTransaction();
			if (t.getUuid() != null) {
				t = em.merge(t);
			} else {
				String uuid = UUID.randomUUID().toString();
				t.setUuid(uuid);
				em.persist(t);
			}
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
		
	}

	public <T extends CtripEntity> T removeEntity(T t) {
		em.remove(t);
		return t;
	}
	
	public <T extends CtripEntity> List<T> queryEntitiesByProperties(Class<T> clazz, Map<String, Object> propMap,
			int topNum) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);

		Root<T> rootEntity = cq.from(clazz);
		cq.select(rootEntity);

		if (propMap != null) {
			Predicate criteria = cb.conjunction();
			Iterator<String> mapKeyIt = propMap.keySet().iterator();
			while (mapKeyIt.hasNext()) {
				String key = mapKeyIt.next();
				Object val = propMap.get(key);

				Expression<T> exp = buildExpressionEqual(rootEntity, key);
				criteria = cb.and(criteria, cb.equal(exp, val));
			}

			cq.where(criteria);
		}

		TypedQuery<T> tq = em.createQuery(cq);
		if (topNum > 0) {
			tq.setMaxResults(topNum);
		}

		List<T> result = tq.getResultList();

		return result;
	}

	private <T extends CtripEntity> Expression<T> buildExpressionEqual(Root<T> root, String key) {
		if (key.contains(".")) {
			Path<T> interRoot = root;
			String[] keyChains = key.split("\\.");
			for (int i = 0; i < keyChains.length; i++) {
				interRoot = interRoot.get(keyChains[i]);
			}
			return interRoot;
		} else {
			return root.get(key);
		}
	}
}
