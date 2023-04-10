package ro.msg.learning.shop.controller;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
@RequestMapping(value = "/api/v1/database" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class DataBaseController {

    @Autowired
    private Flyway flyway;

    @PostMapping("/setup")
    public ResponseEntity<String> populateDatabase() {
        flyway.migrate();
        return ResponseEntity.ok("DataBase is initialized");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> cleanDatabase(){
        flyway.clean();
        return ResponseEntity.ok("DataBase is deleted");
    }
}

