package com.varzagus.fow;

import com.varzagus.fow.controllers.GameController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class GameControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameController gameController;

    @Test
    void get_main_page_test() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo((print()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Нажмите кнопку, чтобы начать поиск игры!")))
                .andExpect(content().string(containsString("admin")));
    }
}
