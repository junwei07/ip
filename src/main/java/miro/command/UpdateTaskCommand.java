package miro.command;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

/**
 * Represents a command to update a task.
 */
public class UpdateTaskCommand extends Command {
    private int taskNum;
    private String[] words;

    /**
     * Constructor for UpdateTaskCommand.
     *
     * @param taskNum The index of the task in task list.
     * @param words   The user input.
     */
    public UpdateTaskCommand(int taskNum, String[] words) {
        this.taskNum = taskNum;
        this.words = words;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws MiroException {

        Task task = taskList.get(taskNum);

        task.update(words);
        storage.save(taskList.getTaskList());

        return ui.updatedTaskSuccess(task);
    }
}
