package miro.command;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;

public class GetTasksCommand extends Command {
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws MiroException {
        return ui.printTaskList(taskList.getTaskList());
    }
}
