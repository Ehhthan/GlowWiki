package com.ehhthan.glowwiki.listeners;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.template.TemplateListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerHeadCreationEvent extends TemplateListener<PlayerDeathEvent> {
    public PlayerHeadCreationEvent(GlowWiki plugin, String templateId) {
        super(plugin, templateId);
    }

    @Override
    @EventHandler
    public void onEvent(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = player.getKiller();

        // Check that killer was player.
        if (killer != null) {

        }
    }
}
