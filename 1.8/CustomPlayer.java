package fr.xmascraft.tools.utils;

import fr.xmascraft.tools.ClickableBuilder;
import fr.xmascraft.tools.ItemBuilder;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class CustomPlayer {
    private final Player owner;

    public CustomPlayer(Player owner) {
        this.owner = owner;
    }

    public void setGameMode(GameMode mode) {
        owner.setGameMode(mode);
    }
    public void sendMessage(String message) {
        owner.sendMessage(message.replace("&", "§"));
    }
    public void sendClickableMessage(ClickableBuilder message) {
        owner.spigot().sendMessage(message.build());
    }
    public void playSound(Sound sound) {
        owner.playSound(getLocation(), sound, 1, 1);
    }
    public void playEffect(Effect effect, @Nullable Object t) {
        owner.playEffect(getLocation(), effect, t);
    }
    public void launch(double multiplier, double y) {
        owner.setVelocity(getLocation().getDirection().multiply(multiplier).setY(y));
    }
    public void teleport(Object target) {
        if (target instanceof Player) owner.teleport((Player) target);
        else if (target instanceof Entity) owner.teleport((Entity) target);
        else if (target instanceof Location) owner.teleport((Location) target);
        else if (target instanceof World) owner.teleport(((World) target).getSpawnLocation());
    }
    public void sendTitle(String title, String subtitle) {
        sendTitle(title, subtitle, 20, 30, 20);
    }
    public void sendTitle(String title, String subtitle, int in, int duration, int out) {
        addTime(in, duration, out);
        sendPackets(
                new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title.replace("&", "§") + "\"}")),
                new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle.replace("&", "§") + "\"}"))
        );
    }
    public void sendActionBar(String text) {
        sendActionBar(text, 20, 30, 20);
    }
    public void sendActionBar(String text, int in, int duration, int out) {
        addTime(in, duration, out);
        sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text.replace("&", "§") + "\"}"), (byte)2));
    }
    private void addTime(int in, int duration, int out) {
        sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, in, duration, out));
    }
    public void sendPacket(Packet packet) {
        ((CraftPlayer) owner).getHandle().playerConnection.sendPacket(packet);
    }
    public void sendPackets(Packet... packets) {
        Arrays.asList(packets).forEach(this::sendPacket);
    }
    public void setMaxHealth(double value) {
        owner.setMaxHealth(value);
    }
    public void setHealth(double value) {
        owner.setHealth(value);
    }
    public void heal() {
        setHealth(getMaxHealth());
    }
    public void setFoodLevel(int value) {
        owner.setFoodLevel(value);
    }
    public void feed() {
        setFoodLevel(20);
    }
    public void addPotionEffect(PotionEffectType type) {
        addPotionEffect(type, 10, 1);
    }
    public void addPotionEffect(PotionEffectType type, int duration, int amplifier) {
        addPotionEffect(type, duration, amplifier, true, true);
    }
    public void addPotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        owner.addPotionEffect(new PotionEffect(type, duration, amplifier, ambient, particles));
    }
    public void removePotionEffect(PotionEffectType type) {
        owner.removePotionEffect(type);
    }
    public void removePotionEffects(PotionEffectType... types) {
        Arrays.asList(types).forEach(this::removePotionEffect);
    }
    public void strike() {
        getWorld().strikeLightning(getLocation());
    }
    public void clearInventory() {
        owner.getInventory().clear();
        owner.updateInventory();
    }
    public void clearArmor() {
        owner.getInventory().setHelmet(null);
        owner.getInventory().setChestplate(null);
        owner.getInventory().setLeggings(null);
        owner.getInventory().setBoots(null);
        owner.updateInventory();
    }
    public void clear() {
        clearInventory();
        clearArmor();
    }
    public void give(ItemBuilder item) {
        if (!hasSpace()) {
            getWorld().dropItem(getLocation(), item.build());
            return;
        }
        owner.getInventory().addItem(item.build());
        owner.updateInventory();
    }
    public void setItem(int slot, ItemBuilder item) {
        owner.getInventory().setItem(slot, item.build());
        owner.updateInventory();
    }

    public boolean hasSpace() {
        int available = 0;
        for (int i = 0; i < owner.getInventory().getContents().length; i ++)
            if (owner.getInventory().getContents()[i] != null && !owner.getInventory().getContents()[i].getType().equals(Material.AIR))
                available ++;
        return available > 0;
    }

    public World getWorld() {
        return owner.getWorld();
    }
    public Location getLocation() {
        return owner.getLocation();
    }
    public Block getBlock() {
        return getLocation().getBlock();
    }
    public Location getFloor() {
        return getLocation().clone().add(0, -1 ,0);
    }
    public Block getFloorBlock() {
        return getFloor().getBlock();
    }
    public double getMaxHealth() {
        return owner.getMaxHealth();
    }
    public double getHealth() {
        return owner.getHealth();
    }
    public double getHearts() {
        return getHealth() / 2;
    }
    public int getFoodLevel() {
        return owner.getFoodLevel();
    }
}
