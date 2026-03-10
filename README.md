# TradesbyPinsel

A simple and intuitive Minecraft trading plugin that allows players to sell and buy items using a GUI marketplace system.

---

## Overview

| Feature            | Description                                                  |
| ------------------ | ------------------------------------------------------------ |
| GUI Trading System | Players can easily sell and buy items through an in-game GUI |
| Custom Prices      | Sellers can adjust prices using buttons                      |
| Player Marketplace | Browse items listed by other players                         |
| Listing Management | Players can remove their own listings                        |
| Admin Controls     | Admins can remove listings from other players                |

---

# Features

* GUI-based trading system
* Player-to-player item marketplace
* Adjustable item prices
* Easy listing management
* Admin moderation tools
* Lightweight and server-friendly

---

# How It Works

Players can open the trading menu using:

```
/trade
```

A GUI opens with three options:

| Option      | Description                          |
| ----------- | ------------------------------------ |
| Sell Item   | List an item for sale                |
| My Listings | View and remove your listings        |
| Buy Items   | Browse items listed by other players |

Each option opens its own GUI.

---

# Selling Items

When selecting **Sell Item**, a trading interface opens.

Players place the item they want to sell into the center slot.

Price buttons allow adjusting the value:

| Button                  | Action         |
| ----------------------- | -------------- |
| +1 / +10 / +100 / +1000 | Increase price |
| -1 / -10 / -100 / -1000 | Decrease price |

Right-clicking increases the value faster.

A **paper item** in the GUI shows the current price and information.

Players can confirm the listing with the **green confirm button**.

---

# Buying Items

Players can browse all active listings from other players.

Available actions:

* View listed items
* Purchase items directly
* Receive the purchased item instantly

---

# My Listings

The **My Listings** menu allows players to manage their listings.

Players can:

* View their active listings
* Remove listings using right-click

---

# Admin Menu

Admins have additional controls inside the GUI.

| Admin Feature   | Description                               |
| --------------- | ----------------------------------------- |
| Remove Listings | Delete listings from other players        |
| Admin Indicator | Admins see a special red block in the GUI |

---

# Commands

## Player Commands

| Command  | Description           |
| -------- | --------------------- |
| `/trade` | Opens the trading GUI |

## Admin Commands

| Command         | Description                      |
| --------------- | -------------------------------- |
| `/trade reload` | Reloads the plugin configuration |

---

# Installation

1. Download the plugin `.jar`
2. Put it into the **plugins** folder of your server
3. Restart the server

---

# Requirements

| Requirement      | Version           |
| ---------------- | ----------------- |
| Minecraft Server | 1.20+ recommended |
| Server Software  | Spigot / Paper    |

---

# Performance

TradesbyPinsel is designed to be lightweight and efficient.

The plugin has minimal performance impact and works well even on larger servers.

---

# Developer

| Developer |
| --------- |
| PinselDEV |

---

# License

This project is licensed under the MIT License.
