package com.mujah.minidb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseTest {

    @TempDir
    Path tempDir;

    @Test
    void setGetAndDeleteWork() throws IOException {
        Path databaseFile = tempDir.resolve("test.db");
        Database database = new Database(databaseFile.toString());

        try {
            database.set("name", "Mujah");

            assertEquals("Mujah", database.get("name"));
            assertTrue(database.exists("name"));

            database.delete("name");

            assertFalse(database.exists("name"));
            assertNull(database.get("name"));
        } finally {
            database.close();
        }
    }
}