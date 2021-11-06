package com.neutronbinary.infectolabs.microservice.cucumber;

import com.neutronbinary.infectolabs.microservice.NbSolarCoreApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = NbSolarCoreApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
