package com.github.imbharatmalviya;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class CsvDataProvider {

    @DataProvider(name = "csvDataProvider", parallel = true)
    public static Iterator<Object> provideData(Method method) {
        TestDataFile annotation = method.getAnnotation(TestDataFile.class);
        if (annotation == null) {
            throw new RuntimeException("Missing @TestDataFile annotation");
        }
        String fileName = annotation.fileName();
        Class dataClass = annotation.clazz();
        List<Object> dataList = CsvUtil.readCsv(fileName, dataClass);
        return dataList.iterator();
    }
}
