package github.m5rian.listeners.minecraft;

import github.m5rian.utils.Config;
import github.m5rian.utils.Members;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.UUID;

public class MinecraftJoinColour implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final JSONArray members = Members.get(); // Get members file
        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i); // Get current member

            final Long discordId = member.getLong("discord"); // Get id of discord user
            final String uuidDatabase = member.getString("minecraft"); // Get UUID of saved member
            final UUID uuidGame = event.getPlayer().getUniqueId(); // Get UUID of player

            if (uuidGame.toString().equals(uuidDatabase)) {
                Config.getGuild.retrieveMemberById(discordId).queue(discordMember -> { // Retrieve member
                    System.out.println("iohgrtphiiopjyerpjnglp<ijngp<ip<w");
                    final Color colour = discordMember.getColor();
                    final int r = colour.getRed();
                    final int g = colour.getGreen();
                    final int b = colour.getBlue();
                    final String hex = String.format("#%02x%02x%02x", r, g, b); // Get hex of it

                    TextComponent text = Component.text(event.getPlayer().getName(), TextColor.color(r, g, b));
                    event.getPlayer().playerListName(text);
                });
                return;
            }

        }

    }
}
