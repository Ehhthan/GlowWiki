package com.ehhthan.glowwiki.api.event.action;

import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Locale;

public abstract class EventAction {
    public EventAction() {
    }

    public abstract void run(WikiAPI api, OfflinePlayer player);

    public static EventAction get(ConfigurationSection section) {
        if (section != null) {
            return switch (ActionType.get(section.getString("type"))) {
                case UPLOAD_FILE -> new UploadFileAction(section);
                case EDIT_CONTENT -> new EditContentAction(section);
                case COPY_CONTENT -> new CopyContentAction(section);
                case DELETE_PAGE -> new DeletePageAction(section);
            };
        }
        throw new IllegalArgumentException("Section is not defined.");
    }

    enum ActionType {
        UPLOAD_FILE,
        EDIT_CONTENT,
        COPY_CONTENT,
        DELETE_PAGE;

        private static ActionType get(String id) {
            id = id.toUpperCase(Locale.ROOT).replace("-", "_").replace(" ", "_");
            return valueOf(id);
        }
    }
}
