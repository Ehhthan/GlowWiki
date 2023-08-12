package com.ehhthan.glowwiki.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.GlowAuditor;
import org.bukkit.command.CommandSender;

@CommandAlias("glowwiki|gw|wiki")
@CommandPermission("glowwiki.help")
@Description("Main glow wiki command.")
public class GlowWikiCommand extends BaseCommand {
    private final GlowWiki plugin;

    public GlowWikiCommand(GlowWiki plugin) {
        this.plugin = plugin;
    }

    @Subcommand("audit")
    @CommandPermission("glowwiki.audit")
    @Description("Peform an audit.")
    @CommandCompletion("players")
    @Syntax("<players>")
    public void onAuditCommand(CommandSender sender, String function) {
        GlowAuditor auditor = plugin.getAuditor();
        if (function.equalsIgnoreCase("players")) {
//            auditor.runPlayerAudit();
        }
        sender.sendMessage("Performing audit...");
    }
}
