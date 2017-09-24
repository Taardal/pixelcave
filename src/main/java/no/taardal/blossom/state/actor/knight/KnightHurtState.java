package no.taardal.blossom.state.actor.knight;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.actor.Knight;
import no.taardal.blossom.keyboard.Keyboard;
import no.taardal.blossom.state.actor.ActorHurtState;
import no.taardal.blossom.state.actor.PlayerState;

public class KnightHurtState extends ActorHurtState<Knight, Actor> implements PlayerState {

    public KnightHurtState(Knight actor, Actor attacker) {
        super(actor, attacker);
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        if (actor.getHealth() <= 0) {
            actor.pushState(new KnightDeadState(actor));
        } else {
            getAnimation().update();
            if (getAnimation().isFinished()) {
                actor.popState();
            }
        }
    }

    @Override
    public void handleInput(Keyboard keyboard) {

    }

    @Override
    protected void updateBounds() {

    }

}
