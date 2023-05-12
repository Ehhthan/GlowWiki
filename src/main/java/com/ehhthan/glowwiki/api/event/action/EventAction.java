package com.ehhthan.glowwiki.api.event.action;

import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public abstract class EventAction {
    public EventAction() {
    }

    public abstract void run(WikiAPI api, OfflinePlayer player);

    public static EventAction get(ConfigurationSection section) {
        if (section != null) {
            switch (ActionType.get(section.getString("type"))) {
                case UPLOAD_FILE -> {
                    return new UploadFileAction(section);
                }
                case EDIT_CONTENT -> {
                    return new EditContentAction(section);
                }
            }
        }
        throw new IllegalArgumentException("Section is not defined.");
    }
}
