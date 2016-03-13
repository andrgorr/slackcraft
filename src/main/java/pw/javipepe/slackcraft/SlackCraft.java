package pw.javipepe.slackcraft;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pw.javipepe.slackcraft.commands.PrivateCmd;
import pw.javipepe.slackcraft.commands.PublicMessageCmd;
import pw.javipepe.slackcraft.commands.SetSlackCmd;
import pw.javipepe.slackcraft.listener.MentionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Javi
 */
public class SlackCraft extends JavaPlugin {

    @Getter public static Map<UUID, String> connections = new HashMap<>();
    @Getter public static Map<UUID, String> usernames = new HashMap<>();
    @Getter public static Map<UUID, ArrayList<String>> channelsListened = new HashMap<>();

    /**
     * Plugin desc.
     */

    private CommandsManager<CommandSender> commands;
    public static String PLUGIN_NAME = "SlackCraft";


    public void onEnable() {
        commands();
        listeners();
    }

    /**
     * listeners() registers the listeners
     */
    private void listeners() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new MentionListener(), this);
    }

    /**
     * commands() registers the commands
     */

    private void commands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);

        cmdRegister.register(PublicMessageCmd.class);
        cmdRegister.register(SetSlackCmd.class);
        cmdRegister.register(PrivateCmd.class);
    }

    /**
     * sq command framework core
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform that command.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, text received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "There was an internal error while performing this command.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }
}
