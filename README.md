# KartaWorldReset Plugin
Reset worlds automatically on a schedule.

## Features
- Schedule world resets (daily, weekly, or monthly)
- PlaceholderAPI support for displaying reset timers
- Multi-world support
- Set a lobby world for players to be teleported to before a reset
- Easy to use and configure

## Usage
```
[Usages]:
/kartaworldreset reload           Reload config plugin (Admin)
/kartaworldreset autogen          Auto generate config (Admin)
/kartaworldreset info             Show time left
/kartaworldreset info setting     Show config info (Admin)
/kartaworldreset info worldlist   Show world reset list (Admin)
/kartaworldreset info clock       Show system clock
/kartaworldreset papi reload      Reload placeholder expansion (Admin)
```

## Default Config
config.yml :
```
Config :
  # d=day w=week m=month
  every : 1w
  time : "00:00"

Save :
  # You can keep it null for auto-generation
  # or specify it yourself using the format dd-MM-yyyy
  nextReset : null

Worlds :
  - world_the_end

Lobby :
  # set null to teleport to bed or main world.
  - world
```

## Permissions
| Permission              | Description                               |
|-------------------------|-------------------------------------------|
| kartaworldreset.admin   | Gives permission to control plugin settings |


## PlaceholderAPI
| key                     | Description                               |
|-------------------------|-------------------------------------------|
| %kartaworldreset_normal%| Show time left to reset world in long format |
| %kartaworldreset_nextReset%| Show reset world Date and Time          |
| %kartaworldreset_short% | Show time left to reset world in short format |
