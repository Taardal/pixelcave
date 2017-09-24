package no.taardal.blossom.state.actor;

import no.taardal.blossom.actor.Actor;
import no.taardal.blossom.sprite.Animation;

public abstract class ActorHurtState<T extends Actor, E extends Actor> implements ActorState {

    protected T actor;
    protected E attacker;

    public ActorHurtState(T actor, E attacker) {
        this.actor = actor;
        this.attacker = attacker;
    }

    @Override
    public Animation getAnimation() {
        return actor.getAnimations().get("HURT");
    }

    @Override
    public void onEntry() {
        actor.setHealth(actor.getHealth() - attacker.getDamage());
        updateBounds();
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }

    protected abstract void updateBounds();

}
