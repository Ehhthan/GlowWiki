package com.ehhthan.glowwiki.api.atlas.option;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public interface AtlasOption {
    String getKey();

    String getValue(OfflinePlayer offlinePlayer);

    static AtlasOption get(ConfigurationSection section) {
        String key = section.getName();
        if (section.isString("atlas")) {
            return new NestedOption(key, section.getString("atlas"));
        } else if (section.isString("value")) {
            return new StringOption(key, section.getString("value"));
        }
        throw new IllegalArgumentException("No option specified.");
    }
}
