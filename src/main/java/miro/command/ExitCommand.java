package miro.command;

import java.util.ArrayList;

import miro.exception.MiroException;
import miro.main.Storage;
import miro.main.TaskList;
import miro.main.Ui;
import miro.task.Task;

public class ExitCommand extends Command {

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.output("Goodbye. Hope to see you again!");
    }
}
