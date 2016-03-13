package pw.javipepe.slackcraft.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pw.javipepe.slackcraft.SlackCraft;
import pw.javipepe.slackcraft.slack.Slack;

/**
 * @author Javi
 */
public class SetSlackCmd {

    @Command(aliases = {"slconnect", "slc"}, usage = "/slc <code>", desc = "Connect to a slack", min = 1, max = 1)
    public static void slconnect(final CommandContext cmd, final CommandSender sender) throws Exception {
        final SlackSession session = SlackSessionFactory.createWebSocketSlackSession(cmd.getString(0));
        sender.sendMessage(ChatColor.GOLD + "Attempting to connect...");
        session.connect();
        try {
            sender.sendMessage(ChatColor.GREEN + "Connected (bot: " + session.sessionPersona().getUserName() + ")");
            SlackCraft.getConnections().put(((Player)sender).getUniqueId(), cmd.getString(0));
            session.addMessagePostedListener(new SlackMessagePostedListener() {
                @Override
                public void onEvent(SlackMessagePosted event, SlackSession session) {
                    boolean sendnotif = false;
                    String message = event.getMessageContent();

                    if (event.getMessageContent().toLowerCase().contains(sender.getName().toLowerCase()) &&
                            !event.getMessageContent().toLowerCase().contains("Message sent from Minecraft from *" + sender.getName() + "*:"))
                        sendnotif = true;

                    if (!sendnotif) {
                        SlackUser s = session.findUserByUserName(SlackCraft.getUsernames().get(((Player)sender).getUniqueId()));
                        String id = s.getId();

                        if (event.getMessageContent().contains(id)) {
                            sendnotif = true;
                            message = event.getMessageContent().replace("<@" + id + ">", "@" + s.getUserName());
                        }
                    }

                    if(sendnotif) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[&6Slack Notification in #&c" + event.getChannel().getName() + "&4] &7From &7&l" + event.getSender().getUserName() + "&r&7: " + message));
                        ((Player) sender).playSound(((Player) sender).getLocation(), Sound.LEVEL_UP, 1.0F, 2.0F);
                    }
                }
            });
        } catch (Exception e){
            sender.sendMessage(ChatColor.RED + "Error connecting to slack team (is the ID alright?)");
        }
    }

    @Command(aliases = {"sldisconnect", "sld"}, usage = "/sld <code>", desc = "Disconnect to a slack", max = 0)
    public static void sldisconnect(final CommandContext cmd, final CommandSender sender) throws Exception {
        if(!SlackCraft.getConnections().containsKey(((Player)sender).getUniqueId()))
            throw new CommandException("You are not connected to any slack.");

        SlackCraft.getConnections().remove(((Player)sender).getUniqueId());
        if (SlackCraft.getChannelsListened().containsKey(((Player)sender).getUniqueId()))
            SlackCraft.getChannelsListened().remove(((Player)sender).getUniqueId());

        sender.sendMessage(ChatColor.DARK_RED + "Disconnected.");
    }

    @Command(aliases = {"slusername", "slu"}, usage = "/slu <global username>", desc = "Change your slack username to check for pings", min = 1, max = 1)
    public static void slusername(final CommandContext cmd, final CommandSender sender) throws Exception {
        if(SlackCraft.getUsernames().containsKey(((Player)sender).getUniqueId())) {
            String oldcache = SlackCraft.getUsernames().get(((Player)sender).getUniqueId());
            SlackCraft.getUsernames().remove(((Player)sender).getUniqueId());
            SlackCraft.getUsernames().put(((Player)sender).getUniqueId(), cmd.getString(0));
            sender.sendMessage(ChatColor.GREEN + "Global username setting changed from " + ChatColor.RED + oldcache + ChatColor.GREEN + " to " + ChatColor.DARK_GREEN + cmd.getString(0));
        }
    }
}
