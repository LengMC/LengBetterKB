package org.leng;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class KBManager {

    private final LengBetterKB plugin;
    private double defaultHorizontalKB;
    private double defaultVerticalKB;

    public KBManager(LengBetterKB plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        defaultHorizontalKB = config.getDouble("kb.horizontal", 0.4);
        defaultVerticalKB = config.getDouble("kb.vertical", 0.4);
    }

    public double getHorizontalKB() {
        // 根据服务器性能动态调整击退
        double performanceFactor = getPerformanceFactor();
        return defaultHorizontalKB * performanceFactor;
    }

    public double getVerticalKB() {
        // 根据服务器性能动态调整击退
        double performanceFactor = getPerformanceFactor();
        return defaultVerticalKB * performanceFactor;
    }

    private double getPerformanceFactor() {
        // 获取服务器性能因子
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsage = (double) usedMemory / maxMemory;

        // 如果内存使用率超过 80%，减少击退
        if (memoryUsage > 0.8) {
            return 0.8;
        }
        return 1.0;
    }
}