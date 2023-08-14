package com.ehhthan.glowwiki.api.atlas;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.event.WikiEvent;
import com.ehhthan.glowwiki.api.util.FormatUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GlowAtlasManager {
    private final Map<String, GlowAtlas> atlases = new HashMap<>();

    public GlowAtlasManager(@NotNull GlowWiki plugin) {
        reload(plugin);
    }

    public void reload(@NotNull GlowWiki plugin) {
        atlases.clear();

        File[] files = new File(plugin.getDataFolder(), "atlas").listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File configFile : files) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                for (String key : config.getKeys(false)) {
                    ConfigurationSection section = config.getConfigurationSection(key);
                    if (section != null) {
                        GlowAtlas atlas = new GlowAtlas(section);
                        atlases.put(atlas.getId(), atlas);
                    }
                }
            }
        }
    }

    public boolean hasAtlas(@NotNull String id) {
        return atlases.containsKey(FormatUtil.id(id));
    }

    @Nullable
    public GlowAtlas getAtlas(@NotNull String id) {
        return atlases.get(FormatUtil.id(id));
    }
}
