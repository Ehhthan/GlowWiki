package com.ehhthan.glowwiki.api;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.wiki.PlayerPage;
import com.ehhthan.glowwiki.api.template.Template;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class GlowAuditor {
    private final GlowWiki plugin;

    public GlowAuditor(GlowWiki plugin) {
        this.plugin = plugin;
    }

    public void runPlayerAudit() {
        Template template = plugin.getTemplates().getTemplate("player-page");
        if (template != null && template.isEnabled()) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                new PlayerPage(player).create(plugin.getWikiAPI(), template);
            }
        }
    }
}
