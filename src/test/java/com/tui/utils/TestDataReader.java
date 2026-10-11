
package com.tui.utils;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestDataReader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private TestDataReader() {
        // Utility class; prevent object creation.
    }

    public static JsonNode readTestData(String fileName) {
        String resourcePath = "testdata/" + fileName;

        try (InputStream input = TestDataReader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (input == null) {
                throw new IllegalArgumentException(
                    "Test data file not found: " + resourcePath
                );
            }

            return OBJECT_MAPPER.readTree(input);

        } catch (IOException e) {
            throw new IllegalStateException(
                "Failed to read test data file: " + resourcePath,
                e
            );
        }
    }

    public static String getString(
            String fileName,
            String section,
            String key) {

        JsonNode root = readTestData(fileName);
        JsonNode value = root.path(section).path(key);

        if (value.isMissingNode() || value.isNull()) {
            throw new IllegalArgumentException(
                "Missing test data: " + section + "." + key
            );
        }

        return value.asText();
    }
}