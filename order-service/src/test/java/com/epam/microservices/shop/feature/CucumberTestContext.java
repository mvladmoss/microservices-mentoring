package com.epam.microservices.shop.feature;

import com.epam.microservices.shop.ApplicationInitializer;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = ApplicationInitializer.class)
public class CucumberTestContext {

}
