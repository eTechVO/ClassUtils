package fr.xmascraft.managers;

import fr.xmascraft.tools.utils.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GuiManager implements Listener {
    private Map<Class<? extends GuiBuilder>, GuiBuilder> menus = new HashMap<>();

    @EventHandler
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();
        ClickType click = event.getClick();

        if (inventory == null || item == null) return;

        menus.values().stream()
                .filter(menu -> inventory.getTitle().equals(menu.title().replace("&", "§")))
                .forEach(menu -> {
            event.setCancelled(true);
            menu.click(player, inventory, item, slot, click);
        });
    }

    public void register(GuiBuilder gui) {
        menus.put(gui.getClass(), gui);
    }
    public void open(Player player, Class<? extends GuiBuilder> gClass) {
        GuiBuilder gui = menus.get(gClass);
        Inventory inventory = Bukkit.createInventory(null, gui.lines() * 9, gui.title().replace("&", "§"));
        gui.fill(player, inventory);
        player.openInventory(inventory);
    }
}
