package pw.javipepe.slackcraft.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.javipepe.slackcraft.SlackCraft;
import pw.javipepe.slackcraft.slack.Slack;

/**
 * @author Javi
 */
public class PrivateCmd {

    @Command(aliases = {"slackpm", "pm"}, usage = "/slackpm <user> <message>", desc = "Send a pm", min = 2)
    public static void slackpm(final CommandContext cmd, final CommandSender sender) throws Exception {
        if(!SlackCraft.getConnections().containsKey(((Player)sender).getUniqueId()))
            throw new CommandException("You are not connected to any slack team. Do so by using /slconnect <botkey>");

        Slack s = new Slack(SlackCraft.getConnections().get(((Player)sender).getUniqueId()));

        if (!s.userExists(cmd.getString(0)))
            throw new CommandException("User not found in team. As an example, @javipepe would become 'javipepe'");

        sender.sendMessage(ChatColor.GOLD + "Attempting to send message...");
        try {
            String message = cmd.getJoinedStrings(1);
            new Slack(SlackCraft.getConnections().get(((Player)sender).getUniqueId())).pm(cmd.getString(0), "Message whispered from Minecraft from *" + ((Player) sender).getName() + "*:\n _" + message + "_");
            sender.sendMessage(ChatColor.GREEN + "Message set to " + cmd.getString(0));
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An error occurred when posting message. Aborting.");
        }
    }

}
