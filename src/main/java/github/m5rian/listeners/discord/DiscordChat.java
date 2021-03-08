package github.m5rian.listeners.discord;

import github.m5rian.utils.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Bukkit;

public class DiscordChat extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        try {
            if (event.getMessage().isWebhookMessage()) return; // Message is webhook
            if (event.getGuild().getIdLong() != Config.get().getLong("guildId")) return; // Wrong guild
            if (Config.get().getLong("channelId") != event.getChannel().getIdLong()) return; // Wrong channel

            final String message = event.getMessage().getContentDisplay(); // Get message
            final String username = event.getAuthor().getName(); // Get username

            if (message.toLowerCase().startsWith(Config.prefix)) return; // Message is command
            Bukkit.getConsoleSender().sendMessage(message.toLowerCase());
            Bukkit.getConsoleSender().sendMessage(Config.prefix);

            TextComponent msg = new PlainComponentSerializer().deserialize(String.format("<%s> %s", username, message));
            Bukkit.getServer().sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
