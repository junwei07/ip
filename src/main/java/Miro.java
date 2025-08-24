import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import java.util.Scanner;
import java.time.LocalDate;

public class Miro {
    private boolean isExit;
    private final Scanner sc = new Scanner(System.in);
    private TaskList taskList;
    private final Ui ui;
    private final Parser parser;

    public Miro() {
        this.ui = new Ui();
        this.isExit = false;
        String filepath = "./data/duke.txt";
        Storage storage = new Storage(filepath);

        try {
            this.taskList = new TaskList(storage.load());
        } catch (IllegalArgumentException e) {
            this.taskList = new TaskList();
            ui.output("Invalid task type found.");
        }

        this.parser = new Parser(taskList, ui, storage);

    }

    private void run() {
        ui.greet();
        while (!isExit) {
            String input = sc.nextLine().strip().toLowerCase();
            String[] words = input.split(" ");
            isExit = parser.parse(words);
        }
    }

    public static void main(String[] args) {
        new Miro().run();
    }
}
