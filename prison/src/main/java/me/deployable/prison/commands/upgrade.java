package me.deployable.prison.commands;

import me.deployable.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class upgrade implements CommandExecutor {
    private final Prison plugin;

    public upgrade(Prison plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        int currentSlot = getCurrentSlot(player);

        if (currentSlot < 1 || currentSlot >= 18) {
            player.sendMessage("You are already at the highest rank.");
            return true;
        }

        int nextSlot = currentSlot + 1;
        int cost = plugin.getConfig().getInt("menu-slots.slot-" + nextSlot + ".cost");
        String message = plugin.getConfig().getString("menu-slots.slot-" + nextSlot + ".message");
        String group = plugin.getConfig().getString("menu-slots.slot-" + nextSlot + ".group");

        if (cost <= 0 || takeEconomy(player, cost, group)) {

            String nextPermission = "prison.upgrade.slot-" + nextSlot;
            player.addAttachment(plugin, nextPermission, true);

            Bukkit.getServer().getConsoleSender().sendMessage(message);

            return true;
        } else {
            player.sendMessage("You don't have enough money to upgrade.");
        }

        return true;
    }

    private int getCurrentSlot(Player player) {
        for (int i = 18; i >= 1; i--) {
            if (player.hasPermission("prison.upgrade.slot-" + i)) {
                System.out.println("Player has permission for slot " + i);
                return i;
            }
        }

        System.out.println("Player has no permissions for any slot.");
        return 0;
    }

    private boolean takeEconomy(Player player, int cost, String group) {
        String playerName = player.getName();
        String command = "eco take " + playerName + " " + cost + " " + group;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

        return true;
    }
}
