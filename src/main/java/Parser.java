import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser {
    private ArrayList<Task> taskList;
    private Ui ui;
    private Storage storage;

    public Parser(ArrayList<Task> taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    public boolean parse(String[] words) {
        if (words[0].equals("list")) {
            ui.printTaskList(taskList);
        } else if (words[0].equals("mark") || words[0].equals("unmark")) {
            if (words.length == 2) {
                try {
                    int taskNum = Integer.parseInt(words[1]);

                    if (taskNum > 0 && taskNum <= taskList.size()) {
                        Task task = taskList.get(taskNum - 1);
                        if (words[0].equals("mark")) {
                            markTask(task);
                        } else {
                            unmarkTask(task);
                        }
                    } else {
                        ui.output("Invalid command!");
                    }
                } catch (NumberFormatException e) {
                    if (words[0].equals("mark") || words[0].equals("unmark")) {
                        ui.output("Invalid command!");
                    } else {
                        addTask(words);
                    }
                }
            }
        } else if (words[0].equals("todo")) {
            addTask(words);
        } else if (words[0].equals("delete")) {
            if (words.length == 2) {
                try {
                    int taskNum  = Integer.parseInt(words[1]);
                    deleteTask(taskNum - 1);
                } catch (NumberFormatException e) {
                    ui.output("Invalid command!");
                }
            }
        } else if (words[0].equals("bye")) {
            exit();
            return true;
        } else {
            addTask(words);
        }
        return false;
    }

    private void exit() {
        ui.output("GoodBye. Hope to See you again!");
    }

    private void addTask(String[] words) {
        Task task = null;
        StringBuilder sb = new StringBuilder();

        if (words[0].equals("todo")) {

            for (int i = 1; i < words.length; i++) {
                sb.append(words[i]);
                sb.append(" ");
            }

            if (!sb.toString().isEmpty()) {
                task = new ToDoTask(sb.toString().strip());
            } else {
                ui.output("Task description cannot be empty.");
            }
        } else if (words[0].equals("deadline")) {
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
                ui.output("Please specify a date using \"/by ...\"");
            } else {
                ui.output("Task description cannot be empty.");
            }
        } else if (words[0].equals("event")) {
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
                    ui.output("Invalid date. Date should be in format 'YYYY-MM-DD'");
                }
            } else {
                ui.output("Please specify dates using \"/from ... /to ...\"");
            }
        } else {
            ui.output("Oops! This is an invalid task.");
        }


        if (task != null) {
            this.taskList.add(task);
            ui.addTaskSuccess(task, taskList.size());
        }
        storage.save(taskList);
    }

    private void deleteTask(int index) {
        if (index >= 0 && index < taskList.size()) {

            Task task = taskList.get(index);
            taskList.remove(index);
            ui.deleteTaskSuccess(task);
        } else {
            ui.output("Invalid command!");
        }

        storage.save(taskList);
    }

    private void markTask(Task task) {
        task.mark();
        ui.markTask(task);
        storage.save(taskList);
    }

    private void unmarkTask(Task task) {
        task.unmark();
        ui.unmarkTask(task);
        storage.save(taskList);
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
