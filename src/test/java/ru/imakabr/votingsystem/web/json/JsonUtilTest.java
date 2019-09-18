package ru.imakabr.votingsystem.web.json;

import org.junit.jupiter.api.Test;
import ru.imakabr.votingsystem.model.User;

import java.util.List;

import static ru.imakabr.votingsystem.UserTestData.*;

class JsonUtilTest {

    @Test
    void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        assertMatch(user, ADMIN);
    }

    @Test
    void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(USERS);
        System.out.println(json);
        List<User> users = JsonUtil.readValues(json, User.class);
        assertMatch(users, USERS);

    }
}