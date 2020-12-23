package fr.xmascraft.managers;

import fr.xmascraft.tools.utils.Hologram;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HologramManager {
    private final Plugin plugin;
    private final Set<Hologram> holograms = new HashSet<>();

    public HologramManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void create(String name, Location location) {
        Hologram h = new Hologram(plugin, name, location);
        h.spawn();
        holograms.add(h);
    }
    public void load(String name, Location location, List<String> lines) {
        Hologram h = new Hologram(plugin, name, location, lines);
        h.spawn();
        holograms.add(h);
    }
    public void delete(String name) {
        Hologram h = getHologram(name);
        h.destroy();
        holograms.remove(h);
    }

    public boolean exists(String name) {
        return getHologram(name) != null;
    }

    public Hologram getHologram(String name) {
        return holograms.stream().filter(h -> h.getName().equals(name)).findFirst().orElse(null);
    }
    public Set<Hologram> getHolograms() {
        return holograms;
    }
}
