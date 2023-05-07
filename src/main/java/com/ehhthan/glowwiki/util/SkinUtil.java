package com.ehhthan.glowwiki.util;

import com.ehhthan.glowwiki.GlowWiki;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class SkinUtil {
    public static File getFullBody(UUID uuid) {
        try {
            BufferedImage image = ImageIO.read(new URL("https://visage.surgeplay.com/full/600/" + uuid));
            File file = new File(GlowWiki.getInstance().getDataFolder(), "cache/" + uuid + ".png");
            ImageIO.write(image, "png", file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
