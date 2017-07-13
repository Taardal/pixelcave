package no.taardal.blossom.state.actorstate;

import no.taardal.blossom.entity.Actor;
import no.taardal.blossom.vector.Vector2d;
import no.taardal.blossom.world.World;

public class PlayerJumpingState extends PlayerFallingState {

    public PlayerJumpingState(Actor actor, World world) {
        super(actor, world);
    }

    @Override
    public void onEntry() {
        super.onEntry();
        actor.setVelocity(new Vector2d(0, -200));
    }

}
