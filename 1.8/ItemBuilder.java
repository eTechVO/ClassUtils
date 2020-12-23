package fr.xmascraft.tools;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {
    private final ItemStack item; // RENDERED ITEM
    // CONSTRUCTORS
    public ItemBuilder(ItemStack item) {
        this.item = item;
    }
    public ItemBuilder(Material material) {
        this.item = new ItemStack(material, 1, (short) 0);
    }
    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount, (short) 0);
    }
    public ItemBuilder(Material material, int amount, int data) {
        this.item = new ItemStack(material, amount, (short) data);
    }

    // GLOBAL
    public ItemBuilder applyMeta(ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setName(String name) {
        ItemMeta meta = meta();
        meta.setDisplayName(name.replace("&", "§"));
        return applyMeta(meta);
    }
    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }
    public ItemBuilder setLore(List<String> lore) {
        List<String> ls = new ArrayList<>();
        lore.forEach(l -> ls.add(l.replace("&", "§")));

        ItemMeta meta = meta();
        meta.setLore(ls);
        return applyMeta(meta);
    }
    public ItemBuilder addEnchantment(Enchantment enchant, int level) {
        ItemMeta meta = meta();
        meta.addEnchant(enchant, level, true);
        return applyMeta(meta);
    }
    public ItemBuilder addUnsafeEnchantment(Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return this;
    }
    public ItemBuilder removeEnchantment(Enchantment enchant) {
        ItemMeta meta = meta();
        meta.removeEnchant(enchant);
        return applyMeta(meta);
    }
    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = meta();
        meta.addItemFlags(flags);
        return applyMeta(meta);
    }
    public ItemBuilder removeFlags(ItemFlag... flags) {
        ItemMeta meta = meta();
        meta.removeItemFlags(flags);
        return applyMeta(meta);
    }
    public ItemBuilder setUnbreakable() {
        item.setDurability(Short.MAX_VALUE);
        return this;
    }
    // SKULLS
    public ItemBuilder setSkullOwner(String owner) {
        SkullMeta meta = skullMeta();
        meta.setOwner(owner);
        return applyMeta(meta);
    }
    public ItemBuilder setSkullTexture(String value) {
        SkullMeta meta = skullMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        try {
            profile.getProperties().put("textures", new Property("textures", value));
            Field field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applyMeta(meta);
    }
    // ARMORS
    public ItemBuilder setArmorColor(Color color) {
        LeatherArmorMeta meta = leatherArmorMeta();
        meta.setColor(color);
        return applyMeta(meta);
    }

    // GETTERS
    private ItemMeta meta() {
        return item.getItemMeta();
    }
    private SkullMeta skullMeta() {
        return (SkullMeta) meta();
    }
    private LeatherArmorMeta leatherArmorMeta() {
        return (LeatherArmorMeta) meta();
    }
    // RENDER THE ITEM
    public ItemStack build() {
        return item;
    }
}
