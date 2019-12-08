package ru.imakabr.votingsystem.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.imakabr.votingsystem.TimingExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
class AbstractServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("allRestaurantsWithItemsByDate").clear();
        cacheManager.getCache("oneRestaurantWithItemsByDate").clear();
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }
}
