package ru.imakabr.votingsystem.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.imakabr.votingsystem.model.Role;
import ru.imakabr.votingsystem.model.User;
import ru.imakabr.votingsystem.web.AbstractControllerTest;
import ru.imakabr.votingsystem.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.imakabr.votingsystem.UserTestData.*;
import static ru.imakabr.votingsystem.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER_ID, "newName", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("newemail@ya.ru"), updated);
    }
}