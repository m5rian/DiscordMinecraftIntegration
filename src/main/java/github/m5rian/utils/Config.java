package github.m5rian.utils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Config {

    private final static File folder = new File(System.getProperty("user.dir") + File.separator + "plugins");

    public static JSONObject get() throws Exception {
        final File config = new File(folder.getPath() + File.separator + "JDChatConfig.json");

        // Config file doesn't exist
        if (!config.exists()) {
            final String content = "{\"token\": \"\", \"webhookUrl\": \"\"}"; // Create config raw text
            Files.write(Paths.get(config.getPath()), content.getBytes()); // Write content as file

            throw new Exception("Please setup the config file");
        }

        final StringBuilder output = new StringBuilder();
        final Scanner scanner = new Scanner(new FileReader(config)); // Create scanner from file
        while (scanner.hasNext()) output.append(scanner.next()); // Add all lines to output

        return new JSONObject(output.toString()); // Create JSONObject from output
    }

}
