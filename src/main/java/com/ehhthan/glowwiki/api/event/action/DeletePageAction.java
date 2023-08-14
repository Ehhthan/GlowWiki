package com.ehhthan.glowwiki.api.event.action;

import com.ehhthan.glowwiki.api.info.GlowInfo;
import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class DeletePageAction extends EventAction {
    private final String title;

    public DeletePageAction(ConfigurationSection section) {
        this.title = section.getString("title");
    }

    @Override
    public void run(WikiAPI api, OfflinePlayer player) {
        try {
            api.delete(GlowInfo.parse(title, player), "Requested Deletion.", true);
        } catch (IOException | LoginException e) {
            e.printStackTrace();
        }
    }
}
