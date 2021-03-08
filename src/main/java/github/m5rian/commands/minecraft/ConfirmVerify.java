package github.m5rian.commands.minecraft;

import github.m5rian.commands.discord.LinkAccount;
import github.m5rian.utils.Members;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConfirmVerify implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Command author isn't a player
        if (!(sender instanceof Player)) return true;
        final Player player = (Player) sender; // Get sender as Player Object

        // Command usage
        if (args.length != 1) {
            Bukkit.getConsoleSender().sendMessage("Usage: /verify <code>");
            player.sendMessage("Usage: /verify <code>"); // Send error message
            return true;
        }

        // Provided code is valid
        if (LinkAccount.verify.containsKey(args[0])) {
            final Long discordUserId = LinkAccount.verify.get(args[0]); // Get user id of discord member
            final JSONArray members = Members.get(); // Get current members
            // Search for member
            for (int i = 0; i < members.length(); i++) {
                final JSONObject member = members.getJSONObject(i); // Get current member
                // Member already exists
                if (member.getLong("discord") == discordUserId) {
                    player.sendMessage("You're discord account is already linked up. To unlink it use /unlink"); // Send error message
                    return true;
                }
            }

            // Create JSONObject for member
            final JSONObject member = new JSONObject()
                    .put("discord", discordUserId) // Add discord id
                    .put("minecraft", player.getUniqueId()); // Add minecraft UUID
            members.put(member); // Add member to file
            System.out.println(members);
            Members.update(members); // Update members file
            player.sendMessage("Verification successfully"); // Send success message
        }
        // Provided code is invalid
        else {
            player.sendMessage("Invalid verification code"); // Send error message
        }

        return true; // Return true for no errors
    }
}