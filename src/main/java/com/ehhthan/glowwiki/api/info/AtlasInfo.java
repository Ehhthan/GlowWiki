package com.ehhthan.glowwiki.api.info;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.atlas.GlowAtlas;
import com.ehhthan.glowwiki.api.wiki.template.WikiTemplate;
import org.bukkit.OfflinePlayer;

public class AtlasInfo implements GlowInfo {
    private final String atlasId;

    public AtlasInfo(String atlasId) {
        this.atlasId = atlasId;
    }

    @Override
    public String build(OfflinePlayer player) {
        GlowAtlas atlas = GlowWiki.getInstance().getAtlases().getAtlas(atlasId);

        if (atlas != null)
            return atlas.getValue(player);
        else
            return "";
    }
}
