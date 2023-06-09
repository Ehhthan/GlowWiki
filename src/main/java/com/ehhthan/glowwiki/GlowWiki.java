package com.ehhthan.glowwiki;

import co.aikar.commands.PaperCommandManager;
import com.ehhthan.glowwiki.api.GlowAuditor;
import com.ehhthan.glowwiki.api.event.WikiEventManager;
import com.ehhthan.glowwiki.api.wiki.GlowUser;
import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import com.ehhthan.glowwiki.api.wiki.template.WikiTemplateManager;
import com.ehhthan.glowwiki.command.GlowWikiCommand;
import com.ehhthan.glowwiki.file.DirectoryCopyFileVisitor;
import com.ehhthan.glowwiki.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;

public final class GlowWiki extends JavaPlugin {
    private static GlowWiki INSTANCE;


    private WikiTemplateManager templates;
    private WikiEventManager events;
    private GlowAuditor auditor;
    private WikiAPI wikiAPI;

    @Override
    public void onEnable() {
        INSTANCE = this;

        // Clear cache before restart.
        File cache = new File(getDataFolder(), "cache");
        if (cache.exists())
            cache.delete();

        // Default file generation.
        saveDefaultConfig();
        try {
            DirectoryCopyFileVisitor.INSTANCE.copy("default", getDataFolder().toPath());
        } catch (URISyntaxException | IOException e) {
            getLogger().log(Level.SEVERE, "Could not generate default files.");
            e.printStackTrace();
        }

        this.templates = new WikiTemplateManager(this);
        this.events = new WikiEventManager(this);
        this.auditor = new GlowAuditor(this);

        try {
            ConfigurationSection section = getConfig().getConfigurationSection("wiki");
            if (section != null) {
                wikiAPI = new GlowUser(section).login();
            }
        } catch (IOException | URISyntaxException | LoginException e) {
            e.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        registerCommands();
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new GlowWikiCommand(this));
    }


    public static GlowWiki getInstance() {
        return INSTANCE;
    }

    public WikiTemplateManager getTemplates() {
        return templates;
    }

    public WikiEventManager getEvents() {
        return events;
    }

    public GlowAuditor getAuditor() {
        return auditor;
    }

    public WikiAPI getWikiAPI() {
        return wikiAPI;
    }
}
