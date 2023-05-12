package com.ehhthan.glowwiki.api.info;

import org.bukkit.OfflinePlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PlaceholderInfo {
    Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([^%]+)[%]");
    Pattern TEMPLATE_PATTERN = Pattern.compile("[%]template-([^%]+)[%]");

    String build(OfflinePlayer player);

    static String parse(String text, OfflinePlayer player) {
        String newText = text;
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);

        while (matcher.find()) {
            String placeholder = matcher.group();
            Matcher template = TEMPLATE_PATTERN.matcher(placeholder);
            if (template.find()) {
                newText = newText.replace(placeholder, new TemplateInfo(template.group(1)).build(player));
            } else {
                newText = newText.replace(placeholder, PlayerInfo.get(matcher.group(1)).build(player));
            }
        }

        return newText;
    }
}
