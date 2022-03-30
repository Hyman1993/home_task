package com.penghuang.home_task.controller;

import com.penghuang.home_task.dto.Role;
import com.penghuang.home_task.dto.User;
import com.penghuang.home_task.exception.SystemException;
import com.penghuang.home_task.service.RoleService;
import com.penghuang.home_task.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
 * Test class for roleController.
 */
@EnableWebMvc
@AutoConfigureMockMvc
@SpringBootTest(classes = RoleController.class)
public class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test createRole method.
     * Case: if roleName is empty, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testCreateRole_Ng() throws Exception {

        // test data
        String role = "{\n" +
                "    \"roleName\":\"\"\n" +
                "}";

        // execute createRole method
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/role").content(role)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 500
                    .andExpect(MockMvcResultMatchers.status().is(500)).andReturn();
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: roleName can't be empty!",e.getMessage());
        }

    }

    /**
     * Test createRole method.
     * Case: if roleName already exist, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testCreateRole_Ng2() throws Exception {

        // test data
        String testRole = "{\n" +
                "    \"roleName\":\"admin\"\n" +
                "}";

        Role expectRole = new Role();
        expectRole.setRoleName("admin");
        Mockito.when(roleService.createRole(expectRole)).thenThrow(new SystemException("create role failed,because the role already exist!" + ", roleName:" + expectRole.getRoleName() ));

        // execute createRole method
        try {
             mockMvc.perform(MockMvcRequestBuilders.post("/role").content(testRole));
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: create role failed,because the role already exist!, roleName:admin",e.getMessage());
        }
    }

    /**
     * Test createRole method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUser_Ok() throws Exception {

        // test data
        String testRole = "{\n" +
                "    \"roleName\":\"admin\"\n" +
                "}";

        // execute createRole method
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/role").content(testRole)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                // assert 201
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();

        // assert response code
        assertEquals(201,mvcResult.getResponse().getStatus());

        // assert message
        assertEquals("{\"statusCode\":201,\"message\":\"Create role Successfully!\",\"result\":null}",mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test deleteRole method.
     * Case: if roleName is empty, then Ng.
     *
     * @throws Exception
     */
    @Test
    public void testDeleteRole_Ng() throws Exception {

        // test data
        String role = "{\n" +
                "    \"roleName\":\"\"\n" +
                "}";

        // execute deleteRole method
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/role").content(role)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                    // assert 500
                    .andExpect(MockMvcResultMatchers.status().is(500)).andReturn();
        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: roleName can't be empty!",e.getMessage());
        }

    }

    /**
     * Test deleteRole method.
     * Case: if role doesn't exist, then ng
     *
     * @throws Exception
     */
    @Test
    public void testDeleteRole_Ng2() throws Exception {

        // test data
        String user = "{\n" +
                "    \"name\":\"tom\",\n" +
                "    \"password\":\"xxxx\"\n" +
                "}";

        Role roleObj = new Role();
        roleObj.setRoleName("admin");

        Mockito.when(roleService.deleteRole(roleObj)).thenThrow(new SystemException("delete role failed, because the role doesn't exist!" + ", rolename:" + roleObj.getRoleName() ));

        // execute deleteRole method
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/role").content(user)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE));

        } catch (Exception e) {
            assertEquals("Request processing failed; nested exception is com.penghuang.home_task.exception.SystemException: roleName can't be empty!",e.getMessage());
        }

    }

    /**
     * Test deleteRole method.
     * Case: ok.
     *
     * @throws Exception
     */
    @Test
    public void testDeleteRole_Ok() throws Exception {

        // test data
        String role = "{\n" +
                "    \"roleName\":\"admin\"\n" +
                "}";

        // execute deleteRole method
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/role").content(role)
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
                // assert 200
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        // assert response code
        assertEquals(200,mvcResult.getResponse().getStatus());

        // assert message
        assertEquals("{\"statusCode\":200,\"message\":\"Delete role Successfully!\",\"result\":null}",mvcResult.getResponse().getContentAsString());
    }
}
