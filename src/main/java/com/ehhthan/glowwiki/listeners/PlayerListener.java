package com.ehhthan.glowwiki.listeners;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.event.WikiEvent;
import com.ehhthan.glowwiki.api.event.WikiEventManager;
import com.ehhthan.glowwiki.api.event.action.EventAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerListener implements Listener {
    private final Map<WikiEvent.Type, List<WikiEvent>> events = new HashMap<>();
    private final GlowWiki plugin;

    public PlayerListener(GlowWiki plugin) {
        this.plugin = plugin;
        reload(plugin.getEvents());
    }

    public void reload(WikiEventManager eventManager) {
        events.clear();
        for (WikiEvent.Type type : WikiEvent.Type.values()) {
            events.put(type, eventManager.fromType(type));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        run(e.getPlayer(), WikiEvent.Type.ON_JOIN);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        run(e.getPlayer(), WikiEvent.Type.ON_QUIT);
    }

    private void run(Player player, WikiEvent.Type type) {
        for (WikiEvent event : events.get(type)) {
            for (EventAction action : event.getActions()) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    try {
                        action.run(plugin.getWikiAPI(), player);
                    } catch (SecurityException e) {
                        GlowWiki.getInstance().getUser().reload();
                    }
                });

            }
        }
    }
}
