# Tank vs Planes 🛩️🛡️

A fast-paced 2D arcade shooter built with the **FXGL Game Engine**. Take control of a tank, defend your territory against waves of escalating aerial threats, and utilize a variety of power-ups to survive the onslaught.

---

## 🎮 Game Features

* **Escalating Difficulty:** Five distinct game stages with enemy spawning that scales based on your kill count.
* **Dynamic Power-Up System:** * 🍔 **Heal:** Restore your health by eating burgers.
    * ⚡ **Energy Drink:** Temporary movement speed boost.
    * 🛡️ **Wall:** Create a defensive barrier against enemies.
    * 💣 **Nuke:** Wipe out threats instantly.
    * 🔫 **Gun Upgrade:** Increase your firepower with up to 9 weapon levels (multi-shot).
* **Progressive Enemy Types:** Face off against Normal, Medium, and Large planes, each with unique health pools and point values.
* **High-Resolution UI:** Real-time health bars, score tracking, and status indicators.

---

## 🕹️ Controls

| Key | Action |
| :--- | :--- |
| **A** | Move Left |
| **D** | Move Right |
| **SPACE** | Fire Main Cannon |

---

## 🛠️ Technical Overview

The project is built using Java and the FXGL library, utilizing an Entity-Component-System (ECS) architecture.

* **Launcher.java**: The main entry point. Handles game initialization, input binding, and physics rules.
* **ConfigUtil.java**: Centralized configuration for game constants, screen dimensions, and balance tweaks.
* **GameEntityFactory.java**: Manages the creation of all game entities (Player, Enemies, Bullets, Power-ups, and Explosions).
* **Components**: Custom logic for entity behavior (e.g., `PlayerComponent` for movement/shooting, `EnemyComponent` for AI and health).
* **GameUI.java**: Handles the JavaFX-based HUD and reactive property binding for the UI.

---

## 🚀 Getting Started

### Prerequisites
* **Java 17** or higher
* **Maven**
