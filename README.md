# Rogue - Savage Rats

**Read the [MANULAL](http://goo.gl/AU0IdI) if you want to play the game.**

It explains it way better than this text file.

---

The package `mightypork.gamecore` contains the framework part of the project.

It is not yet ready to be published on it's own, but you can have a look at the 
source if you're interested. The actual library repo will be set up later, with 
more polished code, better docs, and cleaner hierarchy.

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

1. Export a jar with the `mightypork.*` packages and the `res` folder into 
`build/in/build.jar`
2. Run `make` to create a stand-alone executable jar in `build/out/release.jar`
3. Use `make run` to execute it



## Usage

The game is controlled by mouse and keyboard.

See the manual (link at the top) for more detailed info.
