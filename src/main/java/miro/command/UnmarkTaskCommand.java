package miro.command;

import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

/**
 * Represents a command to unmark a task.
 */
public class UnmarkTaskCommand extends Command {
    private final Task task;

    public UnmarkTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        task.unmark();
        storage.save(taskList.getTaskList());
        return ui.unmarkTask(task);
    }
}
