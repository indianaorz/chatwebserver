package com.colur;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * @author Josh Long
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ColorRestControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private User user;

    private List<Color> colorList = new ArrayList<>();

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.colorRepository.deleteAllInBatch();
        this.userRepository.deleteAllInBatch();

        this.user = userRepository.save(new User(userName));
        this.colorList.add(colorRepository.save(new Color(user,"FF00AA","1/1/1")));
        this.colorList.add(colorRepository.save(new Color(user,"FFAAFF","2/2/2")));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/george/colors/")
                .content(this.json(new Color(null, null, null)))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSinglecolor() throws Exception {
        mockMvc.perform(get("/" + userName + "/colors/"
                + this.colorList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.colorList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.color", is("FF00AA")))
                .andExpect(jsonPath("$.date", is("1/1/1")));
    }

    @Test
    public void readcolors() throws Exception {
        mockMvc.perform(get("/" + userName + "/colors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.id", is(this.colorList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.color", is("FF00AA")))
                .andExpect(jsonPath("$.date", is("1/1/1")))
                .andExpect(jsonPath("$.id", is(this.colorList.get(1).getId().intValue())))
                .andExpect(jsonPath("$.color", is("FFAAFF")))
                .andExpect(jsonPath("$.date", is("2/2/2")));
    }

    @Test
    public void createcolor() throws Exception {
        String colorJson = json(new Color(
                this.user, "00000", "6/5/47"));

        this.mockMvc.perform(post("/" + userName + "/colors")
                .contentType(contentType)
                .content(colorJson))
                .andExpect(status().isCreated());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}