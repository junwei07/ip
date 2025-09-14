package miro.main;

import miro.command.AddDeadlineCommand;
import miro.command.AddEventCommand;
import miro.command.AddToDoCommand;
import miro.command.Command;
import miro.command.DeleteTaskCommand;
import miro.command.ExitCommand;
import miro.command.FindTaskCommand;
import miro.command.GetTasksCommand;
import miro.command.MarkTaskCommand;
import miro.command.UnmarkTaskCommand;
import miro.command.UpdateTaskCommand;
import miro.exception.MiroException;
import miro.task.Task;

/**
 * Represents a parser to parse user input.
 * A <code>taskList</code> object corresponds to a list of tasks.
 * A <code>Ui</code> object corresponds to the UI of the app.
 * A <code>Storage</code> object corresponds the storage to load/ save the
 * task list.
 */
public class Parser {
    private final TaskList taskList;
    private final Ui ui;
    private final Storage storage;


    /**
     * Represents a parser to parse user input.
     * A <code>taskList</code> object corresponds to a list of tasks.
     * A <code>Ui</code> object corresponds to the UI of the app.
     * A <code>Storage</code> object corresponds the storage to load/ save the
     * task list.
     */
    public Parser(TaskList taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Parses the array of words from user input, creates the
     * command and executes the command.
     *
     * @param words The array of words from user input.
     *
     * @return A <code>String</code> that indicates the response of
     *      the chatbot.
     */
    public String parse(String[] words) throws MiroException {
        Command command;
        int taskNum;

        switch (words[0]) {
        case "list":
            command = new GetTasksCommand();
            return command.execute(taskList, ui, storage);

        case "mark", "unmark":

            taskNum = getValidTaskNum(words, taskList);
            Task task = taskList.get(taskNum - 1);

            if (words[0].equals("mark")) {
                command = new MarkTaskCommand(task);
            } else {
                command = new UnmarkTaskCommand(task);
            }

            return command.execute(taskList, ui, storage);

        case "find":
            if (words.length != 2) {
                throw new MiroException("Please input one keyword to search.");
            }

            command = new FindTaskCommand(words[1]);
            return command.execute(taskList, ui, storage);

        case "delete":
            taskNum = getValidTaskNum(words, taskList);
            command = new DeleteTaskCommand(taskNum - 1);
            return command.execute(taskList, ui, storage);

        case "update":
            taskNum = getValidUpdateTaskNum(words, taskList);
            command = new UpdateTaskCommand(taskNum - 1, words);
            return command.execute(taskList, ui, storage);

        case "bye":
            command = new ExitCommand();
            return command.execute(taskList, ui, storage);

        default:
            return addTask(words);

        }
    }

    /**
     * Adds ToDo, Deadline, Event tasks to the task list.
     *
     * @param words The array of words from user input.
     *
     * @return A <code>String</code> that indicates the response of
     *      the chatbot.
     */
    private String addTask(String[] words) throws MiroException {
        Command command;

        switch (words[0]) {
        case "todo":
            command = new AddToDoCommand(words);
            return command.execute(taskList, ui, storage);

        case "deadline":
            command = new AddDeadlineCommand(words);
            return command.execute(taskList, ui, storage);

        case "event":
            command = new AddEventCommand(words);
            return command.execute(taskList, ui, storage);

        default:
            throw new MiroException("Oops! This is an invalid task.");
        }
    }

    /**
     * Validates the task number given by the user.
     *
     * @param taskNumInput The task number from user input.
     * @param taskListSize The size of task list.
     *
     * @return A <code>boolean</code> that indicates if the task number
     *      is valid.
     */
    private boolean isValidTaskNum(String taskNumInput, int taskListSize) throws MiroException {
        try {
            int taskNum = Integer.parseInt(taskNumInput);
            return taskNum > 0 && taskNum <= taskListSize;
        } catch (NumberFormatException e) {
            throw new MiroException("Task number must be an integer!");
        }
    }

    /**
     * Returns the validated task number given by the user.
     *
     * @param words The user input.
     * @param taskList The current task list.
     *
     * @return An <code>int</code> that indicates the valid task number.
     */
    private int getValidTaskNum(String[] words, TaskList taskList) throws MiroException {
        if (words.length != 2) {
            throw new MiroException("Input must be of length 2!");
        } else if (!isValidTaskNum(words[1], taskList.size())) {
            throw new MiroException("Task number is out of range!");
        }

        return Integer.parseInt(words[1]);
    }

    private int getValidUpdateTaskNum(String[] words, TaskList taskList) throws MiroException {
        if (!isValidTaskNum(words[1], taskList.size())) {
            throw new MiroException("Task number is out of range!");
        }

        return Integer.parseInt(words[1]);
    }
}
