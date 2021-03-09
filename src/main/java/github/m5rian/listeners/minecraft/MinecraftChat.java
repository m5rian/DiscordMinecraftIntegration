package github.m5rian.listeners.minecraft;

import github.m5rian.DiscordBridge;
import github.m5rian.utils.Config;
import github.m5rian.utils.Webhook;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MinecraftChat implements Listener {
    private final Config config;

    public MinecraftChat(DiscordBridge discordBridge) {
        this.config = discordBridge.getConfiguration();
    }

    @EventHandler
    public void onMessage(AsyncChatEvent event) throws Exception {
        final String message = new PlainComponentSerializer().serialize(event.message()); // Get content of message
        final String userName = new PlainComponentSerializer().serialize(event.getPlayer().displayName()); // Get username
        final String avatar = "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId().toString();

        Webhook webhook = new Webhook(config.getString("discord.webhookUrl"));
        webhook.setAvatarUrl(avatar);
        webhook.setUsername(userName);
        webhook.setContent(String.format("%s\u2503%s", config.getString("discord.minecraftEmoji"), message));
        webhook.send();
    }
}
