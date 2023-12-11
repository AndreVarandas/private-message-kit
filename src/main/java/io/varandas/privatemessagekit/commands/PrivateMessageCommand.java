package io.varandas.privatemessagekit.commands;

import io.varandas.privatemessagekit.Main;
import io.varandas.privatemessagekit.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * Command executor for sending private messages.
 */
public class PrivateMessageCommand implements CommandExecutor {

    private final Main main;

    private static final String PLAYER_NOT_FOUND = "§cPlayer not found.";
    private static final String USAGE = "§cUsage: /%s <player> <message>";
    private static final String PLAYER_ONLY_COMMAND = "§cYou must be a player to use this command.";

    public PrivateMessageCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(PLAYER_ONLY_COMMAND);
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(String.format(USAGE, command.getName()));
            return true;
        }

        Player player = (Player) sender;
        Optional<Player> target = Optional.ofNullable(player.getServer().getPlayer(args[0]));

        if (!target.isPresent()) {
            player.sendMessage(PLAYER_NOT_FOUND);
            return true;
        }

        String message = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        MessageUtils.sendFormattedMessage(player, target.get(), message);

        // Store the last player that sent a message to the target
        main.getRecentMessages().put(target.get().getUniqueId(), player.getUniqueId());

        return true;
    }
}
