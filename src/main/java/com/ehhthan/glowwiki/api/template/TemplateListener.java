package com.ehhthan.glowwiki.api.template;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.WikiAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

public abstract class TemplateListener<T extends Event> implements Listener {
    private final WikiAPI wikiAPI;
    private final Template template;

    public TemplateListener(GlowWiki plugin, String templateId) {
        this.wikiAPI = plugin.getWikiAPI();
        this.template = plugin.getTemplates().getTemplate(templateId);
        if (template != null && template.isEnabled()) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }
    }

    public WikiAPI getWikiAPI() {
        return wikiAPI;
    }

    public boolean isTemplate() {
        return template != null;
    }

    @Nullable
    public Template getTemplate() {
        return template;
    }

    @EventHandler
    public abstract void onEvent(T event);
}
