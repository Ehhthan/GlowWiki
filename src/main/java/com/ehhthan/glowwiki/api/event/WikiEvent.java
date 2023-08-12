package com.ehhthan.glowwiki.api.event;

import com.ehhthan.glowwiki.api.event.action.EventAction;
import com.ehhthan.glowwiki.api.util.FormatUtil;
import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class WikiEvent {
    private final String id;
    private final Type type;
    private final List<EventAction> actions = new LinkedList<>();

    public WikiEvent(ConfigurationSection section) {
        this.id = FormatUtil.id(section.getName());

        this.type = Type.get(section.getString("event", "ON_JOIN"));
        ConfigurationSection actionSection = section.getConfigurationSection("actions");
        if (actionSection != null) {
            for (String key : actionSection.getKeys(false)) {
                actions.add(EventAction.get(actionSection.getConfigurationSection(key)));
            }
        }
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public List<EventAction> getActions() {
        return actions;
    }

    public enum Type {
        ON_JOIN,
        ON_QUIT,
        MANUAL;

        public static Type get(String id) {
            id = id.toUpperCase(Locale.ROOT).replace("-", "_").replace(" ", "_");
            return Type.valueOf(id);
        }
    }
}
