package com.ehhthan.glowwiki.api.event.action;

import com.ehhthan.glowwiki.api.info.GlowInfo;
import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.List;

public class CopyContentAction extends EventAction {
    private final String original, newPage;
    private final boolean replaceIfExists, deleteOriginal;

    public CopyContentAction(ConfigurationSection section) {
        this.original = section.getString("original");
        this.newPage = section.getString("new-page");
        this.replaceIfExists = section.getBoolean("replace-if-exists", false);
        this.deleteOriginal = section.getBoolean("delete-original", false);
    }

    @Override
    public void run(WikiAPI api, OfflinePlayer player) {
        try {
            String original = GlowInfo.parse(this.original, player);
            String newPage = GlowInfo.parse(this.newPage, player);

            if (api.exists(List.of(original))[0]) {
                String content = api.getSectionText(original, 0);
                if (!content.contains("#REDIRECT") && (!api.exists(List.of(newPage))[0] || replaceIfExists)) {
                    api.edit(newPage, content, "Copied content from '" + original  + "'.", -1);

                    if (deleteOriginal) {
                        api.delete(original, "Page was copied to '" + newPage + "'.", true);
                    }
                }
            }
        } catch (IOException | LoginException e) {
            e.printStackTrace();
        }
    }
}
