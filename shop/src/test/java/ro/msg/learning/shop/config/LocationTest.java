package ro.msg.learning.shop.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ro.msg.learning.shop.domain.repository.LocationRepository;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationTest {

    @Autowired
    private LocationRepository locationRepository;

    @Container
    private static PostgreSQLContainer postgreSQLContainer = AbstractPostgreSQLContainer.getInstance();


    @Test
    public void loadContext(){
        System.out.println("Sda");
    }

}
