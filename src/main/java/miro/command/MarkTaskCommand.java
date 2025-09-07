package miro.command;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

public class MarkTaskCommand extends Command {
    private final Task task;

    public MarkTaskCommand(Task task) {
        this.task  = task;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        task.mark();
        storage.save(taskList.getTaskList());
        return ui.markTask(task);
    }
}
