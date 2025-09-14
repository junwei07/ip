package miro.command;

import java.time.LocalDate;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.EventTask;
import miro.task.Task;
import miro.utils.Utils;

/**
 * Represents a command to add an event task.
 */
public class AddEventCommand extends Command {
    private final String[] words;

    public AddEventCommand(String[] words) {
        this.words = words;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws MiroException {
        boolean hasFrom = false;
        boolean hasTo = false;
        boolean isFrom = false;
        boolean isTo = false;

        StringBuilder taskSb = new StringBuilder();
        StringBuilder fromSb = new StringBuilder();
        StringBuilder toSb = new StringBuilder();

        for (int i = 1; i < words.length; i++) {
            if (isFrom && !words[i].equals("/to")) {
                fromSb.append(words[i]);
                fromSb.append(" ");

            } else if (isTo) {
                toSb.append(words[i]);
                toSb.append(" ");
            } else {
                if (!words[i].equals("/from") && !words[i].equals("/to")) {
                    taskSb.append(words[i]);
                    taskSb.append(" ");
                }
            }

            if (words[i].equals("/from")) {
                hasFrom = true;
                isFrom = true;
            } else if (words[i].equals("/to")) {
                hasTo = true;
                isFrom = false;
                isTo = true;
            }
        }
        String inputFromDate = fromSb.toString().strip();
        String inputToDate = toSb.toString().strip();

        if (!hasTo || !hasFrom) {
            throw new MiroException("Please specify dates using \"/from ... /to ...\"");
        } else if (taskSb.isEmpty()) {
            throw new MiroException("Task description cannot be empty.");
        } else {
            Utils.isValidToFromDates(inputFromDate, inputToDate);
        }

        Task task = new EventTask(taskSb.toString().strip(),
                                LocalDate.parse(inputFromDate),
                                LocalDate.parse(inputToDate));

        taskList.add(task);
        storage.save(taskList.getTaskList());
        return ui.addTaskSuccess(task, taskList.size());

    }
}
