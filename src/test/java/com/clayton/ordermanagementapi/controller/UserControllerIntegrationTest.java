package com.clayton.ordermanagementapi.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateUser() throws Exception {

        String email = "test" + System.currentTimeMillis() + "@email.com";

        String json = """
        {
            "name": "Clayton",
            "email": "%s",
            "password": "123456"
        }
        """.formatted(email);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void shouldGetUserById() throws Exception {

        String email = "test" + System.currentTimeMillis() + "@email.com";

        String createJson = """
    {
        "name": "Clayton",
        "email": "%s",
        "password": "123456"
    }
    """.formatted(email);

        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {

        mockMvc.perform(get("/users/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllUsers() throws Exception {

        String email = "test" + System.currentTimeMillis() + "@email.com";

        String json = """
    {
        "name": "User Test",
        "email": "%s",
        "password": "123456"
    }
    """.formatted(email);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldDeleteUser() throws Exception {

        String email = "test" + System.currentTimeMillis() + "@email.com";

        String json = """
    {
        "name": "Clayton",
        "email": "%s",
        "password": "123456"
    }
    """.formatted(email);

        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingUser() throws Exception {

        mockMvc.perform(delete("/users/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateUser() throws Exception {

        String email = "test" + System.currentTimeMillis() + "@email.com";

        String createJson = """
    {
        "name": "Old Name",
        "email": "%s",
        "password": "123456"
    }
    """.formatted(email);

        String response = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = ((Number) JsonPath.read(response, "$.id")).longValue();

        String updateJson = """
    {
        "name": "New Name",
        "email": null,
        "password": null
    }
    """;

        mockMvc.perform(put("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingUser() throws Exception {

        String updateJson = """
    {
        "name": "New Name",
        "email": "email@email.com",
        "password": "123"
    }
    """;

        mockMvc.perform(put("/users/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound());
    }
}
