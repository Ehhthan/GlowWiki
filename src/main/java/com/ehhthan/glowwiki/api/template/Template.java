package com.ehhthan.glowwiki.api.template;

import com.ehhthan.glowwiki.GlowWiki;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Map;

public class Template {
    private final String id;
    private final boolean enabled;

    private String text = null;

    public Template(ConfigurationSection section) {
        this.id = format(section.getName());
        this.enabled = section.getBoolean("enabled", false);

        if (enabled && section.isString("text")) {
            // TODO: 5/7/2023 improve this
            File file = new File(GlowWiki.getInstance().getDataFolder(), "templates/" + section.getString("text"));
            try {
                text = Files.readString(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isText() {
        return text != null;
    }

    public String getText() {
        return text;
    }

    public String getText(Map<String, String> placeholders) {
        String returnText = text;
        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            returnText = returnText.replace("%" + placeholder.getKey() + "%", placeholder.getValue());
        }
        return returnText;
    }

    public static String format(@NotNull String id) {
        return id.toLowerCase(Locale.ROOT).replace('_', '-').replace(' ', '-');
    }
}
