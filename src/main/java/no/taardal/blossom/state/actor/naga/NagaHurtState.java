package no.taardal.blossom.state.actor.naga;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.actor.Naga;
import no.taardal.blossom.actor.Player;
import no.taardal.blossom.state.actor.ActorHurtState;
import no.taardal.blossom.state.actor.EnemyState;

public class NagaHurtState extends ActorHurtState<Naga, Actor> implements EnemyState {

    public NagaHurtState(Naga actor, Actor attacker) {
        super(actor, attacker);
    }

    @Override
    public void update(double timeSinceLastUpdate) {
        if (actor.getHealth() <= 0) {
            actor.pushState(new NagaDeadState(actor));
        } else {
            getAnimation().update();
            if (getAnimation().isFinished()) {
                actor.popState();
            }
        }
    }

    @Override
    public void nextMove(Player player) {

    }

    @Override
    protected void updateBounds() {

    }

}
