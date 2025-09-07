package miro.command;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;

/**
 * Represents an abstract command.
 */
public abstract class Command {

    /**
     * Executes the command given the TaskList, Ui and Storage.
     *
     * @param taskList   The given TaskList.
     * @param ui      The given Ui.
     * @param storage The given Storage.
     */
    public abstract String execute(TaskList taskList, Ui ui, Storage storage) throws MiroException;
}
