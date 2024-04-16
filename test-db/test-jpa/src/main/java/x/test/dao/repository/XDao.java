package x.test.dao.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import x.test.dao.entity.User;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 * @author chenrong30@gome.com.cn
 * @date 2021/6/4 09:03
 */
@Repository
public class XDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional("t1Transaction")
    public void test(){
        User user = entityManager.find(User.class, 1);
        user.setId(1);
        user.setUserName("t");
        entityManager.persist(user);
        entityManager.flush();
    }

    public User get() {
//        User user = new User();
//        user.setId(1);
        return entityManager.find(User.class, 1);
    }



}
