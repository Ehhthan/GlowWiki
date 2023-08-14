package com.ehhthan.glowwiki.api.atlas.option;

import org.bukkit.OfflinePlayer;

public class StringOption implements AtlasOption {
    private final String key, value;

    public StringOption(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    public String getValue(OfflinePlayer offlinePlayer) {
        return value;
    }
}
