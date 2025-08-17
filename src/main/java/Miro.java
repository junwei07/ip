public class Miro {
    private boolean isRunning;

    public Miro() {
        this.isRunning = true;
    }

    private void Hbar() {
        System.out.println("───────────────────────────────");
    }
    private void run() {
        Hbar();
        greet();
        while (isRunning) {
        }
        exit();
    }

    private void greet() {
        System.out.println("Hello I'm Miro\nWhat can I do for you?\n");
        Hbar();
        this.isRunning = false;
    };

    private void exit() {
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
        System.out.println("Hello I'm Miro\nWhat can I do for you?");
    }
}
