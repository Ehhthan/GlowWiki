package com.ehhthan.glowwiki.api.info;

import org.bukkit.OfflinePlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface GlowInfo {
    Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([^%]+)[%]");
    Pattern SPECIAL_PATTERN = Pattern.compile("[%]([a-zA-Z]+)-([^%]+)[%]");

    String build(OfflinePlayer player);

    static String parse(String text, OfflinePlayer player) {
        String newText = text;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);

        while (matcher.find()) {
            String placeholder = matcher.group();
            Matcher special = SPECIAL_PATTERN.matcher(placeholder);
            if (special.find()) {
                String type = special.group(1);
                String value = special.group(2);
                if (type.equalsIgnoreCase("template"))
                    newText = newText.replace(placeholder, new TemplateInfo(value).build(player));
                else if (type.equalsIgnoreCase("atlas"))
                    newText = newText.replace(placeholder, new AtlasInfo(value).build(player));

            } else {
                newText = newText.replace(placeholder, PlayerInfo.get(matcher.group(1)).build(player));
            }
        }

        return newText;
    }
}
