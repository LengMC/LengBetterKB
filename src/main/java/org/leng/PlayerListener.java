package org.leng;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener implements Listener {

    private final LengBetterKB plugin;
    private final Map<Player, Long> lastHitTimeMap; // 记录玩家上次被击退的时间
    private final Map<Player, Double> vlHistoryMap; // 记录玩家的 VL 历史

    public PlayerListener(LengBetterKB plugin) {
        this.plugin = plugin;
        this.lastHitTimeMap = new HashMap<>();
        this.vlHistoryMap = new HashMap<>();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damaged = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            // 检查 damager 是否有 bypass 权限
            if (damager.hasPermission("lengbetterkb.bypass")) {
                return; // 如果有权限，跳过检测
            }

            // 获取击退设置
            KBManager kbManager = plugin.getKBManager();
            double horizontalKB = kbManager.getHorizontalKB();
            double verticalKB = kbManager.getVerticalKB();

            // 计算击退方向
            Vector direction = damaged.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();

            // 应用击退
            damaged.setVelocity(direction.multiply(horizontalKB).setY(verticalKB));

            // 计算 VL 增加量
            double vlIncrease = calculateVLIncrease(damaged);
            if (vlIncrease > 0) {
                plugin.getVLManager().increaseVL(damaged, vlIncrease);
            }

            // 检查是否需要封禁
            plugin.getVLManager().checkAndBanPlayer(damaged);
        }
    }

    private double calculateVLIncrease(Player player) {
        long currentTime = System.currentTimeMillis();
        long lastHitTime = lastHitTimeMap.getOrDefault(player, 0L);
        double vlIncrease = 0.0;

        // 计算时间差（毫秒）
        long timeDiff = currentTime - lastHitTime;

        // 如果时间差小于 500 毫秒（0.5 秒），可能是异常行为
        if (timeDiff < 500) {
            vlIncrease = 2.0; // 快速连续击退，增加更多 VL
        } else if (timeDiff < 1000) {
            vlIncrease = 1.0; // 较快击退，增加少量 VL
        } else {
            vlIncrease = 0.5; // 正常击退，增加少量 VL
        }

        // 更新上次击退时间
        lastHitTimeMap.put(player, currentTime);

        // 考虑历史行为
        double historyVL = vlHistoryMap.getOrDefault(player, 0.0);
        vlHistoryMap.put(player, historyVL + vlIncrease);

        // 如果历史 VL 过高，增加更多 VL
        if (historyVL > 10.0) {
            vlIncrease *= 1.5;
        }

        // VL 衰减机制：每 5 秒减少 1.0 VL
        if (timeDiff > 5000) {
            double decay = (timeDiff / 5000) * 1.0;
            vlHistoryMap.put(player, Math.max(0, historyVL - decay));
        }

        return vlIncrease;
    }
}