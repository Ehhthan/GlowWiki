package com.ehhthan.glowwiki.api.info;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.wiki.template.WikiTemplate;
import org.bukkit.OfflinePlayer;

public class TemplateInfo implements GlowInfo {
    private final String templateId;

    public TemplateInfo(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String build(OfflinePlayer player) {
        WikiTemplate template = GlowWiki.getInstance().getTemplates().getTemplate(templateId);

        if (template != null)
            return template.build(player);
        else
            return "";
    }
}
