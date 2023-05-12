package com.ehhthan.glowwiki.api;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.event.WikiEvent;
import com.ehhthan.glowwiki.api.event.action.EventAction;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class GlowAuditor {
    private final GlowWiki plugin;

    public GlowAuditor(GlowWiki plugin) {
        this.plugin = plugin;
    }

    public void runPlayerAudit(String eventId) {
        WikiEvent playerEvent = plugin.getEvents().getEvent(eventId);
        if (playerEvent != null) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                run(player, playerEvent);
            }
        }
    }

    private void run(OfflinePlayer player, WikiEvent event) {
        for (EventAction action : event.getActions()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                action.run(plugin.getWikiAPI(), player);
            });
        }
    }
}
