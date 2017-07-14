package no.taardal.blossom.actor;

import no.taardal.blossom.direction.Direction;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.sprite.SpriteSheet;
import no.taardal.blossom.sprite.SpriteSheetBuilder;
import no.taardal.blossom.state.actorstate.PlayerFallingState;
import no.taardal.blossom.state.actorstate.PlayerState;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Player extends Actor {

    public static final SpriteSheet SPRITE_SHEET = new SpriteSheetBuilder().directory("scorpion").fileName("scorpion-black-sheet-x1.png").spriteWidth(16).spriteHeight(16).build();

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public Player(World world) {
        super(world);
        direction = Direction.EAST;
        position = new Vector2d(250, 50);
        actorState = new PlayerFallingState(this, world);
        actorState.onEntry();
    }

    public void handleInput(Keyboard keyboard) {
        PlayerState playerState = ((PlayerState) actorState).handleInput(keyboard);
        if (playerState != null) {
            LOGGER.debug("New actor state [{}]", playerState.toString());
            playerState.onEntry();
            actorState = playerState;
        }
    }

}
