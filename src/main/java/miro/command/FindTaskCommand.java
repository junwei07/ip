package miro.command;

import java.util.ArrayList;

import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

/**
 * Represents a command to search for a task.
 */
public class FindTaskCommand extends Command {
    private final String keyword;

    public FindTaskCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {

        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList.getTaskList()) {
            if (task.getDescription().contains(keyword)) {
                filteredTasks.add(task);
            }
        }

        return ui.searchedTasks(filteredTasks);
    }
}
