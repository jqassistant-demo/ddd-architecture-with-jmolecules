package org.jqassistant.demo.architecture.hexagonal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.jqassistant.demo.architecture.hexagonal.user.application.ports.primary.UserApplicationService;
import org.jqassistant.demo.architecture.hexagonal.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiControllerTest {

    @MockBean
    private UserApplicationService userApplicationService;

    @Autowired
    private MockMvc mockMvc;

    private Map<Long, User> users;

    @BeforeEach
    void stub() {
        users = new LinkedHashMap<>();
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            long id = users.size();
            users.put(id, user.toBuilder()
                    .id(id)
                    .build());
            return user;
        }).when(userApplicationService)
                .save(any(User.class));

        doAnswer(invocation -> users.values()
                .stream()
                .collect(toList())).when(userApplicationService)
                .findAll();

        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return Optional.ofNullable(users.get(id));
        }).when(userApplicationService)
                .findById(any(long.class));
    }

    @Test
    void createAndFindUser() throws Exception {
        this.mockMvc.perform(put("/api/v1/users").contentType(APPLICATION_JSON)
                        .content("{ \"email\": \"foo.bar@example.com\", \"firstName\": \"Foo\", \"lastName\": \"Bar\" }")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(userApplicationService).save(any(User.class));

        this.mockMvc.perform(get("/api/v1/users").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(userApplicationService).findAll();

        this.mockMvc.perform(get("/api/v1/users/0").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("id", equalTo(0)))
                .andExpect(jsonPath("firstName", equalTo("Foo")))
                .andExpect(jsonPath("lastName", equalTo("Bar")))
                .andExpect(jsonPath("email", equalTo("foo.bar@example.com")));

        verify(userApplicationService).findById(0);
    }

    @Test
    void getNonExistingUser() throws Exception {
        this.mockMvc.perform(get("/api/v1/users/0").accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
