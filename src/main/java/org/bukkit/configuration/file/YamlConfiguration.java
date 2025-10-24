package org.bukkit.configuration.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlConfiguration {

    private final Map<String, Object> values = new LinkedHashMap<>();

    public void set(String path, Object value) {
        values.put(path, value);
    }

    public void save(File file) throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof List) {
                    writer.write(key + ":");
                    writer.newLine();
                    for (Object element : (List<?>) value) {
                        writer.write("- " + element);
                        writer.newLine();
                    }
                } else {
                    writer.write(key + ": " + value);
                    writer.newLine();
                }
            }
        }
    }
}
