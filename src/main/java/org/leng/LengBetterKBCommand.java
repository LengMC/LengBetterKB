package org.leng;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LengBetterKBCommand implements CommandExecutor {

    private final LengBetterKB plugin;

    public LengBetterKBCommand(LengBetterKB plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§c用法: /lengbetterkb <reload|setkb|setvl>");
            return true;
        }

        // 检查权限
        if (!sender.hasPermission("lengbetterkb.admin")) {
            sender.sendMessage("§c你没有权限使用此命令！");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage("§a配置已重载！");
                break;
            case "setkb":
                if (args.length < 3) {
                    sender.sendMessage("§c用法: /lengbetterkb setkb <horizontal> <vertical>");
                    return true;
                }
                try {
                    double horizontal = Double.parseDouble(args[1]);
                    double vertical = Double.parseDouble(args[2]);
                    plugin.getConfig().set("kb.horizontal", horizontal);
                    plugin.getConfig().set("kb.vertical", vertical);
                    plugin.saveConfig();
                    sender.sendMessage("§a击退设置已更新！");
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c无效的数字格式！");
                }
                break;
            case "setvl":
                if (args.length < 2) {
                    sender.sendMessage("§c用法: /lengbetterkb setvl <threshold>");
                    return true;
                }
                try {
                    double threshold = Double.parseDouble(args[1]);
                    plugin.getConfig().set("vl.ban-threshold", threshold);
                    plugin.saveConfig();
                    sender.sendMessage("§aVL 封禁阈值已更新！");
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c无效的数字格式！");
                }
                break;
            default:
                sender.sendMessage("§c未知命令！");
                break;
        }
        return true;
    }
}