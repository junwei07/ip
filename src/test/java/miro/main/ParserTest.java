package miro.main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ParserTest {


    @Test
    public void testParse() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/output.txt");
        Parser parser = new Parser(taskList, ui, storage);

        String[] input1 = new String[]{"todo", "test", "task"};
        String[] input2 = new String[]{"invalid", "test", "task"};
        String[] input3 = new String[]{"bye", "test", "task"};

        assertFalse(parser.parse(input1));
        assertFalse(parser.parse(input2));
        assertTrue(parser.parse(input3));
    }
}
