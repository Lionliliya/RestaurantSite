package api;

import com.gmail.liliyayalovchenko.web.api.OrderAPI;
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
public class OrderAPITest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private OrderAPI orderAPI;

    private final static String wrongStatus = "waitStatus";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderAPI).build();
    }

    @Test
    public void shouldFindAllOrders() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0]", hasKey("id")))
                .andExpect(jsonPath("$.[0]", hasKey("employeeId")))
                .andExpect(jsonPath("$.[0].employeeId", hasKey("firstName")))
                .andExpect(jsonPath("$.[0].employeeId", hasKey("secondName")))
                .andExpect(jsonPath("$.[0].employeeId", not(hasKey("id"))))
                .andExpect(jsonPath("$.[0].employeeId", not(hasKey("orderList"))))
                .andExpect(jsonPath("$.[0]", hasKey("dishList")))
                .andExpect(jsonPath("$.[0].dishList.[0]", not(hasKey("ingredients"))))
                .andExpect(jsonPath("$.[0].dishList.[0]", hasKey("name")))
                .andExpect(jsonPath("$.[1]", hasKey("id")))
                .andExpect(jsonPath("$.[1]", hasKey("employeeId")));

    }

    @Test
    public void shouldClosedOrders() throws Exception {
        mockMvc.perform(get("/order/status/closed"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0]", hasKey("id")))
                .andExpect(jsonPath("$.[0]", hasKey("employeeId")))
                .andExpect(jsonPath("$.[0].employeeId", hasKey("firstName")))
                .andExpect(jsonPath("$.[0].employeeId", hasKey("secondName")))
                .andExpect(jsonPath("$.[0].employeeId", not(hasKey("id"))))
                .andExpect(jsonPath("$.[0].employeeId", not(hasKey("orderList"))))
                .andExpect(jsonPath("$.[0]", hasKey("dishList")))
                .andExpect(jsonPath("$.[0].dishList.[0]", not(hasKey("ingredients"))))
                .andExpect(jsonPath("$.[0].dishList.[0]", hasKey("name")));

    }

    @Test
    public void ordersNotFound() throws Exception {
        mockMvc.perform(get("/menu/status/{wrongStatus}", wrongStatus))
                .andExpect(status().isNotFound());
    }

}