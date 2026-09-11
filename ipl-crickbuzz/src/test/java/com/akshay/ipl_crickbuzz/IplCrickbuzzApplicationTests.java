package com.akshay.ipl_crickbuzz;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.akshay.iplcrickbuzz.IplCrickbuzzApplication;

@Disabled("Requires active database connection")
@SpringBootTest(classes = IplCrickbuzzApplication.class)
class IplCrickbuzzApplicationTests {

	@Test
	void contextLoads() {
	}

}
