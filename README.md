# Rogue - Savage Rats

**Read the [MANUAL](http://goo.gl/AU0IdI) if you want to play the game.**

It explains it way better than this text file.

NOTE: Master is broken!
-----------------------

The master branch is used as dev now (sorry about that).

The new engine is being developed here, but it is not finished and the code is in places broken.

The rogue package is not yet updated to work with the new engine structure.

Use the `v5stable` branch to see a working source version!


DEPENDENCIES:
-------------

- [MightyPork/utils](https://github.com/MightyPork/utils)

---

The package `mightypork.gamecore` contains the framework part of the project. It wil eventually be refactored and turned into a stand-alone linkable library.

---

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
