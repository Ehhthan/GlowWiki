package com.ehhthan.glowwiki.api.atlas.option;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.atlas.GlowAtlas;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class NestedOption implements AtlasOption {
    private final String key, atlasId;

    private GlowAtlas atlas;

    public NestedOption(String key, String atlasId) {
        this.key = key;
        this.atlasId = atlasId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue(@NotNull OfflinePlayer offlinePlayer) {
        if (atlas == null) {
            this.atlas = GlowWiki.getInstance().getAtlases().getAtlas(atlasId);
        }
        return atlas.getValue(offlinePlayer);
    }
}
