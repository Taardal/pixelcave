package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.actor.Player;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerJumpingState extends PlayerFallingState {

    public PlayerJumpingState(Player player, World world) {
        super(player, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setVelocity(new Vector2d(0, -200));
    }

}
