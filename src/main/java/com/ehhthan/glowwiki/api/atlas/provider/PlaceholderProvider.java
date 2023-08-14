package com.ehhthan.glowwiki.api.atlas.provider;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderProvider implements AtlasProvider {
    private final String placeholder;

    public PlaceholderProvider(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String provide(OfflinePlayer offlinePlayer) {
        if (offlinePlayer instanceof Player player)
            return online(player);
        else {
            return PlaceholderAPI.setPlaceholders(offlinePlayer, placeholder);
        }
    }

    private String online(Player player) {
        return PlaceholderAPI.setPlaceholders(player, placeholder);
    }
}
