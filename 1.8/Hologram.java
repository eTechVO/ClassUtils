package fr.xmascraft.tools.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public class Hologram {
    private final Plugin plugin;
    private final String name;
    private List<String> lines;
    private Location location;

    public Hologram(Plugin plugin, String name, Location location) {
        this.plugin = plugin;
        this.name = name;
        this.location = location.getBlock().getLocation().add(.5,-1,.5);
        this.lines = new ArrayList<>();
        this.lines.add("&fManage lines this &bline&f.");
    }
    public Hologram(Plugin plugin, String name, Location location, String... lines) {
        this.plugin = plugin;
        this.name = name;
        this.location = location.getBlock().getLocation().add(.5,-1,.5);
        this.lines = Arrays.asList(lines);
    }
    public Hologram(Plugin plugin, String name, Location location, List<String> lines) {
        this.plugin = plugin;
        this.name = name;
        this.location = location.getBlock().getLocation().add(.5,-1,.5);
        this.lines = lines;
    }

    public void spawn() {
        Location start = this.location.clone().add(0, (lines.size() - 1) * 0.2, 0);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Location point = start.clone().add(0, i * -0.2, 0);

            ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(point, EntityType.ARMOR_STAND);
            stand.setGravity(false);
            stand.setVisible(false);
            stand.setBasePlate(false);
            stand.setCustomNameVisible(true);
            stand.setCustomName(line.replace("&", "§"));
            stand.setMetadata("hologram", new FixedMetadataValue(plugin, 1));
            stand.setMetadata("name", new FixedMetadataValue(plugin, name));
            stand.setMetadata("line", new FixedMetadataValue(plugin, i+1));
        }
    }
    public void move(Location location) {
        this.location = location.getBlock().getLocation().add(.5,-1,.5);
        reload();
    }
    public void destroy() {
        location.getWorld().getEntities().stream()
                .filter(entity -> entity.hasMetadata("hologram")
                        && entity.hasMetadata("name")
                        && Objects.equals(entity.getMetadata("name").get(0), name))
                .forEach(Entity::remove);
    }
    public void reload() {
        destroy();
        spawn();
    }
    public void addLine(String text) {
        lines.add(text);
        reload();
    }
    public void setLine(int index, String text) {
        if (index == 0) index = 1;
        if (index >= this.lines.size()) index = this.lines.size();

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < this.lines.size(); i++) {
            String line = this.lines.get(i);
            if (i == index-1) line = text;
            lines.add(line.replace("&", "§"));
        }
        this.lines = lines;
        reload();
    }
    public void removeLine(int index) {
        lines.remove(index);
        reload();
    }

    public String getName() {
        return name;
    }
    public List<String> getLines() {
        return lines;
    }
    public Location getLocation() {
        return location;
    }
}
