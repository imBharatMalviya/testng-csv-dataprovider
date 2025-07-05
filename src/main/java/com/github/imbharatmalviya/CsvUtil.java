package com.github.imbharatmalviya;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.InputStream;
import java.util.List;

public class CsvUtil {

    public static <T> List<T> readCsv(String fileName, Class<T> clazz) {
        try (InputStream is = CsvUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            ObjectReader reader = mapper.readerFor(clazz).with(schema);
            return reader.<T>readValues(is).readAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV file: " + fileName, e);
        }
    }
}