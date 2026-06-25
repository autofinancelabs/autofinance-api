package com.autofinance.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Needs a database + JPA mapping; re-enabled in the persistence slice (Testcontainers + tenant resolver)")
class AutofinanceApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
