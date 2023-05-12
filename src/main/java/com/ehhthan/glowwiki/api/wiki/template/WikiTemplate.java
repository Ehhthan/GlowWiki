package com.ehhthan.glowwiki.api.wiki.template;

import com.ehhthan.glowwiki.api.info.PlaceholderInfo;
import com.ehhthan.glowwiki.api.info.PlayerInfo;
import com.ehhthan.glowwiki.api.info.TemplateInfo;
import com.ehhthan.glowwiki.api.util.FormatUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedHashMap;
import java.util.Map;

public class WikiTemplate {
    private final String id;
    private final String template;
    private final Map<String, PlaceholderInfo> parameters = new LinkedHashMap<>();

    public WikiTemplate(ConfigurationSection section) {
        this.id = FormatUtil.id(section.getName());
        this.template = section.getString("wiki-template", "Unknown");
        ConfigurationSection parameterSection = section.getConfigurationSection("parameters");
        if (parameterSection != null) {
            for (String key : parameterSection.getKeys(false)) {
                if (parameterSection.isString(key + ".template")) {
                    parameters.put(key, new TemplateInfo(parameterSection.getString(key + ".template")));
                } else {
                    parameters.put(key, PlayerInfo.get(parameterSection.getString(key, "")));
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public String build(OfflinePlayer player) {
        StringBuilder builder = new StringBuilder().append("{{").append(template);

        for (Map.Entry<String, PlaceholderInfo> parameter : parameters.entrySet()) {
            String build = parameter.getValue().build(player);
            if (build != null && !build.isEmpty())
                builder.append("|").append(parameter.getKey()).append("=").append(build);
        }

        builder.append("}}");

        return builder.toString();
    }
}
