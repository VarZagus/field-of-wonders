package com.varzagus.fow;

import com.varzagus.fow.controllers.AdminController;
import com.varzagus.fow.controllers.GameController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class AdminControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminController adminController;

    @Autowired
    private GameController gameController;


    @Test
    void get_user_list_page_test() throws Exception {
        this.mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Данные пользователей")));
    }

    @Test
    void get_question_list_page_test() throws Exception {
        this.mockMvc.perform(get("/admin/questions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Споисок вопросов")));
    }

}
