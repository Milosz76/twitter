package mvc.controller;

import mvc.repository.MessageRepository;
import mvc.service.MessageService;
import mvc.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MessageService messageService;

    @Mock
    private UserService userService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    @WithMockUser(username = "john", roles = {"USER"})
    void shouldRenderAddNewMessageView() throws Exception {
        mockMvc.perform(get("/useraddcontent"))
                .andExpect(status().isOk())
                .andExpect(view().name("useraddcontent"));
    }

    @Test
    @WithMockUser(username = "john", roles = {"USER"})
    void shouldRedirectToUserPageWhenMessageSaved() throws Exception {
        Mockito.when(userService.isUserMarkedAsBanned("john")).thenReturn(false);

        mockMvc.perform(post("/useraddcontent")
                        .param("message", "Lorem ipsum dolor sit amet"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "john", roles = {"USER"})
    void shouldRedirectToBannedPageWhenUserIsBanned() throws Exception {
        Mockito.when(userService.isUserMarkedAsBanned("john")).thenReturn(true);

        mockMvc.perform(post("/useraddcontent")
                        .param("message", "Lorem ipsum dolor sit amet"))
                .andExpect(status().isOk())
                .andExpect(view().name("banned"));
    }
}
