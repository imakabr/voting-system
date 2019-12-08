package ru.imakabr.votingsystem.web;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.imakabr.votingsystem.service.UserService;
import ru.imakabr.votingsystem.util.exception.ErrorType;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
//@WebAppConfiguration
//@ExtendWith(SpringExtension.class)
@Transactional
abstract public class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    protected UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CacheManager cacheManager;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        cacheManager.getCache("allRestaurantsWithItemsByDate").clear();
        cacheManager.getCache("oneRestaurantWithItemsByDate").clear();
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }

    public ResultMatcher errorType(ErrorType type) {
        return jsonPath("$.type").value(type.name());
    }

    public ResultMatcher detailMessage(String code) {
        return jsonPath("$.details").value(code);
    }

}
