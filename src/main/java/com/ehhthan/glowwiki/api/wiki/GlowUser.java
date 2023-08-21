package com.ehhthan.glowwiki.api.wiki;

import com.ehhthan.glowwiki.GlowWiki;
import org.bukkit.configuration.ConfigurationSection;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;

public class GlowUser {
    private final String domain, scriptPath, protocol, username, password;

    private WikiAPI api;

    public GlowUser(ConfigurationSection section) throws IOException, URISyntaxException {
        this(section.getString("domain"),
            section.getString("script-path"),
            section.getString("protocol"),
            section.getString("credentials.username"),
            section.getString("credentials.password"));
    }

    public GlowUser(String domain, String scriptPath, String protocol, String username, String password) {
        this.domain = domain;
        this.scriptPath = scriptPath;
        this.protocol = protocol;
        this.username = username;
        this.password = password;
    }

    public WikiAPI getApi() {
        return api;
    }

    public void login() {
        api = WikiAPI.newSession(domain, scriptPath, protocol);
        api.setThrottle(1000);
        try {
            api.login(username, password);
        } catch (FailedLoginException | IOException ex) {
            // deal with failed login attempt
            ex.printStackTrace();
        }
    }

    public void reload() {
        if (api != null) {
            api.logout();
        }

        login();
    }
}
