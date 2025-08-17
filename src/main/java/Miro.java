import java.util.Scanner;

public class Miro {
    private boolean isRunning;
    private Scanner sc = new Scanner(System.in);

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
            if (input.equals("bye")) {
                break;
            }
            Hbar();
            space();
            System.out.printf("%s\n", input);
            Hbar();
        }
        exit();
    }

    private void space() {
        System.out.print("    ");
    }

    private void greet() {
        space();
        System.out.println("Hello I'm Miro\n");
        space();
        System.out.println("What can I do for you?\n");
        Hbar();
    };

    private void exit() {
        space();
        System.out.println("GoodBye. Hope to See you again!\n");
        Hbar();
    }
    public static void main(String[] args) {
        new Miro().run();

//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello I'm Miro\nWhat can I do for you?");
    }
}
