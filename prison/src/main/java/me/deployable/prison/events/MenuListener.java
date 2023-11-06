package me.deployable.prison.events;

import me.deployable.prison.Prison;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {
    private final Prison plugin;

    public MenuListener(Prison plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == null || event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();
        String menuTitle = event.getView().getTitle();

        if (menuTitle.equals(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Name-GUI", "Rankup")))) {
            event.setCancelled(true);
        }

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        int slot = event.getRawSlot();
        String slotKey = "slot-" + (slot + 1);

        if (plugin.getConfig().contains("menu-slots." + slotKey)) {
            String command = plugin.getConfig().getString("menu-slots." + slotKey + ".command");
            String permission = plugin.getConfig().getString("menu-slots." + slotKey + ".permission");

            if (command != null && !command.isEmpty() && permission != null && !permission.isEmpty()) {
                if (player.hasPermission(permission)) {
                    command = command.replace("%player%", player.getName());
                    player.getServer().dispatchCommand(player.getServer().getConsoleSender(), command);
                } else {
                    player.sendMessage("You don't have permission to use this command.");
                }
            }
        }
    }
}