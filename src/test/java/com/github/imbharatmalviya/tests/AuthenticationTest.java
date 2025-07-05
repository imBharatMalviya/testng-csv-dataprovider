package com.github.imbharatmalviya.tests;

import com.github.imbharatmalviya.CsvDataProvider;
import com.github.imbharatmalviya.LoginTestData;
import com.github.imbharatmalviya.TestDataFile;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

@Slf4j
public class AuthenticationTest {

    @Test(dataProvider = "csvDataProvider", dataProviderClass = CsvDataProvider.class)
    @TestDataFile(fileName = "login-tests.csv", clazz = LoginTestData.class)
    public void loginTest(LoginTestData testData) {
        log.info("loginTestData: {}", testData);
    }

}


