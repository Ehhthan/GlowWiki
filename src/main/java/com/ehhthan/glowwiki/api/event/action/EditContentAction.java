package com.ehhthan.glowwiki.api.event.action;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.info.GlowInfo;
import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EditContentAction extends EventAction {
    private final String title;
    private final int section;
    private final String content;

    public EditContentAction(ConfigurationSection section) {
        this.title = section.getString("title");
        this.section = section.getInt("section");
        String content = "";
        if (section.isString("file")) {
            File file = new File(GlowWiki.getInstance().getDataFolder(), "content/" + section.getString("file"));
            try {
                content = Files.readString(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (section.isString("content")) {
            content = section.getString("content");
        }

        this.content = content;
    }

    @Override
    public void run(WikiAPI api, OfflinePlayer player) {
        try {
            api.edit(GlowInfo.parse(title, player), GlowInfo.parse(content, player),
                "Updated content.", section);
        } catch (IOException | LoginException e) {
            e.printStackTrace();
        }
    }
}
