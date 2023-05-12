package com.ehhthan.glowwiki.api.event.action;

import com.ehhthan.glowwiki.GlowWiki;
import com.ehhthan.glowwiki.api.info.PlaceholderInfo;
import com.ehhthan.glowwiki.api.wiki.WikiAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class UploadFileAction extends EventAction {
    private final String url, name, format;

    public UploadFileAction(ConfigurationSection section) {
        this.url = section.getString("url");
        this.name = section.getString("name");
        this.format = section.getString("format", "png");
    }


    @Override
    public void run(WikiAPI api, OfflinePlayer player) {
        try {
            BufferedImage image = ImageIO.read(new URL(PlaceholderInfo.parse(url, player)));
            File file = new File(GlowWiki.getInstance().getDataFolder(), "cache/" +
                PlaceholderInfo.parse(name + "." + format, player));

            if (!api.exists(List.of("File:" + file.getName()))[0]) {
                file.mkdirs();
                ImageIO.write(image, PlaceholderInfo.parse(format, player), file);
                api.upload(file, file.getName(), "", "Uploaded file.");
                file.deleteOnExit();
            }
        } catch (IOException | LoginException e) {
            e.printStackTrace();
        }
    }
}
