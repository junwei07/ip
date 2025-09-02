package miro.main;

import miro.task.DeadlineTask;
import miro.task.EventTask;
import miro.task.Task;
import miro.task.ToDoTask;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

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

    public Parser(TaskList taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Parses the array of words from user input and carries out
     * the corresponding tasks.
     *
     * @param words The array of words from user input.
     *
     * @return A boolean value to indicate whether to exit the program.
     */
    public String parse(String[] words) {
        switch (words[0]) {
            case "list" -> {
                return ui.printTaskList(taskList.getTaskList());
            }
            case "mark", "unmark" -> {
                if (words.length == 2) {
                    try {
                        int taskNum = Integer.parseInt(words[1]);

                        if (taskNum > 0 && taskNum <= taskList.size()) {
                            Task task = taskList.get(taskNum - 1);
                            if (words[0].equals("mark")) {
                                return markTask(task);
                            } else {
                                return unmarkTask(task);
                            }
                        } else {
                            return ui.output("Invalid command!");
                        }
                    } catch (NumberFormatException e) {
                        if (words[0].equals("mark") || words[0].equals("unmark")) {
                            return ui.output("Invalid command!");
                        } else {
                            return addTask(words);
                        }
                    }
                }
            }
            case "find" -> {
                if (words.length == 2) {
                    return searchTask(words[1]);
                } else {
                    return ui.output("Please input one keyword to search.");
                }
            }
            case "delete" -> {
                if (words.length == 2) {
                    try {
                        int taskNum = Integer.parseInt(words[1]);
                        return deleteTask(taskNum - 1);
                    } catch (NumberFormatException e) {
                        return ui.output("Invalid command!");
                    }
                }
            }
            case "bye" -> {
                return ui.output("GoodBye. Hope to See you again!");
            }
            default -> {
                return addTask(words);
            }
        }
        return ui.output("Please input something!");
    }

    private String addTask(String[] words) {
        Task task = null;
        StringBuilder sb = new StringBuilder();

        switch (words[0]) {
            case "todo" -> {

                for (int i = 1; i < words.length; i++) {
                    sb.append(words[i]);
                    sb.append(" ");
                }

                if (!sb.toString().isEmpty()) {
                    task = new ToDoTask(sb.toString().strip());
                } else {
                    return ui.output("Task description cannot be empty.");
                }
            }
            case "deadline" -> {
                // find date or time
                boolean isDate = false;
                StringBuilder dateSb = new StringBuilder();
                for (int i = 1; i < words.length; i++) {
                    if (isDate) {
                        dateSb.append(words[i]);
                        dateSb.append(" ");
                    } else {
                        if (words[i].equals("/by")) {
                            isDate = true;
                        } else {
                            sb.append(words[i]);
                            sb.append(" ");
                        }
                    }
                }
                String inputDate = dateSb.toString().strip();

                // check if date is specified
                if (isDate && !inputDate.isEmpty()) {
                    // check valid date and time
                    if (isValidDate(inputDate)) {
                        task = new DeadlineTask(sb.toString().strip(), LocalDate.parse(inputDate));
                    }
                } else if (!isDate) {
                    return ui.output("Please specify a date using \"/by ...\"");
                } else {
                    return ui.output("Task description cannot be empty.");
                }
            }
            case "event" -> {
                boolean hasFrom = false;
                boolean hasTo = false;
                boolean isFrom = false;
                boolean isTo = false;

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
                            sb.append(words[i]);
                            sb.append(" ");
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

                // check if date is valid
                if (hasFrom && hasTo) {
                    if (isValidDate(inputFromDate) && isValidDate(inputToDate)) {
                        task = new EventTask(sb.toString().strip(), LocalDate.parse(inputFromDate), LocalDate.parse(inputToDate));
                    } else {
                        return ui.output("Invalid date. Date should be in format 'YYYY-MM-DD'");
                    }
                } else {
                    return ui.output("Please specify dates using \"/from ... /to ...\"");
                }
            }
            default -> {
                return ui.output("Oops! This is an invalid task.");
            }
        }


        if (task != null) {
            taskList.add(task);
            storage.save(taskList.getTaskList());

            return ui.addTaskSuccess(task, taskList.size());
        }
        return ui.output("No task to be added.");
    }

    private String deleteTask(int index) {
        if (index >= 0 && index < taskList.size()) {

            Task task = taskList.get(index);
            taskList.delete(index);
            storage.save(taskList.getTaskList());
            return ui.deleteTaskSuccess(task);
        } else {
            storage.save(taskList.getTaskList());
            return ui.output("Invalid command!");
        }

    }

    private String searchTask(String keyword) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList.getTaskList()) {
            if (task.getDescription().contains(keyword)) {
                filteredTasks.add(task);
            }
        }

        return ui.searchedTasks(filteredTasks);
    }

    private String markTask(Task task) {
        task.mark();
        storage.save(taskList.getTaskList());
        return ui.markTask(task);
    }

    private String unmarkTask(Task task) {
        task.unmark();
        storage.save(taskList.getTaskList());
        return ui.unmarkTask(task);
    }

    private boolean isValidDate(String input) {
        try {
            LocalDate inputDate = LocalDate.parse(input);
            if (!inputDate.isBefore(LocalDate.now())) {
                return true;
            } else {
                ui.output("Date cannot be in the past.");
            }
        } catch (DateTimeParseException e) {
            ui.output("Invalid date. Date must be in format 'YYYY-MM-DD'.");
        }

        return false;
    }
}
