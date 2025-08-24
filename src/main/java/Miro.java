
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.time.LocalDate;

public class Miro {
    private boolean isRunning;
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Task> taskList;
    private Storage storage;
    private final String filepath = "./data/duke.txt";

    public Miro() {
        this.isRunning = true;

        this.storage = new Storage(filepath);

        try {
            this.taskList = storage.load();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid task type.");
        }

    }

    private void Hbar() {
        space();
        System.out.println("───────────────────────────────");
    }
    private void run() {
        Hbar();
        greet();
        while (isRunning) {
            String input = sc.nextLine().strip().toLowerCase();
            String[] words = input.split(" ");
            if (input.equals("list")) {
                getTasks();
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
                            space();
                            System.out.println("Invalid command!");
                            Hbar();
                        }
                    } catch (NumberFormatException e) {
                        if (words[0].equals("mark") || words[0].equals("unmark")) {
                            space();
                            System.out.println("Invalid command!");
                            Hbar();
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
                        output("Invalid command!");
                    }
                }
            } else if (input.equals("bye")) {
                break;
            } else {
                addTask(words);
            }
        }
        exit();
    }

    private void space() {
        System.out.print("    ");
    }

    private void greet() {
        space();
        System.out.println("Hello! I'm Miro.");
        space();
        System.out.println("What can I do for you?");
        Hbar();
    };

    private void exit() {
        output("GoodBye. Hope to See you again!");
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
                        output("Task description cannot be empty.");
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
                    output("Please specify a date using \"/by ...\"");
                } else {
                    output("Task description cannot be empty.");
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
                        output("Invalid date. Date should be in format 'YYYY-MM-DD'");
                    }
                } else {
                    output("Please specify dates using \"/from ... /to ...\"");
                }
            } else {
                invalidMsg();
            }


        if (task != null) {
            this.taskList.add(task);
            Hbar();
            space();
            System.out.println("Got it. I've added this task:");
            space();
            System.out.printf("%s\n", task);
            space();
            System.out.printf("Now you have %d tasks in the list.\n", taskList.size());
            Hbar();
        }
        storage.save(taskList);
    }

    private void deleteTask(int index) {
        if (index >= 0 && index < taskList.size()) {

            Task task = taskList.get(index);
            taskList.remove(index);
            Hbar();
            space();
            System.out.println("Noted. I've removed this task from the list:");
            space();
            System.out.println(task);
            Hbar();
        } else {
            output("Invalid command!");
        }

        storage.save(taskList);
    }
    private void output(String message) {
        Hbar();
        space();
        System.out.println(message);
        Hbar();
    }
    private void invalidMsg() {
        Hbar();
        space();
        System.out.println("Oops! This is an invalid task.");
        Hbar();
    }

    private void getTasks() {
        space();
        System.out.println("Here are the list of tasks:");
        for (int i = 0; i < taskList.size(); i++) {

            space();
            System.out.printf("%d. %s\n", i + 1, taskList.get(i).toString());
        }
        Hbar();
    }
    private void markTask(Task task) {
        task.mark();
        Hbar();
        space();
        System.out.println("Good job! I've marked this task as done");
        space();
        System.out.println(task);
        Hbar();

        storage.save(taskList);
    }

    private void unmarkTask(Task task) {
        task.unmark();
        Hbar();
        space();
        System.out.println("Ok, I've unmarked this task");
        space();
        System.out.println(task);
        Hbar();

        storage.save(taskList);
    }

    private boolean isValidDate(String input) {
        try {
            LocalDate inputDate = LocalDate.parse(input);
            if (!inputDate.isBefore(LocalDate.now())) {
                return true;
            } else {
                output("Date cannot be in the past.");
            }
        } catch (DateTimeParseException e) {
            output("Invalid date. Date must be in format 'YYYY-MM-DD'.");
        }

        return false;
    }

    public static void main(String[] args) {
        new Miro().run();
    }
}
