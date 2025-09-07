package miro.command;

import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

/**
 * Represents a command to mark a task.
 */
public class MarkTaskCommand extends Command {
    private final Task task;

    public MarkTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        task.mark();
        storage.save(taskList.getTaskList());
        return ui.markTask(task);
    }
}
