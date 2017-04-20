package no.taardal.blossom.command;

import no.taardal.blossom.entity.Actor;

public interface Command {

    void execute(Actor actor);

}
