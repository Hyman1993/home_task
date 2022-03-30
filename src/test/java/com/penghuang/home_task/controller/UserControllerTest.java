package com.penghuang.home_task.controller;

import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.entity.Users;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.service.UserService;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test class for userController.
 */
@EnableWebMvc
@AutoConfigureMockMvc
@SpringBootTest(classes = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test createUser method.
     * Case: if username or password is empty, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUser_Ng() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"\",\n" +
                "    \"password\":\"\"\n" +
                "}";

        // execute createUser method
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 500
                    .andExpect(MockMvcResultMatchers.status().is(500)).andReturn();
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: username or password can't be empty!",e.getMessage());
        }

    }

    /**
     * Test createUser method.
     * Case: if user already exist, then ng
     *
     * @throws Exception
     */
    @Test
    public void testCreateUser_Ng2() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"tom\",\n" +
                "    \"password\":\"xxxx\"\n" +
                "}";

        User userObj = new User();
        userObj.setName("tom");
        userObj.setPassword("xxxx");
        Users.add(userObj);

        Mockito.when(userService.createUser(userObj)).thenThrow(new SystemException("create user failed,because the user already exist!" + ", username:" + userObj.getName() ));

        // execute createUser method
        try {
                 mockMvc.perform(MockMvcRequestBuilders.post("/user").content(user)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE));

        } catch (SystemException e) {
            assertEquals("create user failed,because the user already exist!, username:tom",e.getMessage());
        }

    }

    /**
     * Test createUser method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUser_Ok() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"tom\",\n" +
                "    \"password\":\"a5701582\"\n" +
                "}";

        // execute createUser method
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user").content(user)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                // assert 201
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();

        // assert response code
        assertEquals(201,mvcResult.getResponse().getStatus());

        // assert message
        assertEquals("{\"statusCode\":201,\"message\":\"Create user Successfully!\",\"result\":null}",mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test deleteUser method.
     * Case: if username or password is empty, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testDeleteUser_Ng() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"\",\n" +
                "    \"password\":\"\"\n" +
                "}";

        // execute createUser method
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/user").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 500
                    .andExpect(MockMvcResultMatchers.status().is(500)).andReturn();
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: username can't be empty!",e.getMessage());
        }

    }

    /**
     * Test deleteUser method.
     * Case: if user doesn't exist, then ng
     *
     * @throws Exception
     */
    @Test
    public void testDeleteUser_Ng2() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"tom\",\n" +
                "    \"password\":\"xxxx\"\n" +
                "}";

        User userObj = new User();
        userObj.setName("tom");
        userObj.setPassword("xxxx");

        Mockito.when(userService.deleteUser(userObj)).thenThrow(new SystemException("delete user failed, because the user doesn't exist!" + ", username:" + userObj.getName() ));

        // execute createUser method
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/user").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE));

        } catch (SystemException e) {
            assertEquals("delete user failed, because the user doesn't exist!, username:tom",e.getMessage());
        }

    }

    /**
     * Test deleteUser method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testDeleteUser_Ok() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"tom\",\n" +
                "    \"password\":\"xxxx\"\n" +
                "}";

        // execute createUser method
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/user").content(user)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                // assert 200
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        // assert response code
        assertEquals(200,mvcResult.getResponse().getStatus());

        // assert message
        assertEquals("{\"statusCode\":200,\"message\":\"Delete user Successfully!\",\"result\":null}",mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test addRole2User method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testAddRole2User_Ok() throws Exception {

        // test data
        String user = "{\n" +
                "   \"user\":{\n" +
                "            \"name\":\"jack\"\n" +
                "            },\n" +
                "    \"role\":{\n" +
                "            \"roleName\":\"visitor\"\n" +
                "     }\n" +
                "\n" +
                "}";

        // execute addRole2User method
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/role2user").content(user)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                // assert 200
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        // assert response code
        assertEquals(200,mvcResult.getResponse().getStatus());

        // assert message
        assertEquals("{\"statusCode\":200,\"message\":\"Add role to user Successfully!\",\"result\":null}",mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test addRole2User method.
     * Case: if role is empty, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testAddRole2User_Ng() throws Exception {

        // test data
        String user = "{\n" +
                "   \"user\":{\n" +
                "            \"name\":\"jack\"\n" +
                "            },\n" +
                "    \"role\":{\n" +
                "            \"roleName\":\"\"\n" +
                "     }\n" +
                "\n" +
                "}";

        // execute addRole2User method
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/role2user").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 500
                    .andExpect(MockMvcResultMatchers.status().is(500)).andReturn();
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: roleName can't be empty!",e.getMessage());
        }

    }

    /**
     * Test addRole2User method.
     * Case: if username is empty, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testAddRole2User_Ng2() throws Exception {

        // test data
        String user = "{\n" +
                "   \"user\":{\n" +
                "            \"name\":\"\"\n" +
                "            },\n" +
                "    \"role\":{\n" +
                "            \"roleName\":\"visitor\"\n" +
                "     }\n" +
                "\n" +
                "}";

        // execute addRole2User method
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/role2user").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 500
                    .andExpect(MockMvcResultMatchers.status().is(500)).andReturn();
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: userName can't be empty!",e.getMessage());
        }

    }
}
