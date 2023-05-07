package com.ehhthan.glowwiki.api.template;

import com.ehhthan.glowwiki.GlowWiki;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TemplateManager {
    private final Map<String, Template> templates = new HashMap<>();

    public TemplateManager(@NotNull GlowWiki plugin) {
        reload(plugin);
    }

    public void reload(@NotNull GlowWiki plugin) {
        templates.clear();

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "templates.yml"));
        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section != null) {
                Template template = new Template(section);
                templates.put(template.getId(), template);
            }
        }
    }

    public boolean hasTemplate(@NotNull String id) {
        return templates.containsKey(Template.format(id));
    }

    @Nullable
    public Template getTemplate(@NotNull String id) {
        return templates.get(Template.format(id));
    }
}
