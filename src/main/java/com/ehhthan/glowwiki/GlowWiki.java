package com.ehhthan.glowwiki;

import co.aikar.commands.PaperCommandManager;
import com.ehhthan.glowwiki.api.WikiAPI;
import com.ehhthan.glowwiki.api.template.TemplateManager;
import com.ehhthan.glowwiki.command.GlowWikiCommand;
import com.ehhthan.glowwiki.file.DirectoryCopyFileVisitor;
import com.ehhthan.glowwiki.listeners.PlayerFirstJoinEvent;
import com.ehhthan.glowwiki.listeners.PlayerHeadCreationEvent;
import com.ehhthan.glowwiki.api.GlowAuditor;
import com.ehhthan.glowwiki.api.wiki.GlowUser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;

public final class GlowWiki extends JavaPlugin {
    private static GlowWiki INSTANCE;

    private TemplateManager templates;
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

        this.templates = new TemplateManager(this);
        this.auditor = new GlowAuditor(this);

        try {
            ConfigurationSection section = getConfig().getConfigurationSection("wiki");
            if (section != null) {
                wikiAPI = new GlowUser(section).login();
            }
        } catch (IOException | URISyntaxException | LoginException e) {
            e.printStackTrace();
        }
        new PlayerFirstJoinEvent(this, "player-page");
        new PlayerHeadCreationEvent(this, "head-creation");

        registerCommands();
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new GlowWikiCommand(this));
    }


    public static GlowWiki getInstance() {
        return INSTANCE;
    }

    public TemplateManager getTemplates() {
        return templates;
    }

    public GlowAuditor getAuditor() {
        return auditor;
    }

    public WikiAPI getWikiAPI() {
        return wikiAPI;
    }
}
