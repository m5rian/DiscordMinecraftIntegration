package github.m5rian.utils;

import github.m5rian.DiscordBridge;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Config {

    private final FileConfiguration config;
    private final DiscordBridge discordBridge;

    public Config(DiscordBridge discordBridge) {
        this.config = discordBridge.getConfig();
        this.discordBridge = discordBridge;
        this.discordBridge.saveDefaultConfig();
    }

    public void reload() {
        this.discordBridge.reloadConfig();
    }

    public Long getLong(@NotNull String key) {
        return this.config.getLong(key, 0L);
    }

    public String getString(@NotNull String key) {
        return this.config.getString(key, "");
    }
}
