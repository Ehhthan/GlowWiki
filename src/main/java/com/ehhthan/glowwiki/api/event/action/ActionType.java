package com.ehhthan.glowwiki.api.event.action;

import java.util.Locale;

public enum ActionType {
    UPLOAD_FILE,
    EDIT_CONTENT;

    public static ActionType get(String id) {
        id = id.toUpperCase(Locale.ROOT).replace("-", "_").replace(" ", "_");
        return ActionType.valueOf(id);
    }
}
