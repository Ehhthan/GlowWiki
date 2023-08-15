package com.ehhthan.glowwiki.api.wiki.template;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.util.FormatUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WikiTemplateManager {
    private final Map<String, WikiTemplate> templates = new HashMap<>();

    public WikiTemplateManager(@NotNull GlowWiki plugin) {
        reload(plugin);
    }

    public void reload(@NotNull GlowWiki plugin) {
        templates.clear();

        File[] files = new File(plugin.getDataFolder(), "templates").listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File configFile : files) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                for (String key : config.getKeys(false)) {
                    ConfigurationSection section = config.getConfigurationSection(key);
                    if (section != null) {
                        WikiTemplate template = new WikiTemplate(section);
                        templates.put(template.getId(), template);
                    }
                }
            }
        }
    }

    public boolean hasTemplate(@NotNull String id) {
        return templates.containsKey(FormatUtil.id(id));
    }

    @Nullable
    public WikiTemplate getTemplate(@NotNull String id) {
        return templates.get(FormatUtil.id(id));
    }

    public Collection<WikiTemplate> values() {
        return templates.values();
    }
}

