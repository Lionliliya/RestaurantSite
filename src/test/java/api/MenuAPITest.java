package api;

import com.gmail.liliyayalovchenko.web.api.MenuAPI;
import com.gmail.liliyayalovchenko.web.configuration.WebConfig;
import com.gmail.liliyayalovchenko.web.configuration.WebInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, WebInitializer.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class MenuAPITest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private MenuAPI menuAPI;

    private static final String wrongMenuName = "WrongMenuName";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(menuAPI).build();
    }

    @Test
    public void shouldFindById() throws Exception {
        mockMvc.perform(get("/menu/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$", hasKey("name")))
                .andExpect(jsonPath("$.dishList", notNullValue()))
                .andExpect(jsonPath("$.dishList.[0]", hasKey("id")))
                .andExpect(jsonPath("$.dishList.[0]", hasKey("name")))
                .andExpect(jsonPath("$.dishList.[0].ingredients.[0]", hasKey("name")))
                .andExpect(jsonPath("$.dishList.[0]", not(hasKey("menu"))))
                .andExpect(jsonPath("$.dishList.[0]", not(hasKey("photoLink"))))
                .andExpect(jsonPath("$.dishList.[0]", hasKey("ingredients")));
    }

    @Test
    public void shouldFindByName() throws Exception {
        mockMvc.perform(get("/menu/name/{name}", "Salads"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$", hasKey("name")))
                .andExpect(jsonPath("$.dishList.[0]", hasKey("id")))
                .andExpect(jsonPath("$.dishList.[0]", hasKey("name")))
                .andExpect(jsonPath("$.dishList.[0].ingredients.[0]", hasKey("id")))
                .andExpect(jsonPath("$.dishList.[0].ingredients.[0]", hasKey("name")))
                .andExpect(jsonPath("$.dishList.[0]", not(hasKey("menu"))))
                .andExpect(jsonPath("$.dishList.[0]", not(hasKey("photoLink"))))
                .andExpect(jsonPath("$.dishList.[0]", hasKey("ingredients")));
    }

    @Test
    public void shouldFindAllMenuOnlyNames() throws Exception {
        mockMvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0]", not(hasKey("id"))))
                .andExpect(jsonPath("$.[0]", not(hasKey("dishList"))))
                .andExpect(jsonPath("$.[1]", hasKey("name")));
    }

    @Test
    public void menuNotFound() throws Exception {
        mockMvc.perform(get("/menu/name/{menuName}", wrongMenuName))
                .andExpect(status().isNotFound());
    }
}