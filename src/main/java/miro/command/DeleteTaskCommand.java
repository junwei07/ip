package miro.command;

import java.util.ArrayList;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

public class DeleteTaskCommand extends Command {
    private final int index;

    public DeleteTaskCommand(int index) {
        this.index  = index;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        Task task = taskList.get(index);
        taskList.delete(index);
        storage.save(taskList.getTaskList());
        return ui.deleteTaskSuccess(task);
    }
}
