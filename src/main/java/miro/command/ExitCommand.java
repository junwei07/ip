package miro.command;

import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;

/**
 * Represents a command to exit the program.
 */
public class ExitCommand extends Command {

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.output("Goodbye. Hope to see you again!");
    }
}
