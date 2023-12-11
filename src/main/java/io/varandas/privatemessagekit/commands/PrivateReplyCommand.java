package io.varandas.privatemessagekit.commands;

import io.varandas.privatemessagekit.Main;
import io.varandas.privatemessagekit.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Command executor for replying to the most recent private message.
 */
public class PrivateReplyCommand implements CommandExecutor {

    private final Main main;

    private static final String USAGE_MESSAGE = "§cUsage: /%s <message>";
    private static final String PLAYER_NOT_FOUND = "§cPlayer not found.";
    private static final String NO_RECIPIENT = "§cYou have no one to reply to.";

    public PrivateReplyCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage(String.format(USAGE_MESSAGE, command.getName()));
            return true;
        }

        UUID lastMessageSenderUUID = main.getRecentMessages().get(player.getUniqueId());
        Optional<Player> recipient = Optional.ofNullable(
                lastMessageSenderUUID != null ? player.getServer().getPlayer(lastMessageSenderUUID) : null
        );

        if (!recipient.isPresent()) {
            player.sendMessage(NO_RECIPIENT);
            return true;
        }

        String message = String.join(" ", args);
        MessageUtils.sendFormattedMessage(player, recipient.get(), message);

        return true;
    }
}
