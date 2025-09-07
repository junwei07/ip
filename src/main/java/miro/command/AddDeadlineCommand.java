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

        boolean isDate = false;
        StringBuilder taskSb = new StringBuilder();
        StringBuilder dateSb = new StringBuilder();

        for (int i = 1; i < words.length; i++) {
            if (isDate) {
                dateSb.append(words[i]);
                dateSb.append(" ");
            } else {
                if (words[i].equals("/by")) {
                    isDate = true;
                } else {
                    taskSb.append(words[i]);
                    taskSb.append(" ");
                }
            }
        }
        String inputDate = dateSb.toString().strip();

        if (!isDate || !Utils.isValidDate(inputDate)) {
            throw new MiroException("Please specify a date using \"/by ...\"");
        } else if (inputDate.isEmpty()) {
            throw new MiroException("Task description cannot be empty.");
        } else if (taskSb.isEmpty()) {
            throw new MiroException("Task description cannot be empty.");
        }

        Task task = new DeadlineTask(taskSb.toString().strip(), LocalDate.parse(inputDate));
        taskList.add(task);
        storage.save(taskList.getTaskList());
        return ui.addTaskSuccess(task, taskList.size());
    }
}
