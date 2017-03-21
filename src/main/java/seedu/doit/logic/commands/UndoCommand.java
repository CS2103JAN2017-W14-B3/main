package seedu.doit.logic.commands;

import seedu.doit.commons.exceptions.EmptyTaskManagerStackException;
import seedu.doit.logic.commands.exceptions.CommandException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Task undid";
    // : %1$s";
    public static final String MESSAGE_FAILURE = "Unable to undo. There is nothing to undo.\nYou cannot undo a save, find and list.";

    // public static Command toUndo;

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.undo();
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (EmptyTaskManagerStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
