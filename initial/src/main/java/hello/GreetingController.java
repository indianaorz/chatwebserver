package hello;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="id", defaultValue="0") String id) {

    String sql = "SELECT * FROM USERS WHERE ID = ?";

	User user = (User)jdbcTemplate.queryForObject(
			sql, new Object[] { Long.parseLong(id) }, new UserRowMapper());

    return new Greeting(counter.incrementAndGet(),
                            String.format(template, user.toString()));
    }
}   