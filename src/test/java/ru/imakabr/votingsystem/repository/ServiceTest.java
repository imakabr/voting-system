package ru.imakabr.votingsystem.repository;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.imakabr.votingsystem.TimingExtension;
import ru.imakabr.votingsystem.model.Role;
import ru.imakabr.votingsystem.model.User;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.imakabr.votingsystem.UserTestData.*;

@SpringJUnitConfig(locations = {
//        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
//@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
class ServiceTest {

    @Autowired
    protected CrudUserRepository userRepository;

//    @Test
//    void create() throws Exception {
//        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
//        User created = userRepository.create(new User(newUser));
//        newUser.setId(created.getId());
//        assertMatch(created, newUser);
//        assertMatch(userRepository.getAll(), ADMIN, newUser, USER);
//    }
//
//    @Test
//    void duplicateMailCreate() throws Exception {
//        assertThrows(DataAccessException.class, () ->
//                userRepository.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
//    }

    @Test
    void delete() throws Exception {
        userRepository.delete(USER_ID);
        assertMatch(userRepository.getAll(), ADMIN);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userRepository.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = userRepository.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userRepository.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = userRepository.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

//    @Test
//    void update() throws Exception {
//        User updated = new User(USER);
//        updated.setName("UpdatedName");
//        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
//        userRepository.update(new User(updated));
//        assertMatch(userRepository.get(USER_ID), updated);
//    }

    @Test
    void getAll() throws Exception {
        List<User> all = userRepository.getAll();
        assertMatch(all, ADMIN, USER);
    }


}
