package org.leng;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BanManager {

    private final LengBetterKB plugin;

    public BanManager(LengBetterKB plugin) {
        this.plugin = plugin;
    }

    public void banPlayer(Player player) {
        String banMessage = plugin.getConfig().getString("ban-message", "你因异常击退被封禁。");
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), banMessage, null, null);
        player.kickPlayer(banMessage);
        plugin.getLogger().warning("玩家 " + player.getName() + " 已被封禁，原因：异常击退。");
    }
}