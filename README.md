# Pixelcave

<div float="left">
  <img src="./img/pixelcave.gif" width="800" />
</div>

# Description

2D platformer demo game written from scratch in Java.

This is a very small demo/prototype game that simply consists of a parallax background, environment tiles and a single character that can run, jump and collide with the environment. There are no enemies and no UI.

Main features of the code:

- Software rendering
- Sprite sheet animation
- Collision detection
- State machine

# Prerequisites&nbsp;:vertical_traffic_light:

- [Git][git:download]
- [Java 8][java:download]
- [Maven][maven:download]

# Getting started&nbsp;:runner:

### Getting the code&nbsp;:octocat:

- Clone the repository: `git clone https://github.com/taardal/pixelcave`

### Running the app&nbsp;:rocket:

**Script**

- Make run-script executable: `chmod +x run.sh`
- Run the script: `sh run.sh`

**Manual**

- Build the app: `mvn clean package`
- Run the app: `java -jar target/pixelcave-jar-with-dependencies.jar`

# Resources&nbsp;:books:

### Tools

- [Tiled Level Editor][tiled]

### Assets

- [Itch.io: Nami Pixels][itchio:namipixels]

### Knowledge

- [TheCherno: Game Programming][res:yt:thecherno:gameprogramming] (YouTube)
- [Game Programming Patterns: Game Loop][res:gameprogrammingpatterns:gameloop] (Book)
- [Build a 2D platformer - JAVA][res:brainycode:2dplatformer] (Book)
- [The Guide to Implementing 2D platformers][res:higherorderfun:2dplatformer] (Blog)


[git:download]: https://git-scm.com/downloads
[java:download]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[maven:download]: https://maven.apache.org/download.cgi
[res:brainycode:2dplatformer]: http://www.brainycode.com/downloads/Building2DPlatformerV10.pdf
[res:higherorderfun:2dplatformer]: http://higherorderfun.com/blog/2012/05/20/the-guide-to-implementing-2d-platformers/
[res:gameprogrammingpatterns:gameloop]: http://gameprogrammingpatterns.com/game-loop.html
[res:yt:thecherno:gameprogramming]: https://www.youtube.com/watch?v=GFYT7Lqt1h8&list=PLlrATfBNZ98eOOCk2fOFg7Qg5yoQfFAdf&ab_channel=TheCherno
[tiled]: https://www.mapeditor.org/
[itchio:namipixels]: https://namipixels.itch.io/