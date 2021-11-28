package com.varzagus.fow;


import com.varzagus.fow.configs.MvcConfig;
import com.varzagus.fow.controllers.GameController;
import com.varzagus.fow.controllers.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    void redirect_to_login_page_for_not_authorize_user() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void get_login_page() throws Exception {

    }

    @Test
    void correct_login_test() throws Exception {
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("admin").password("123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void incorrect_login_test() throws Exception {
        this.mockMvc.perform(post("/login").param("login","kjb"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void get_registration_page_test() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Регистрация пользователя")));
    }
    @Test
    void get_userinfo_page_with_no_login_test() throws Exception{
        this.mockMvc.perform(get("/userinfo"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
