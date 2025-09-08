package miro.command;

import java.time.LocalDate;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.DeadlineTask;
import miro.task.Task;
import miro.utils.Utils;

/**
 * Represents a command to add a deadline task.
 */
public class AddDeadlineCommand extends Command {
    private final String[] words;

    public AddDeadlineCommand(String[] words) {
        this.words = words;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws MiroException {

        String[] params = Utils.getDeadlineParams(words);
        String description = params[0];
        String date = params[1];

        Task task = new DeadlineTask(description, LocalDate.parse(date));
        taskList.add(task);
        storage.save(taskList.getTaskList());
        return ui.addTaskSuccess(task, taskList.size());
    }

}
