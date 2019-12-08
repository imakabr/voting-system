package ru.imakabr.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ru.imakabr.votingsystem.model.Role;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.util.exception.NotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.imakabr.votingsystem.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {
    @Autowired
    protected UserService userService;

    @Test
    void create() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass",false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = userService.create(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(created, newUser);
        assertMatch(userService.getAll(), ADMIN, newUser, USER);
    }

    @Test
    void duplicateMailCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                userService.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER)));
    }

    @Test
    void delete() throws Exception {
        userService.delete(USER_ID);
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.delete(1111));
    }

    @Test
    void get() throws Exception {
        User user = userService.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        User user = userService.getByEmail("admin@gmail.com");
        assertMatch(user, ADMIN);
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        userService.update(new User(updated));
        assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<User> all = userService.getAll();
        assertMatch(all, ADMIN, USER);
    }
}
