package pw.javipepe.slackcraft.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.javipepe.slackcraft.SlackCraft;
import pw.javipepe.slackcraft.slack.Slack;

/**
 * @author Javi
 */
public class PublicMessageCmd {

    @Command(aliases = {"slack", "sl"}, usage = "/slack <channel> <message>", desc = "Send a slack message", min = 2)
    @CommandPermissions("slackcraft.command.send")
    public static void slack(final CommandContext cmd, final CommandSender sender) throws Exception {

        if(!SlackCraft.getConnections().containsKey(((Player)sender).getUniqueId()))
            throw new CommandException("You are not connected to any slack team. Do so by using /slconnect <botkey>");

        Slack s = new Slack(SlackCraft.getConnections().get(((Player)sender).getUniqueId()));

        if(!s.channelExists(cmd.getString(0))) {
            sender.sendMessage("The channel specified (" + cmd.getString(0) + ") doesn't exist. Available channels:");
            throw new CommandException(s.listOfAccessibleChannels());
        }

        sender.sendMessage(ChatColor.GOLD + "Attempting to send message...");
        try {
            String message = cmd.getJoinedStrings(1);
            new Slack(SlackCraft.getConnections().get(((Player)sender).getUniqueId())).sendMessageToChannelAndQuit(cmd.getString(0), "Message sent from Minecraft from *" + ((Player) sender).getName() + "*:\n _" + message + "_");
            sender.sendMessage(ChatColor.GREEN + "Message posted into " + cmd.getString(0));
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An error occurred when posting message. Aborting.");
        }

    }

    @Command(aliases = {"slacklisten", "sll"}, usage = "/slacklisten <channel>", desc = "Listen for all the messages in a slack channel", min = 1, max = 1)
    public static void listen(final CommandContext cmd, final CommandSender sender) throws Exception {

        if(!SlackCraft.getConnections().containsKey(((Player)sender).getUniqueId()))
            throw new CommandException("You are not connected to any slack team. Do so by using /slconnect <botkey>");

        Slack s = new Slack(SlackCraft.getConnections().get(((Player)sender).getUniqueId()));

        String channel = cmd.getString(0);
        if(SlackCraft.getChannelsListened().containsKey(((Player)sender).getUniqueId())) {
            if (SlackCraft.getChannelsListened().get(((Player) sender).getUniqueId()).contains(channel))
                throw new CommandException("You are already listening to " + channel);
        }
        if(!s.channelExists(channel)) {
            sender.sendMessage("The channel specified (" + cmd.getString(0) + ") doesn't exist. Available channels:");
            throw new CommandException(s.listOfAccessibleChannels());
        }

        SlackCraft.getChannelsListened().get(((Player)sender).getUniqueId()).add(channel);
        s.subscribePlayerToChannel(channel);
        sender.sendMessage(ChatColor.GREEN + "Successfully subscribed to channel " + channel);

    }

    @Command(aliases = {"slackunlisten", "slul"}, usage = "/slackunlisten <channel>", desc = "Unlisten for all the messages in a slack channel", min = 1, max = 1)
    public static void unlisten(final CommandContext cmd, final CommandSender sender) throws Exception {

        if(!SlackCraft.getConnections().containsKey(((Player)sender).getUniqueId()))
            throw new CommandException("You are not connected to any slack team. Do so by using /slconnect <botkey>");

        Slack s = new Slack(SlackCraft.getConnections().get(((Player)sender).getUniqueId()));

        String channel = cmd.getString(0);

        if (!SlackCraft.getChannelsListened().get(((Player) sender).getUniqueId()).contains(channel))
            throw new CommandException("You not listening to " + channel);

        if(!s.channelExists(channel)) {
            sender.sendMessage("The channel specified (" + cmd.getString(0) + ") doesn't exist. Available channels:");
            throw new CommandException(s.listOfAccessibleChannels());
        }

        SlackCraft.getChannelsListened().get(((Player)sender).getUniqueId()).remove(channel);
        sender.sendMessage(ChatColor.GREEN + "Successfully unsubscribed from channel " + channel);

    }


}
