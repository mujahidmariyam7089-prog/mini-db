package com.mujah.minidb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final DiskManager diskManager;
    private final BPlusTree index;

    public Database(String fileName) throws IOException {
        this.diskManager = new DiskManager(fileName);
        this.index = new BPlusTree();
        loadIndex();
    }

    private void loadIndex() throws IOException {
        List<String> lines = diskManager.readAllLines();
        long offset = 0;

        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }

            int equalsPosition = line.indexOf('=');

            if (equalsPosition == -1) {
                offset += line.length() + 1;
                continue;
            }

            String key = line.substring(0, equalsPosition);
            index.put(key, offset);

            offset += line.length() + 1;
        }
    }

    public void set(String key, String value) throws IOException {
        String line = key + "=" + value;
        long newOffset = diskManager.appendLine(line);
        index.put(key, newOffset);
    }

    public String get(String key) throws IOException {
        Long offset = index.get(key);
        if (offset == null) {
            return null;
        }

        String line = diskManager.readLineAt(offset);

        if (line == null) {
            return null;
        }

        int equalsPosition = line.indexOf('=');

        if (equalsPosition == -1) {
            return null;
        }

        String storedKey = line.substring(0, equalsPosition);

        if (!storedKey.equals(key)) {
            return null;
        }

        return line.substring(equalsPosition + 1);
    }

    public boolean exists(String key) {
        return index.get(key) != null;
    }
    public long increment(String key) throws IOException {
    String currentValue = get(key);
    long number;

    if (currentValue == null) {
        number = 0;
    } else {
        number = Long.parseLong(currentValue);
    }

    number++;
    set(key, String.valueOf(number));

    return number;
}

public long decrement(String key) throws IOException {
    String currentValue = get(key);
    long number;

    if (currentValue == null) {
        number = 0;
    } else {
        number = Long.parseLong(currentValue);
    }

    number--;
    set(key, String.valueOf(number));

    return number;
}

    public void delete(String key) {
        index.remove(key);
    }

    public List<String> listAll() throws IOException {
        List<String> result = new ArrayList<>();

        for (String key : index.keys()) {
            String value = get(key);

            if (value != null) {
                result.add(key + " = " + value);
            }
        }

        return result;
    }

    public void close() throws IOException {
        diskManager.close();
    }
}