package com.ehhthan.glowwiki.api.atlas.provider;

import com.ehhthan.glowwiki.api.info.GlowInfo;
import org.bukkit.OfflinePlayer;

public class InfoProvider implements AtlasProvider {
    private final GlowInfo info;

    public InfoProvider(GlowInfo info) {
        this.info = info;
    }

    @Override
    public String provide(OfflinePlayer offlinePlayer) {
        return info.build(offlinePlayer);
    }
}
