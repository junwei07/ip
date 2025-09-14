package miro.main;

import miro.exception.MiroException;

/**
 * Represents a Miro chatbot.
 * A <code>taskList</code> object corresponds to a list of tasks.
 * A <code>Ui</code> object corresponds to the UI of the app.
 * A <code>Parser</code> object corresponds to the parser of user input.
 * A <code>Storage</code> object corresponds the storage to load/ save the
 * task list.
 */
public class Miro {
    private TaskList taskList;
    private final Ui ui;
    private final Parser parser;
    private String miroResponse;


    /**
     * Initializes a Miro chatbot instance.
     */
    public Miro() {
        this.ui = new Ui();
        String filepath = "./data/miro.txt";
        Storage storage = new Storage(filepath);

        try {
            this.taskList = new TaskList(storage.load());
        } catch (IllegalArgumentException e) {
            this.taskList = new TaskList();
            ui.output("Invalid task type found.");
        }

        this.parser = new Parser(taskList, ui, storage);

    }

    private void run(String input) throws MiroException {
        String[] words = input.split(" ");
        miroResponse = parser.parse(words);
    }

    public String greet() {
        return ui.greet();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) throws MiroException {
        run(input);
        return miroResponse;
    }
}
