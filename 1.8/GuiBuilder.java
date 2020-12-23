package fr.xmascraft.tools.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {
    String title();
    int lines();
    void fill(Player player, Inventory inventory);
    void click(Player player, Inventory inventory, ItemStack item, int slot, ClickType type);
}
