package util;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	 private static final EntityManagerFactory entityManagerFactory;
	  static {
	    try {
	      entityManagerFactory = Persistence.createEntityManagerFactory("apartment");

	    } catch (ExceptionInInitializerError ex) {
	      System.err.println("初始化实体管理器工厂失败。" + ex);
	      throw new ExceptionInInitializerError(ex);
	    }
	  }
	  public static EntityManager getEntityManager() {
	    return entityManagerFactory.createEntityManager();
	  }
}
