package com.ehhthan.glowwiki.api.atlas.provider;

import com.ehhthan.glowwiki.api.info.PlayerInfo;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public interface AtlasProvider {
    String provide(OfflinePlayer offlinePlayer);

    static AtlasProvider get(ConfigurationSection section) {
        if (section.isString("info")) {
            return new InfoProvider(PlayerInfo.get(section.getString("info")));
        } else if (section.isString("placeholder")) {
            return new PlaceholderProvider(section.getString("placeholder"));
        }
        throw new IllegalArgumentException("No provider specified.");
    }
}
