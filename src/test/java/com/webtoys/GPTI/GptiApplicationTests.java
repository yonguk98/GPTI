package com.webtoys.GPTI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = GptiApplication.class)
@ContextConfiguration(classes = GptiApplication.class)
@ActiveProfiles("secret")
class GptiApplicationTests {

	@Test
	void contextLoads() {
	}

}
