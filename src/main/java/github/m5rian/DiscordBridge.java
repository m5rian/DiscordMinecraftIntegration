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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DiscordBridge extends JavaPlugin {
    // Minecraft listeners
    private MinecraftChat minecraftChat;
    private MinecraftJoinColour minecraftJoinColour;
    private MinecraftJoinLeaveMessage minecraftJoinLeaveMessage;
    // Discord listeners
    private DiscordChat discordChat;
    private LinkAccount linkAccount;
    // Minecraft commands
    private ConfirmVerify confirmVerify;

    private Config config;
    private JDA jda;

    /**
     * This method runs once the plugin is loaded.
     */
    @Override
    public void onEnable() {
        try {
            this.config = new Config(this); // Load config file

            // Build bot
            jda = JDABuilder.createLight(config.getString("discord.token"))
                    .setActivity(Activity.playing("Minecraft"))
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .build().awaitReady();

            this.discordChat = new DiscordChat(this);
            this.linkAccount = new LinkAccount(this);

            this.minecraftChat = new MinecraftChat(this);
            this.minecraftJoinColour = new MinecraftJoinColour(this);
            this.minecraftJoinLeaveMessage = new MinecraftJoinLeaveMessage(this);

            this.confirmVerify = new ConfirmVerify();
            // Register discord listeners
            jda.addEventListener(discordChat, linkAccount);
            // Register minecraft listeners
            final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            pluginManager.registerEvents(this.minecraftChat, this);
            pluginManager.registerEvents(this.minecraftJoinColour, this);
            pluginManager.registerEvents(this.minecraftJoinLeaveMessage, this);
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

    /**
     * @return Returns the configuration file.
     */
    public Config getConfiguration() {
        return config;
    }

    public Guild getBoundGuild() {
        return jda.getGuildById(config.getLong("discord.guildId"));
    }
}