package com.ehhthan.glowwiki.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.audit.GlowAuditor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
    @CommandCompletion("players <event-name>")
    @Syntax("<players> <event-name>")
    public void onAuditCommand(CommandSender sender, String function, String arg) {
        GlowAuditor auditor = plugin.getAuditor();
        if (function.equalsIgnoreCase("players")) {
            auditor.runPlayerAudit(arg, sender);
        }
        sender.sendMessage("Performing audit...");
    }

    @Subcommand("reload")
    @CommandPermission("glowwiki.reload")
    @Description("Peform a reload.")
    public void onReloadCommand(CommandSender sender) {
        plugin.reload();

        sender.sendMessage(Component.text("Reloading GlowWiki.").decorate(TextDecoration.BOLD).color(NamedTextColor.GREEN));
        sender.sendMessage(Component.text("Reloaded " + plugin.getTemplates().values().size() + " Templates").color(NamedTextColor.RED));
        sender.sendMessage(Component.text("Reloaded " + plugin.getEvents().values().size() + " Events").color(NamedTextColor.BLUE));
        sender.sendMessage(Component.text("Reloaded " + plugin.getAtlases().values().size() + " Atlases").color(NamedTextColor.YELLOW));
    }
}
