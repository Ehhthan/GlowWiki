package com.ehhthan.glowwiki.util;

import com.ehhthan.glowwiki.GlowWiki;
import com.google.gson.Gson;
import com.turbotailz.joindate.JoinDate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class JoinDateUtil {
    private static final Gson GSON = new Gson();

    public static int getPlayerNumber(UUID uuid) {
        return JoinDate.getPlugin().jnMap.get(uuid);
    }

    public static String getPlayerJoinDate(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return new SimpleDateFormat(GlowWiki.getInstance().getConfig().getString("date", "MMMM d, yyyy")).format(new Date(player.getFirstPlayed()));
    }

    public static String getPlayerJoinYear(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return new SimpleDateFormat("yyyy").format(new Date(player.getFirstPlayed()));
    }
}
