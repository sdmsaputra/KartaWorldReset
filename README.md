# KartaWorldReset Plugin v1.1.0-SNAPSHOT

A powerful and easy-to-use plugin for automatically resetting worlds on a schedule.

## Features
- **Scheduled Resets**: Configure worlds to reset daily, weekly, or monthly at a specific time.
- **Multi-world Support**: Specify multiple worlds to be reset simultaneously.
- **Dynamic World Management**: Add or remove worlds from the reset list directly via in-game commands.
- **Customizable Messages**: Almost all plugin messages can be customized through the `config.yml`.
- **PlaceholderAPI Support**: Display countdown timers and reset information on scoreboards, boss bars, and more.
- **Lobby System**: Set a safe lobby world for players to be teleported to before a world reset occurs.
- **Permission-Based Commands**: Fine-grained control over who can use administrative commands.

## Commands
The main command is `/kartaworldreset`, which can be aliased with `/kwr`.

| Command                  | Description                               | Permission              |
|--------------------------|-------------------------------------------|-------------------------|
| `/kwr help`              | Displays the help message.                | (none)                  |
| `/kwr info`              | Shows the time remaining until the next reset. | (none)                  |
| `/kwr info clock`        | Shows the current system time.            | (none)                  |
| `/kwr info setting`      | Shows the current configuration settings. | `kartaworldreset.admin` |
| `/kwr info worldlist`    | Shows the list of worlds to be reset.     | `kartaworldreset.admin` |
| `/kwr reload`            | Reloads the plugin's configuration file.  | `kartaworldreset.admin` |
| `/kwr autogen`           | Automatically generates the `nextReset` date. | `kartaworldreset.admin` |
| `/kwr addworld <world>`  | Adds a world to the reset list.           | `kartaworldreset.admin` |
| `/kwr removeworld <world>`| Removes a world from the reset list.      | `kartaworldreset.admin` |
| `/kwr papi reload`       | Reloads the PlaceholderAPI expansion.     | `kartaworldreset.admin` |

## Permissions
| Permission              | Description                               |
|-------------------------|-------------------------------------------|
| `kartaworldreset.admin` | Grants access to all administrative commands. |

## PlaceholderAPI Placeholders
| Placeholder             | Description                               |
|-------------------------|-------------------------------------------|
| `%kartaworldreset_normal%`| Shows the time left until the next reset in a long format (e.g., "1 week 2 days 3 hours"). |
| `%kartaworldreset_nextReset%`| Shows the exact date and time of the next reset. |
| `%kartaworldreset_short%` | Shows the time left until the next reset in a short format (e.g., "dd:hh:mm:ss"). |

## Configuration (`config.yml`)
The configuration is split into several sections.

### Main Configuration
```yaml
Config:
  # Set the reset frequency.
  # d = day, w = week, m = month
  every: 1w
  # Set the time of day for the reset (24-hour format).
  time: "00:00"

Save:
  # The date of the next reset.
  # Can be left null to be auto-generated on the first run.
  # Format: dd-MM-yyyy
  nextReset: null

Worlds:
  # A list of worlds to be reset.
  - world_the_end

Lobby:
  # The world to teleport players to before the reset.
  # Set to null to teleport players to their bed or the main world.
  - world
```

### Message Customization
All user-facing messages can be customized in the `Messages` section. You can use standard Bukkit color codes (`&a`, `&b`, etc.).
New messages for the `addworld` and `removeworld` commands have been added.

```yaml
Messages:
  reload: "&a[KartaWorldReset] Configuration reloaded!"
  autogen: "&a[KartaWorldReset] Autogen complete!"
  papi-reloaded: "&a[KartaWorldReset] PAPI Registered!"
  papi-failed: "&c[KartaWorldReset] PAPI Register is failed!"
  no-permission: "&cYou don't have permission to do that."
  world-list: "&eWorldlist : &f[%worlds%]"
  lobby: "&eLobby : &f%lobby%"
  world-added: "&aWorld %world% has been added to the list."
  world-removed: "&aWorld %world% has been removed from the list."
  world-already-exists: "&cWorld %world% is already in the list."
  world-not-found: "&cWorld %world% is not in the list."
  add-world-usage: "&cUsage: /kwr addworld <world>"
  remove-world-usage: "&cUsage: /kwr removeworld <world>"
  help:
    - "&eKartaWorldReset Plugin"
    - "&e[Usages]:"
    - "&f/kwr reload &7- &aReload config plugin (Admin)"
    - "&f/kwr autogen &7- &aAuto generate config (Admin)"
    - "&f/kwr addworld <world> &7- &aAdd world to reset list (Admin)"
    - "&f/kwr removeworld <world> &7- &aRemove world from reset list (Admin)"
    - "&f/kwr info &7- &aShow time left"
    - "&f/kwr info setting &7- &aShow config info (Admin)"
    - "&f/kwr info worldlist &7- &aShow world reset list (Admin)"
    - "&f/kwr info clock &7- &aShow system clock"
    - "&f/kwr papi reload &7- &aReload placeholder expansion"
```
