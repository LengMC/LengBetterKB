package org.leng;

import org.bukkit.plugin.java.JavaPlugin;

public class LengBetterKB extends JavaPlugin {

    private KBManager kbManager;
    private VLManager vlManager;
    private BanManager banManager;

    @Override
    public void onEnable() {
        // 加载配置
        saveDefaultConfig();
        reloadConfig();

        // 初始化模块
        kbManager = new KBManager(this);
        vlManager = new VLManager(this);
        banManager = new BanManager(this);

        // 注册命令
        this.getCommand("lengbetterkb").setExecutor(new LengBetterKBCommand(this));

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        getLogger().info("LengBetterKB 已启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("LengBetterKB 已禁用！");
    }

    // 获取模块实例
    public KBManager getKBManager() {
        return kbManager;
    }

    public VLManager getVLManager() {
        return vlManager;
    }

    public BanManager getBanManager() {
        return banManager;
    }
}