package github.m5rian.utils;

import github.m5rian.DiscordBridge;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Config {

    private final FileConfiguration config;
    private final DiscordBridge discordBridge;

    public Config(DiscordBridge discordBridge) {
        this.config = discordBridge.getConfig();
        this.discordBridge = discordBridge;
        // Save config file. Only runs if there isn't a config file yet
        this.discordBridge.saveDefaultConfig();
    }

    public void load() {
        // No webhook has been set up yet
        if (getString("discord.webhookUrl").equals("none")) {
            final Guild guild = discordBridge.getJda().getGuildById(getLong("discord.guildId")); // Get bound guild
            final TextChannel textChannel = guild.getTextChannelById(getLong("discord.channelId"));
            // Create new webhook and save it in the config file
            textChannel.createWebhook("Minecraft bridge").queue(webhook -> {
                this.config.set("discord.webhookUrl", webhook.getUrl());
                save();
            });
        }
    }

    public Long getLong(@NotNull String key) {
        return this.config.getLong(key, 0L);
    }

    public String getString(@NotNull String key) {
        return this.config.getString(key, "");
    }

    public void save() {
        this.discordBridge.saveConfig();
    }
}
