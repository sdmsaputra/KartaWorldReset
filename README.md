# Endworldreset-Plugin
Reset World The End automaticaly in periodically.

## Feature
- Schedule the world end reset
- Support PlaceholderAPI
- Support Multi World
- Set default lobby
- Easy to use

## Usage
```
[Usages]:
/endworldreset reload           Reload config plugin (Admin)
/endworldreset autogen          Auto generate config (Admin)
/endworldreset info             Show time left
/endworldreset info setting     Show config info (Admin)
/endworldreset info worldlist   Show world reset list (Admin)
/endworldreset info clock       Show system clock
/endworldreset papi reload      Reload placeholder expansion (Admin)
```

## Default Config
config.yml :
```
Config :
  # d=day w=week m=month
  every : 1w
  time : "00:00"

Save :
  # u can keep it null while auto-generate
  # u can specify by yourself use format dd-MM-yyyy
  nextReset : null

Worlds :
  - world_the_end

Lobby :
  # set null for teleport to bed or main world.
  - world
```

## Permissions
| Permission          | Description                  |
|---------------------|-------------------------------|
| endworldreset.admin | Give permission to control plugin setting |


## PlaceholderAPI
| key                 | Description                   |
|---------------------|-------------------------------|
| %endworldreset_normal%     | Show time left to reset world in long format |
| %endworldreset_nextReset%  | Show reset world Date Time                |
| %endworldreset_short%      | Show time left to reset world in short format |
