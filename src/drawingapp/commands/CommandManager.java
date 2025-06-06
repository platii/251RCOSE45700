package drawingapp.commands;

import java.util.*;

public class CommandManager {
    List<Command> undoCommand = new ArrayList<>();
    List<Command> redoCommand = new ArrayList<>();

    public void executeCommand(Command command) {
        command.execute();
        undoCommand.add(command);
        redoCommand.clear();
    }

    public void undo() {
        if (undoCommand.isEmpty()) {
            return;
        }
        Command command = undoCommand.remove(undoCommand.size() - 1);
        command.undo();
        redoCommand.add(command);
    }

    public void redo() {
        if (redoCommand.isEmpty()) {
            return;
        }
        Command command = redoCommand.remove(redoCommand.size() - 1);
        command.execute();
        undoCommand.add(command);
    }
}
