package me.deployable.prison.commands;

import me.deployable.prison.Prison;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;


public class rankup implements CommandExecutor {
    private final Prison plugin;

    public rankup(Prison plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            String menuTitle = plugin.getConfig().getString("Name-GUI", "Rankup");
            menuTitle = ChatColor.translateAlternateColorCodes('&', menuTitle);
            Inventory menu = player.getServer().createInventory(null, 18, menuTitle);

            for (int i = 1; i <= 18; i++) {
                String slotKey = "slot-" + i;
                if (plugin.getConfig().contains("menu-slots." + slotKey)) {
                    String itemName = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("menu-slots." + slotKey + ".name"));
                    Material material = Material.getMaterial(plugin.getConfig().getString("menu-slots." + slotKey + ".item", "DIAMOND"));

                    ItemStack item = new ItemStack(material);
                    item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, -1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(itemName);
                    List<String> lore = meta.getLore();
                    if (lore != null) {
                        lore.remove("Sharpness I");
                        meta.setLore(lore);
                    }

                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(meta);

                    menu.setItem(i - 1, item);
                }
            }

            player.openInventory(menu);
        }
        return true;
    }
}