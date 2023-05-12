package com.ehhthan.glowwiki.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class FormatUtil {
    public static String id(@NotNull String id) {
        return id.toLowerCase(Locale.ROOT).replace('_', '-').replace(' ', '-');
    }
}
