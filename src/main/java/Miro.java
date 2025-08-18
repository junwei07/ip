import com.sun.source.util.TaskEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Miro {
    private boolean isRunning;
    private Scanner sc = new Scanner(System.in);
    private List<Task> taskList = new ArrayList<>();

    public Miro() {
        this.isRunning = true;
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
        space();
        System.out.println("GoodBye. Hope to See you again!");
        Hbar();
    }

    private void addTask(String[] words) {
        Task task;
        StringBuilder sb = new StringBuilder();

        if (words[0].equals("todo")) {
            if (words.length >= 2) {
                for (int i = 1; i < words.length; i++) {
                    sb.append(words[i]);
                }
            }
            task = new ToDoTask(sb.toString());
        } else {
            for (String word : words) {
                sb.append(word);
            }
            task = new Task(sb.toString());
        }

        this.taskList.add(task);
        Hbar();
        space();
        System.out.println("Got it. I've added this task:");
        space();
        System.out.printf("%s\n", task);
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
        System.out.println(task.toString());
        Hbar();
    }

    private void unmarkTask(Task task) {
        task.unmark();
        Hbar();
        space();
        System.out.println("Ok, I've unmarked this task");
        space();
        System.out.println(task.toString());
        Hbar();
    }
    public static void main(String[] args) {
        new Miro().run();
    }
}
