package pw.javipepe.slackcraft.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pw.javipepe.slackcraft.SlackCraft;

import java.util.ArrayList;

/**
 * @author Javi
 */
public class MentionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!SlackCraft.getUsernames().containsKey(event.getPlayer().getUniqueId())) {
            SlackCraft.getUsernames().put(event.getPlayer().getUniqueId(), event.getPlayer().getName().toLowerCase());
        }

        Player p = event.getPlayer();

        p.sendMessage(ChatColor.RED + "=-=-=-=-=-=-=-=-=-=-=-= " + ChatColor.DARK_RED + ChatColor.BOLD + "SlackCraft " + ChatColor.RESET + ChatColor.RED + "=-=-=-=-=-=-=-=-=-=-=-=");

        StringBuilder s = new StringBuilder();
        s.append(ChatColor.GOLD + "" + ChatColor.BOLD + "TEAM: ");
        if (SlackCraft.getConnections().containsKey(p.getUniqueId())) {
            s.append(ChatColor.GREEN + "You are connected to an Slack team, Disconnect with /sld\n");
        } else {
            s.append(ChatColor.RED + "You are not connected to an Slack team. Connect with /slc <botkey>\n");
        }
        s.append(ChatColor.GOLD + "" + ChatColor.BOLD + "USERNAME: " + ChatColor.GRAY + "Your global username is " + ChatColor.RED + "" + ChatColor.UNDERLINE + "@" + SlackCraft.getUsernames().get(p.getUniqueId()) + ChatColor.RESET + ChatColor.GRAY + ". Change with /slu <username without @>");
        p.sendMessage(s.toString());
        p.sendMessage(ChatColor.AQUA + "                                  Made by Javipepe");
        p.sendMessage(ChatColor.RED + "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        SlackCraft.getChannelsListened().put(event.getPlayer().getUniqueId(), new ArrayList<String>());
    }
}
