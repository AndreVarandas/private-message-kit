package io.varandas.privatemessagekit;

import io.varandas.privatemessagekit.commands.PrivateMessageCommand;
import io.varandas.privatemessagekit.commands.PrivateReplyCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class Main extends JavaPlugin implements Listener {

    private ConcurrentHashMap<UUID, UUID> recentMessages;

    @Override
    public void onEnable() {
        getLogger().info("PrivateMessageKit has been enabled!");
        recentMessages = new ConcurrentHashMap<>();
        registerCommands();
        getServer().getPluginManager().registerEvents(this, this); // Register event listener
    }

    @Override
    public void onDisable() {
        getLogger().info("PrivateMessageKit has been disabled!");
        recentMessages.clear();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        recentMessages.remove(event.getPlayer().getUniqueId());
    }

    private void registerCommands() {
        Map<String, CommandExecutor> commands = Map.of(
                "message", new PrivateMessageCommand(this),
                "reply", new PrivateReplyCommand(this)
        );

        commands.forEach(this::registerCommand);
    }

    private void registerCommand(String commandName, CommandExecutor commandExecutor) {
        Optional<PluginCommand> command = Optional.ofNullable(getCommand(commandName));
        if (command.isPresent()) {
            command.get().setExecutor(commandExecutor);
        } else {
            getLogger().warning("Command '" + commandName + "' not found in plugin.yml");
        }
    }

    public ConcurrentHashMap<UUID, UUID> getRecentMessages() {
        return recentMessages;
    }
}
