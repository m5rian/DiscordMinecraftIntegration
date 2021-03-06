package github.m5rian.listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Bukkit;

public class DiscordChat extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getMessage().isWebhookMessage()) return;

        final String message = event.getMessage().getContentDisplay();
        final String username = event.getAuthor().getName();

        TextComponent msg = new PlainComponentSerializer().deserialize(String.format("<%s> %s", username, message));
        Bukkit.getServer().sendMessage(msg);
    }
}
