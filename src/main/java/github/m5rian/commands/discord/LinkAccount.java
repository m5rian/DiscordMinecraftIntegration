package github.m5rian.commands.discord;

import github.m5rian.utils.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Random;

public class LinkAccount extends ListenerAdapter {

    public static final HashMap<String, Long> verify = new HashMap<>();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        try {
            if (event.getGuild().getIdLong() != Config.get().getLong("guildId")) return; // Wrong guild

            final String message = event.getMessage().getContentRaw(); // Get message

            if (message.equalsIgnoreCase(Config.prefix + "link account")) {
                event.getAuthor().openPrivateChannel().queue(dm -> {
                    final String randomCode = getRandomCode(); // Get random code

                    EmbedBuilder verifyCode = new EmbedBuilder()
                            .setTitle("Verify")
                            .setDescription("Verify your account and type in the minecraft chat the following command\n```/verify " + randomCode + "```");
                    dm.sendMessage(verifyCode.build()).queue(); // Send dm

                    verify.put(randomCode, event.getAuthor().getIdLong()); // Add code to HashMap
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Character[] chars = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private String getRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            final int random = new Random().nextInt(chars.length); // Get random number
            code.append(chars[random]);
        }
        return code.toString();
    }
}
