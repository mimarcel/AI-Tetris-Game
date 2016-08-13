# Ai Tetris Game

This is the AI Tetris game source code and the training program attached to the game to train the AI robot players.
AI Tetris game runs in several environments: Processing, as an applet, as a stand-alone application in Linux, Mac OS X and Windows and finally, using Processing.js, it does also run in Javascript.
The training program runs in Java.

## Files Structure
<pre>
.
├── Game
│   └── src
│       ├── Genetic [5]
│       └── Tetris [1]
├── Game-pde [3]
│   └── Tetris
│       ├── applet
│       ├── application.linux
│       ├── application.macosx
│       └── application.windows
├── Game-pjs [4]
│   └── Tetris.pjs
└── generate-pde-and-pjs.php [2]
</pre>

1. The code was originally written in Java in *Game/src/Tetris* directory of this repository.

2. *generate-pde-and-pjs.php* PHP script converts the *Game/src/Tetris* Java code to Processing code in *Game-pde* and *Game-pjs* directories.

3. *Game-pde* Directory represents the Processing (https://www.processing.org/) runable version of the game. Processing does also export the game as an applet (*Game-pde/Tetris/applet*) and as a stand-alone application in Linux (*Game-pde/Tetris/application.linux*), Mac OS X (*Game-pde/Tetris/application.macosx*) and Windows (*Game-pde/Tetris/appliction.windows*).

4. The PHP script does also generate the Processing.js (http://processingjs.org/) version of the game in *Game-pjs* Directory. Using Processing.js, we can run the Processing code as Javascript, therefore the game can easily be embedded in a website. As crazy as it may sound, the Java code is converted to Javascript code. That's what Processing.js does. Since applets are getting less and less support nowadays, running the game as Javascript using the strong Processing abilities is a good idea.

5. Finally, *Game/src/Genetic* is the Java package which represents the training of the robots to play the game developed in *Game/src/Tetris* package.
