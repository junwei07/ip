
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        storage.save(taskList);
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

                // check if date is specified
                if (isDate && !sb.toString().isEmpty()) {
                    task = new DeadlineTask(sb.toString().strip(), dateSb.toString().strip());
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

                if (hasFrom && hasTo) {
                    task = new EventTask(sb.toString().strip(), fromSb.toString().strip(), toSb.toString().strip());
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
    }

    private void unmarkTask(Task task) {
        task.unmark();
        Hbar();
        space();
        System.out.println("Ok, I've unmarked this task");
        space();
        System.out.println(task);
        Hbar();
    }
    public static void main(String[] args) {
        new Miro().run();
    }
}
