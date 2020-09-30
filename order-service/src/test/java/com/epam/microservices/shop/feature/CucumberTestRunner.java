package com.epam.microservices.shop.feature;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com.epam.microservices.shop.feature")
public class CucumberTestRunner {
}
