package com.command;

import lombok.Getter;

@Getter
public enum Actions {
    CREATE ("Create vehicle", new Create()),
    PRINT ("Print vehicle", new Print()),
    UPDATE ("Update vehicle", new Update()),
    REMOVE ("Remove vehicle", new Remove()),
    EXIT ("Exit", null);

    private final String name;
    private final Command command;
    Actions(String name, Command command) {
        this.name = name;
        this.command = command;
    }
    public Command execute() {
        if (command != null) {
            command.execute();
        }
        return command;
    }
}
