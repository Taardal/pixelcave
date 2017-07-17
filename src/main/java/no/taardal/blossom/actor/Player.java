package no.taardal.blossom.actor;

import no.taardal.blossom.camera.Camera;
import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.actorstate.playerstate.PlayerFallingState;
import no.taardal.blossom.actorstate.playerstate.PlayerState;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Player extends Actor {

    public static final SpriteSheet SPRITE_SHEET = new SpriteSheetBuilder()
            .directory("knight")
            .fileName("spritesheet-knight-red.png")
            .spriteWidth(40)
            .spriteHeight(40)
            .build();

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public Player(World world) {
        super(world);
        position = new Vector2d(200, 50);
        velocity = Vector2d.zero();
        direction = Direction.EAST;
        pushState(new PlayerFallingState(this, world));
    }

    @Override
    public void draw(Camera camera) {
        if (direction == Direction.WEST) {
            getAnimation().drawFlippedHorizontally(this, camera);
        } else {
            super.draw(camera);
        }
    }

    public void handleInput(Keyboard keyboard) {
        if (!states.isEmpty()) {
            PlayerState playerState = (PlayerState) states.getFirst();
            playerState.handleInput(keyboard);
        }
    }

}
