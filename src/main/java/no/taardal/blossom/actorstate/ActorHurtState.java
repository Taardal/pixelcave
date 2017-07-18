package no.taardal.blossom.actorstate;

import no.taardal.blossom.actor.Actor;

public abstract class ActorHurtState<T extends Actor, E extends Actor> implements ActorState {

    protected T actor;
    protected E attacker;

    public ActorHurtState(T actor, E attacker) {
        this.actor = actor;
        this.attacker = attacker;
    }

    @Override
    public void onEntry() {
        actor.setHealth(actor.getHealth() - attacker.getDamage());
    }

    @Override
    public void onExit() {
        getAnimation().reset();
    }
}
