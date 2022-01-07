package com.github.m5rian.discordMinecraftIntegration.listeners.minecraft;

import com.github.m5rian.discordMinecraftIntegration.DiscordBridge;
import com.github.m5rian.discordMinecraftIntegration.utils.Config;
import com.github.m5rian.discordMinecraftIntegration.utils.Webhook;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MinecraftChat implements Listener {
    private final Config config;

    public MinecraftChat(DiscordBridge discordBridge) {
        this.config = discordBridge.getConfiguration();
    }

    @EventHandler
    public void onMessage(AsyncChatEvent event) throws Exception {
        final String message = PlainTextComponentSerializer.plainText().serialize(event.message()); // Get content of message
        final String userName = PlainTextComponentSerializer.plainText().serialize(event.getPlayer().displayName()); // Get username
        final String avatar = "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId();

        Webhook webhook = new Webhook(config.getString("discord.webhookUrl"));
        webhook.setAvatarUrl(avatar);
        webhook.setUsername(userName);
        webhook.setContent(String.format("%s\u2503%s", config.getString("discord.minecraftEmoji"), message));
        webhook.send();
    }
}
