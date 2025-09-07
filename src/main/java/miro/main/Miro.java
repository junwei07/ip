package miro.main;

import java.util.Scanner;

public class Miro {
    private final Scanner sc = new Scanner(System.in);
    private TaskList taskList;
    private final Ui ui;
    private final Parser parser;
    private String miroResponse;

    public Miro() {
        this.ui = new Ui();
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

    private void run(String input) {
            String[] words = input.split(" ");
            miroResponse = parser.parse(words);
    }

    public String greet() {
        return ui.greet();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        run(input);
        return miroResponse;
    }
}
