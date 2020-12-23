package fr.xmascraft.managers;

import com.mojang.authlib.GameProfile;
import fr.xmascraft.tools.utils.CustomPlayer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import javax.annotation.Nonnull;
import java.util.UUID;

public class NPCManager {
    public void spawn(@Nonnull CustomPlayer player, @Nullable UUID uuid, @Nonnull String name) {
        Location location = player.getLocation();
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile profile = new GameProfile(uuid == null ? UUID.randomUUID() : uuid, name.replace("&", "§"));

        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        npc.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        player.sendPackets(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc), new PacketPlayOutNamedEntitySpawn(npc), new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
    }
    public void spawn(@Nonnull CustomPlayer player, @Nonnull Location location, @Nullable UUID uuid, @Nonnull String name) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile profile = new GameProfile(uuid == null ? UUID.randomUUID() : uuid, name.replace("&", "§"));

        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        npc.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        player.sendPackets(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc), new PacketPlayOutNamedEntitySpawn(npc), new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
    }
}