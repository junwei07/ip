package miro.command;

import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

/**
 * Represents a command to delete a task.
 */
public class DeleteTaskCommand extends Command {
    private final int index;

    public DeleteTaskCommand(int index) {
        this.index = index;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        Task task = taskList.get(index);
        taskList.delete(index);
        storage.save(taskList.getTaskList());
        return ui.deleteTaskSuccess(task);
    }
}
