package no.taardal.blossom.command;

import no.taardal.blossom.actor.Actor;

public interface Command {

    void execute(Actor actor);

}
