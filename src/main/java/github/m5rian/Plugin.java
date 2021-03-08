package github.m5rian;

import github.m5rian.commands.discord.LinkAccount;
import github.m5rian.commands.minecraft.ConfirmVerify;
import github.m5rian.listeners.discord.DiscordChat;
import github.m5rian.listeners.minecraft.MinecraftChat;
import github.m5rian.listeners.minecraft.MinecraftJoinColour;
import github.m5rian.listeners.minecraft.MinecraftJoinLeaveMessage;
import github.m5rian.utils.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
    // Discord listeners
    private final DiscordChat discordChat = new DiscordChat();
    private final LinkAccount linkAccount = new LinkAccount();
    // Minecraft commands
    private final ConfirmVerify confirmVerify = new ConfirmVerify();

    /**
     * This method runs once the plugin is loaded.
     */
    @Override
    public void onEnable() {
        try {

            // Build bot
            final JDA jda = JDABuilder.createLight(Config.get().getString("token"))
                    .addEventListeners(discordChat, linkAccount)
                    .setActivity(Activity.playing("Minecraft"))
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .build().awaitReady();

            final long guildId = Config.get().getLong("guildId"); // Get guild id
            Config.getGuild = jda.getGuildById(guildId); // initialize guild variable

            // Register listener
            final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            pluginManager.registerEvents(new MinecraftChat(), this);
            pluginManager.registerEvents(new MinecraftJoinColour(), this);
            pluginManager.registerEvents(new MinecraftJoinLeaveMessage(), this);
            // Register commands
            Bukkit.getPluginCommand("verify").setExecutor(confirmVerify);
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
