package api;

import com.gmail.liliyayalovchenko.web.api.EmployeeAPI;
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
public class EmployeeAPITest {


    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private EmployeeAPI employeeAPI;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeAPI).build();
    }

    @Test
    public void shouldFindById() throws Exception {
        mockMvc.perform(get("/employee/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$", hasKey("firstName")))
                .andExpect(jsonPath("$", hasKey("secondName")))
                .andExpect(jsonPath("$", hasKey("position")));
    }

    @Test
    public void shouldFindAllEmployee() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0]", hasEntry("firstName", "Suise")))
                .andExpect(jsonPath("$[0]", not(hasEntry("id", 1))))
                .andExpect(jsonPath("$[0]", not(hasEntry("id", 1))))
                .andExpect(jsonPath("$[0]", hasKey("secondName")))
                .andExpect(jsonPath("$[0]", hasKey("firstName")));

    }

    @Test
    public void shouldFindEmployeeByFirstName() throws Exception {
        mockMvc.perform(get("/employee/firstName/James"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", hasKey("id")))
                .andExpect(jsonPath("$[0]", hasKey("firstName")))
                .andExpect(jsonPath("$[0]", hasKey("secondName")))
                .andExpect(jsonPath("$[0]", hasKey("emplDate")))
                .andExpect(jsonPath("$[0]", hasKey("position")))
                .andExpect(jsonPath("$[0].orderList", notNullValue()));
    }


    @Test
    public void employeeNotFound() throws Exception {
        mockMvc.perform(get("/employee/{id}", 23))
                .andExpect(status().isNotFound());
    }
}