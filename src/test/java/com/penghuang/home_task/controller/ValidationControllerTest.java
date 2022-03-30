package com.penghuang.home_task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Users;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.service.UserService;
import com.penghuang.home_task.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableWebMvc
@AutoConfigureMockMvc
@SpringBootTest(classes = ValidationController.class)
public class ValidationControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidationService validationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test generateToken method.
     * Case: if username or password is empty, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testAuthenticate_Ng() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"\",\n" +
                "    \"password\":\"\"\n" +
                "}";

        // execute createUser method
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 500
                    .andExpect(MockMvcResultMatchers.status().is(500)).andReturn();
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: generate token failed! username or password can't be empty!",e.getMessage());
        }

    }

    /**
     * Test generateToken method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testAuthenticate_Ok() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"tom\",\n" +
                "    \"password\":\"a123\"\n" +
                "}";
        User userObj = new User();
        userObj.setName("tom");
        userObj.setPassword("a123");

        String expectToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImE1NzAxNTgyIiwiZXhwIjoxNjQ4NjIzMDE3LCJ1c2VybmFtZSI6ImphY2sifQ.A5j88OLJm5qs3rrZ2PSjuGtRgt4G8qSAY6ZYbXdBxcA";

        Mockito.when(validationService.authenticate(userObj)).thenReturn(expectToken);


        // execute generateToken method
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 201
                    .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();

        assertEquals(201,mvcResult.getResponse().getStatus());

    }

    /**
     * Test invalidateToken method.
     * Case: if token is empty ,then ng.
     *
     * @throws Exception
     */
    @Test
    public void testInvalidate_Ng() throws Exception {

        // test data
        String token = "";

        // execute invalidateToken method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/invalidate").content(token)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 400
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();

        assertEquals(400,mvcResult.getResponse().getStatus());
    }

    /**
     * Test invalidateToken method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testInvalidate_Ok() throws Exception {

        // test data
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImE1NzAxNTgyIiwiZXhwIjoxNjQ4NjIzMDE3LCJ1c2VybmFtZSI6ImphY2sifQ.A5j88OLJm5qs3rrZ2PSjuGtRgt4G8qSAY6ZYbXdBxcA";

        Mockito.when(validationService.invalidate(token)).thenReturn(true);

        // execute invalidateToken method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/invalidate").param("token",token)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                // assert 400
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
        assertEquals("{\"statusCode\":200,\"message\":\"invalidate token successfully!\",\"result\":null}",mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test checkRole method.
     * Case: if token is empty ,then ng..
     *
     * @throws Exception
     */
    @Test
    public void testCheckRole_Ng() throws Exception {

        // test data
        String token = "";

        // execute checkRole method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/checkrole").param("token",token)
                )
                // assert 400
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();

        assertEquals(400,mvcResult.getResponse().getStatus());
    }

    /**
     * Test checkRole method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testCheckRole_Ok() throws Exception {

        // test data
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImE1NzAxNTgyIiwiZXhwIjoxNjQ4NjIzMDE3LCJ1c2VybmFtZSI6ImphY2sifQ.A5j88OLJm5qs3rrZ2PSjuGtRgt4G8qSAY6ZYbXdBxcA";

        Mockito.when(validationService.invalidate(token)).thenReturn(false);

        // execute checkRole method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/checkrole").param("token",token).param("roleName","admin"))
                // assert 400
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
        assertEquals("{\"statusCode\":200,\"message\":\"check role from token successfully!\",\"result\":false}",mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test allRoles method.
     * Case: if token is empty ,then ng..
     *
     * @throws Exception
     */
    @Test
    public void testAllRoles_Ng() throws Exception {

        // test data
        String token = null;

        // execute allRoles method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/allRoles").param("token",token)
        )
                // assert 400
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();

        assertEquals(400,mvcResult.getResponse().getStatus());
    }

    /**
     * Test allRoles method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testAllRoles_Ok() throws Exception {

        // test data
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImE1NzAxNTgyIiwiZXhwIjoxNjQ4NjIzMDE3LCJ1c2VybmFtZSI6ImphY2sifQ.A5j88OLJm5qs3rrZ2PSjuGtRgt4G8qSAY6ZYbXdBxcA";

        List<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("visitor");
        Mockito.when(validationService.allRoles(token)).thenReturn(roles);

        // execute allRoles method

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/allRoles").param("token",token)
        )
                // assert 200
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
        assertEquals("{\"statusCode\":200,\"message\":\"get all roles from token successfully!\",\"result\":[\"admin\",\"visitor\"]}",mvcResult.getResponse().getContentAsString());
    }

}
