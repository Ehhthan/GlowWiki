package com.ehhthan.glowwiki.listeners;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.wiki.PlayerPage;
import com.ehhthan.glowwiki.api.template.TemplateListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerFirstJoinEvent extends TemplateListener<PlayerJoinEvent> {
    public PlayerFirstJoinEvent(GlowWiki plugin, String templateId) {
        super(plugin, templateId);
    }

    @Override
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerPage page = new PlayerPage(player);
        if (!player.hasPlayedBefore())
            page.create(getWikiAPI(), getTemplate());
    }
}
