# Rogue - Savage Rats

**Read the [MANUAL](http://goo.gl/AU0IdI) if you want to play the game.**


NOTE: Master has experimental code
----------------------------------

The Master branch holds Rogue based on the new GameCore implementation (see "dependencies" below for link).

The latest **stable** version is in the branch `v5stable`. That branch is stable, debugged and has no dependencies. It's a standalone Eclipse project.

You can use `v5stable` to see the original source and try to build it, but further development of that branch is stopped. The master means the future.


DEPENDENCIES
------------

If you intend to **build it from source**, you will need those Eclipse projects in your workspace:

- [MightyPork/gamecore](https://github.com/MightyPork/gamecore) - The "GameCore" game engine
- [MightyPork/gamecore-lwjgl](https://github.com/MightyPork/gamecore-lwjgl) - LWJGL backend for GameCore
- [MightyPork/mightyutils](https://github.com/MightyPork/mightyutils) - Game utils

---

The following is the original readme, applicable to `v5stable` version.


## Description

### Goals

- Simple retro-themed dungeon crawler


### Code Features

- Full OOP design
- Event driven
- OpenGL 2D rendering
- Screen / layer based graphics with Constraint System
- A* path-finding system
- Audio, Font & Texture systems
- Easily extensible base framework


### Game Features

- Real-time action
- Monsters with AI
- Collectable items (weapons, food)
- Random floor generator


### Used libraries

- LWJGL (OpenGL & OpenAL support)
- SlickUtil (texture loader, audio system)


## BUILDING FROM SOURCE

1. Export a jar with the compiled sources and /res into `build/in/build.jar`
2. Run `make` to create a stand-alone executable jar in `build/out/release.jar`
3. Use `make run` to execute it



## Usage

The game is controlled by mouse and keyboard.

See the manual (link at the top) for more detailed info.
