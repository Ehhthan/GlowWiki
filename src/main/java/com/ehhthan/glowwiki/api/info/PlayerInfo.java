package com.ehhthan.glowwiki.api.info;

import com.ehhthan.glowwiki.GlowWiki;
import com.turbotailz.joindate.JoinDate;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public enum PlayerInfo implements GlowInfo {
    USERNAME {
        @Override
        public String build(OfflinePlayer player) {
            return player.getName();
        }
    },
    UUID {
        @Override
        public String build(OfflinePlayer player) {
            return player.getUniqueId().toString();
        }
    },
    JOIN_NUMBER {
        @Override
        public String build(OfflinePlayer player) {
            Integer number = JoinDate.getPlugin().jnMap.get(player.getUniqueId());
            if (number != null && number > 0)
                return smallFormat().format(number);
            else
                return "?";
        }
    },
    LAST_SEEN {
        @Override
        public String build(OfflinePlayer player) {
            long seen = player.getLastSeen();
            if (seen > 0)
                return dateFormat().format(new Date(seen));
            else
                return "?";
        }
    },
    JOIN_DATE {
        @Override
        public String build(OfflinePlayer player) {
            long played = player.getFirstPlayed();
            if (played > 0)
                return dateFormat().format(new Date(played));
            else
                return "?";
        }

    },
    JOIN_YEAR {
        @Override
        public String build(OfflinePlayer player) {
            return new SimpleDateFormat("yyyy").format(new Date(player.getFirstPlayed()));
        }
    },
    TIME_PLAYED {
        @Override
        public String build(OfflinePlayer player) {
            Duration duration = Duration.ofSeconds(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20);
            long hours = duration.toHours();
            if (hours != 0) {
                return String.format("%d " + ((hours == 1) ? "Hour" : "Hours"), hours);
            } else {
                int minutes = duration.toMinutesPart();
                return String.format("%01d " + ((minutes == 1) ? "Minute" : "Minutes"), minutes);
            }
        }
    },
    DISTANCE_TRAVELED {
        @Override
        public String build(OfflinePlayer player) {
            return largeFormat().format((player.getStatistic(Statistic.CLIMB_ONE_CM)
                + player.getStatistic(Statistic.CROUCH_ONE_CM)
                + player.getStatistic(Statistic.FALL_ONE_CM)
                + player.getStatistic(Statistic.FLY_ONE_CM)
                + player.getStatistic(Statistic.SPRINT_ONE_CM)
                + player.getStatistic(Statistic.SWIM_ONE_CM)
                + player.getStatistic(Statistic.WALK_ONE_CM)
                + player.getStatistic(Statistic.WALK_ON_WATER_ONE_CM)
                + player.getStatistic(Statistic.WALK_UNDER_WATER_ONE_CM)
                + player.getStatistic(Statistic.BOAT_ONE_CM)
                + player.getStatistic(Statistic.AVIATE_ONE_CM)
                + player.getStatistic(Statistic.HORSE_ONE_CM)
                + player.getStatistic(Statistic.MINECART_ONE_CM)
                + player.getStatistic(Statistic.PIG_ONE_CM)
                + player.getStatistic(Statistic.STRIDER_ONE_CM)) / 100) + " Blocks";
        }
    },
    PLAYER_KILLS {
        @Override
        public String build(OfflinePlayer player) {
            return smallFormat().format(player.getStatistic(Statistic.PLAYER_KILLS));
        }
    },
    DEATHS {
        @Override
        public String build(OfflinePlayer player) {
            return smallFormat().format(player.getStatistic(Statistic.DEATHS));
        }
    };

    private static DateFormat dateFormat() {
        return new SimpleDateFormat(GlowWiki.getInstance().getConfig().getString("date-format", "MMMM d, yyyy"));
    }

    private static DecimalFormat smallFormat() {
        return new DecimalFormat(GlowWiki.getInstance().getConfig().getString("decimal-format", "###,###"));
    }

    private static NumberFormat largeFormat() {
        return NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
    }

    public abstract String build(OfflinePlayer player);

    public static PlayerInfo get(String id) {
        id = id.toUpperCase(Locale.ROOT).replace("-", "_").replace(" ", "_");
        return valueOf(id);
    }
}
