package github.m5rian.utils;

import net.dv8tion.jda.api.entities.Guild;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Config {

    public static Guild getGuild;

    public final static String prefix = "m!"; // Discord bot prefix

    private final static File folder = new File(System.getProperty("user.dir") + File.separator + "plugins");

    public static JSONObject get() throws Exception {
        final File config = new File(folder.getPath() + File.separator + "DMIConfig.json");

        // Config file doesn't exist
        if (!config.exists()) {
            final String content = "{" +
                    "\n\"token\": \"\"," +
                    "\n \"webhookUrl\": \"\"," +
                    "\n \"guildId\": " +
                    "\n \"channelId\": " +
                    "\n \"minecraftEmoji\": \"\"," +
                    "\n}"; // Create config raw text
            Files.write(Paths.get(config.getPath()), content.getBytes()); // Write content as file

            throw new Exception("Please setup the config file");
        }

        final StringBuilder output = new StringBuilder();
        final Scanner scanner = new Scanner(new FileReader(config)); // Create scanner from file
        while (scanner.hasNext()) output.append(scanner.next()); // Add all lines to output

        return new JSONObject(output.toString()); // Create JSONObject from output
    }

}
