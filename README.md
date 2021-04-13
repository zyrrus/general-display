# general-display
A java GUI wrapped into an easy-to-use, inheritable class 

## How to use:
1. Create a new class that extends Display and implements Displayable
2. Add appropriate constructor and methods
3. Create an instance of the class in main

TestDisplay is an example

## Available methods:
- init() - called once on start
- fixedUpdate() - called at X fps
- update() - called every frame
- draw(Graphics g) - called every frame

## Available flags:
- antialiasingON - enables antialiaing (true by default)
- showFPS - shows FPS in the window title (true by default)
- isResizable - enables resizing the window (false by default)
- isAlwaysOnTop - keep the window on top of all other windows (true by default)
