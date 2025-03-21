package org.leng;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class VLManager {

    private final LengBetterKB plugin;
    private final Map<Player, Double> playerVLMap;

    public VLManager(LengBetterKB plugin) {
        this.plugin = plugin;
        this.playerVLMap = new HashMap<>();
        startVLDegradationTask();
    }

    public void increaseVL(Player player, double amount) {
        double currentVL = playerVLMap.getOrDefault(player, 0.0);
        playerVLMap.put(player, currentVL + amount);
    }

    public void decreaseVL(Player player, double amount) {
        double currentVL = playerVLMap.getOrDefault(player, 0.0);
        playerVLMap.put(player, Math.max(0, currentVL - amount));
    }

    public double getVL(Player player) {
        return playerVLMap.getOrDefault(player, 0.0);
    }

    public void resetVL(Player player) {
        playerVLMap.remove(player);
    }

    public void checkAndBanPlayer(Player player) {
        double vlThreshold = plugin.getConfig().getDouble("vl.ban-threshold", 20.0);
        if (getVL(player) >= vlThreshold) {
            plugin.getBanManager().banPlayer(player);
        }
    }

    private void startVLDegradationTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    decreaseVL(player, 1.0); // 每 10 秒减少 1.0 VL
                }
            }
        }.runTaskTimer(plugin, 200L, 200L); // 每 10 秒执行一次
    }
}