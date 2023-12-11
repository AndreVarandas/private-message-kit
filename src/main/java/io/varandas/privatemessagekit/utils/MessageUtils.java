package io.varandas.privatemessagekit.utils;

import org.bukkit.entity.Player;

/**
 * Utility class for sending formatted private messages.
 */
public class MessageUtils {
    private static final String MESSAGE_FORMAT_SENDER = "§7[§bYou §7-> §b%s§7] §f%s";
    private static final String MESSAGE_FORMAT_RECIPIENT = "§7[§b%s §7-> §bYou§7] §f%s";

    /**
     * Sends a formatted private message to both the sender and recipient.
     *
     * @param sender    the player sending the message
     * @param recipient the player receiving the message
     * @param message   the message content
     */
    public static void sendFormattedMessage(Player sender, Player recipient, String message) {
        sender.sendMessage(String.format(MESSAGE_FORMAT_SENDER, recipient.getName(), message));
        recipient.sendMessage(String.format(MESSAGE_FORMAT_RECIPIENT, sender.getName(), message));
    }
}
