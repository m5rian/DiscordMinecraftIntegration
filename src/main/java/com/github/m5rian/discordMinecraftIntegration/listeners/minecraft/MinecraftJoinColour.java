package com.github.m5rian.discordMinecraftIntegration.listeners.minecraft;

import com.github.m5rian.discordMinecraftIntegration.DiscordBridge;
import com.github.m5rian.discordMinecraftIntegration.utils.Config;
import com.github.m5rian.discordMinecraftIntegration.utils.Members;
import net.dv8tion.jda.api.entities.Guild;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.UUID;

public class MinecraftJoinColour implements Listener {
    private final Config config;
    private final Guild guild;

    public MinecraftJoinColour(DiscordBridge discordBridge) {
        this.config = discordBridge.getConfiguration();
        this.guild = discordBridge.getJda().getGuildById(this.config.getLong("discord.guildId"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final JSONArray members = Members.get(); // Get members file
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i); // Get current member

            final long discordId = member.getLong("discord"); // Get id of discord user
            final String uuidDatabase = member.getString("minecraft"); // Get UUID of saved member
            final UUID uuidGame = event.getPlayer().getUniqueId(); // Get UUID of player

            if (uuidGame.toString().equals(uuidDatabase)) {
                this.guild.retrieveMemberById(discordId).queue(discordMember -> { // Retrieve member
                    final Color colour = discordMember.getColor();
                    final int r = colour.getRed();
                    final int g = colour.getGreen();
                    final int b = colour.getBlue();

                    TextComponent text = Component.text(event.getPlayer().getName(), TextColor.color(r, g, b));
                    event.getPlayer().playerListName(text);
                });
                return;
            }

        }

    }
}
