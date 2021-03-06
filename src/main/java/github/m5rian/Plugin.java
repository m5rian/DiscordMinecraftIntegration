package github.m5rian;

import github.m5rian.listeners.discord.DiscordChat;
import github.m5rian.listeners.minecraft.MinecraftChat;
import github.m5rian.utils.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private final DiscordChat discordChat = new DiscordChat();

    /**
     * This method runs once the plugin is loaded.
     */
    @Override
    public void onEnable() {
        try {

            // Build bot
            JDABuilder.createLight(Config.get().getString("token"))
                    .addEventListeners(discordChat)
                    .setActivity(Activity.playing("Minecraft"))
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .build();

            // Register commands
            final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            pluginManager.registerEvents(new MinecraftChat(), this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method runs once the plugin is unloaded.
     */
    @Override
    public void onDisable() {
    }
}
