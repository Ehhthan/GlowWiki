package com.ehhthan.glowwiki.api.atlas;

import com.ehhthan.glowwiki.api.atlas.option.AtlasOption;
import com.ehhthan.glowwiki.api.atlas.provider.AtlasProvider;
import com.ehhthan.glowwiki.api.util.FormatUtil;
import com.google.common.base.Preconditions;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class GlowAtlas {
    private final String id;
    private final AtlasProvider provider;
    private final AtlasOption fallback;

    private final Map<String, AtlasOption> options = new LinkedHashMap<>();

    public GlowAtlas(ConfigurationSection section) {
        this.id = FormatUtil.id(section.getName());
        this.provider = AtlasProvider.get(section);

        this.fallback = AtlasOption.get(section.getConfigurationSection("default"));

        Preconditions.checkArgument(section.isConfigurationSection("options"), "No options exist.");

        for (String key : section.getConfigurationSection("options").getKeys(false)) {
            if (section.isConfigurationSection("options." + key)) {
                AtlasOption option = AtlasOption.get(section.getConfigurationSection("options." + key));
                options.put(option.getKey(), option);
            }
        }

    }

    public String getId() {
        return id;
    }

    public String getValue(OfflinePlayer player) {
        return options.getOrDefault(provider.provide(player), fallback).getValue(player);
    }
}
