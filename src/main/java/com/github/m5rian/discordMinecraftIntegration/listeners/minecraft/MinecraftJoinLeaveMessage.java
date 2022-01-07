package com.github.m5rian.discordMinecraftIntegration.listeners.minecraft;

import com.github.m5rian.discordMinecraftIntegration.DiscordBridge;
import com.github.m5rian.discordMinecraftIntegration.utils.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

public class MinecraftJoinLeaveMessage implements Listener {
    private final Config config;
    private final Guild guild;

    public MinecraftJoinLeaveMessage(DiscordBridge discordBridge) {
        this.config = discordBridge.getConfiguration();
        this.guild = discordBridge.getJda().getGuildById(this.config.getLong("discord.guildId"));
    }

    private static final String[] joinMessages = {
            "{user} joined!",
            "{user} appeared, watch out!",
            "{user} joined the party, hold my bear!",
            "{user} crashed in the server.. What is it?",
            "A {user} appeared, gotta catch 'em all",
            "{user} IS HERE!!! SAVE UR DIAMONDS"
    };

    private static final String[] leaveMessages = {
            "{user} left. It's time to shine",
            "{user} just left, time to steal diamonds!",
            "{user} left the party"
    };


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final String userName = PlainTextComponentSerializer.plainText().serialize(event.getPlayer().displayName()); // Get username
        final String avatar = "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId().toString();

        final int random = new Random().nextInt(joinMessages.length); // Get random number
        final EmbedBuilder join = new EmbedBuilder()
                .setColor(0x3FFE3F) // Lime colour
                .setAuthor(joinMessages[random].replace("{user}", userName), null, avatar);
        final TextChannel chat = this.guild.getTextChannelById(this.config.getLong("discord.channelId")); // Get discord chat channel
        chat.sendMessageEmbeds(join.build()).queue(); // Send message
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        final String userName = new PlainComponentSerializer().serialize(event.getPlayer().displayName()); // Get username
        final String avatar = "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId().toString();

        final int random = new Random().nextInt(leaveMessages.length); // Get random number
        final EmbedBuilder leave = new EmbedBuilder()
                .setColor(0xFE3F3F) // Light red colour
                .setAuthor(leaveMessages[random].replace("{user}", userName), null, avatar);
        final TextChannel chat = this.guild.getTextChannelById(this.config.getLong("discord.channelId")); // Get discord chat channel
        chat.sendMessageEmbeds(leave.build()).queue(); // Send message
    }

}
