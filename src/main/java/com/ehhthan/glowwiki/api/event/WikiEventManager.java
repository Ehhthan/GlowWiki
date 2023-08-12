package com.ehhthan.glowwiki.api.event;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.util.FormatUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WikiEventManager {
    private final Map<String, WikiEvent> events = new HashMap<>();

    public WikiEventManager(@NotNull GlowWiki plugin) {
        reload(plugin);
    }

    public void reload(@NotNull GlowWiki plugin) {
        events.clear();

        File[] files = new File(plugin.getDataFolder(), "events").listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File configFile : files) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
                for (String key : config.getKeys(false)) {
                    ConfigurationSection section = config.getConfigurationSection(key);
                    if (section != null) {
                        WikiEvent event = new WikiEvent(section);
                        events.put(event.getId(), event);
                    }
                }
            }
        }
    }

    public boolean hasEvent(@NotNull String id) {
        return events.containsKey(FormatUtil.id(id));
    }

    @Nullable
    public WikiEvent getEvent(@NotNull String id) {
        return events.get(FormatUtil.id(id));
    }

    public List<WikiEvent> getEvents(WikiEvent.Type type) {
        return events.values().stream().filter(c -> c.getType() == type).collect(Collectors.toList());
    }
}
