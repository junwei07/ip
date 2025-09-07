package miro.command;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;
import miro.task.ToDoTask;

public class AddToDoCommand extends Command {
    private final String[] words;

    public AddToDoCommand(String[] words) {
        this.words = words;
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws MiroException {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < words.length; i++) {
            sb.append(words[i]);
            sb.append(" ");
        }

        if (!sb.toString().isEmpty()) {
            Task task = new ToDoTask(sb.toString().strip());
            taskList.add(task);
            storage.save(taskList.getTaskList());

            return ui.addTaskSuccess(task, taskList.size());

        } else {
            throw new MiroException("Task description cannot be empty.");
        }
    }
}
