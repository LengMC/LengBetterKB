# LengBetterKB

[![Minecraft Version](https://img.shields.io/badge/Minecraft-1.8.9-brightgreen)](https://www.minecraft.net)
[![License](https://img.shields.io/badge/License-MPL2.0-blue)](LICENSE)

`LengBetterKB` 是一个用于 Minecraft 1.8.9 服务器的插件，旨在优化玩家的击退（KB）体验，并检测异常行为（如 NoKB）。插件会自动调整击退值，检测异常击退行为，并在必要时封禁玩家。

---

## 功能

- **击退优化**：
  - 根据服务器性能动态调整击退值。
  - 支持水平和垂直击退的独立配置。

- **异常检测**：
  - 检测玩家的击退行为，计算违规等级（VL）。
  - 当 VL 超过阈值时，自动向管理员发送警告。
  - 当 VL 达到封禁阈值时，自动封禁玩家。

- **权限系统**：
  - 支持管理员权限和绕过检测权限。
  - 管理员可以调整击退设置和 VL 阈值。

- **模块化设计**：
  - 击退计算、VL 检测和封禁逻辑分离，便于维护和扩展。

---

## 安装

1. 下载插件：
   - 从 [Releases](https://github.com/LengMC/LengBetterKB/releases) 页面下载最新的 `.jar` 文件。

2. 安装插件：
   - 将 `LengBetterKB.jar` 放入服务器的 `plugins` 文件夹中。

3. 启动服务器：
   - 插件会自动生成默认配置文件。

4. 配置插件：
   - 根据需要修改 `plugins/LengBetterKB/config.yml` 文件。

---

## 配置文件 (`config.yml`)

```yaml
# LengBetterKB 配置文件

# 击退设置
kb:
  horizontal: 0.4  # 水平击退值
  vertical: 0.4    # 垂直击退值

# VL 设置
vl:
  threshold: 10.0      # VL 警告阈值
  ban-threshold: 20.0  # VL 封禁阈值

# 封禁提示
ban-message: "你因异常击退被封禁。"

