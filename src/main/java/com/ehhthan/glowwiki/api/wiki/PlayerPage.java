package com.ehhthan.glowwiki.api.wiki;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.WikiAPI;
import com.ehhthan.glowwiki.api.template.Template;
import com.ehhthan.glowwiki.util.JoinDateUtil;
import com.ehhthan.glowwiki.util.SkinUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerPage {
    private final String name;
    private final UUID uuid;

    public PlayerPage(OfflinePlayer player) {
        this.name = player.getName();
        this.uuid = player.getUniqueId();
    }

    public void create(WikiAPI api, Template template) {
        Bukkit.getScheduler().runTaskAsynchronously(GlowWiki.getInstance(), () -> {
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("username", name);
            placeholders.put("uuid", uuid.toString());
            placeholders.put("join", JoinDateUtil.getPlayerJoinDate(uuid));
            placeholders.put("number", String.valueOf(JoinDateUtil.getPlayerNumber(uuid)));
            placeholders.put("year", JoinDateUtil.getPlayerJoinYear(uuid));
            try {
                boolean[] exists = api.exists(List.of(name, "File:" + name + ".png"));

                if (!exists[0])
                    api.edit(name, template.getText(placeholders), "New player page created.");
                if (!exists[1])
                    api.upload(SkinUtil.getFullBody(uuid), name, "", "Added player photo.");
            } catch (IOException | LoginException e) {
                e.printStackTrace();
            }
        });
    }
}
