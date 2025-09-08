package miro.command;

import java.util.List;

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

        List<Task> filteredTasks = taskList.getTaskList().stream()
                .filter(task -> task.getDescription().contains(keyword))
                .toList();

        return ui.searchedTasks(filteredTasks);
    }
}
