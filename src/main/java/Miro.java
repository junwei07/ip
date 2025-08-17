import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Miro {
    private boolean isRunning;
    private Scanner sc = new Scanner(System.in);
    private List<String> taskList = new ArrayList<>();

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
            if (input.equals("list")){
                getTasks();
            } else if (input.equals("bye")) {
                break;
            } else {
                addTask(input);
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

    private void addTask(String task) {
        this.taskList.add(task);
        Hbar();
        space();
        System.out.printf("Added: %s\n", task);
        Hbar();
    }
    private void getTasks() {
        for (int i = 0; i < taskList.size(); i++) {
            space();
            System.out.printf("%d. %s\n", i + 1, taskList.get(i));
        }
        Hbar();
    }
    public static void main(String[] args) {
        new Miro().run();
    }
}
