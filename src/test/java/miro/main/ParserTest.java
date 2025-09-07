package miro.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import miro.task.DeadlineTask;
import miro.task.EventTask;
import miro.task.Task;
import miro.task.ToDoTask;


public class ParserTest {
    // define stubs
    public class StorageStub extends Storage {
        private final List<Task> taskList = new ArrayList<>();

        public StorageStub(String filePath) {
            super(filePath);

            // load dummy tasks
            Task todoTask = new ToDoTask("todo task");
            Task deadlineTask = new DeadlineTask("deadline task", LocalDate.now());
            Task eventTask = new EventTask("event task", LocalDate.now(), LocalDate.now());

            taskList.add(todoTask);
            taskList.add(deadlineTask);
            taskList.add(eventTask);
        }

        @Override
        public List<Task> load() throws IllegalArgumentException {
            return this.taskList;
        }

        @Override
        public void save(List<Task> taskList) {
        }

    }

    @Test
    public void parse_validInput() {
        // dummy
        Storage storage = new StorageStub("./data/test-output.txt");
        TaskList taskList = new TaskList(storage.load());
        Ui ui = new Ui();
        Parser parser = new Parser(taskList, ui, storage);


        // test with valid todo task
        String[] toDoInput = new String[]{"todo", "test", "todo"};
        Task todoTask = new ToDoTask("test todo");

        StringBuilder expectedToDoResponse = new StringBuilder("Got it. I've added this task:\n");
        expectedToDoResponse.append(todoTask);
        expectedToDoResponse.append("\nNow you have 4 tasks in the list.\n");

        // test with valid deadline task
        LocalDate currTime = LocalDate.now();
        String[] deadlineInput = new String[]{"deadline", "test", "deadline", "/by", currTime.toString()};
        Task deadlineTask = new DeadlineTask("test deadline", currTime);

        StringBuilder expectedDeadlineResponse = new StringBuilder("Got it. I've added this task:\n");
        expectedDeadlineResponse.append(deadlineTask);
        expectedDeadlineResponse.append("\nNow you have 5 tasks in the list.\n");

        // test with valid event task
        String[] eventInput = new String[]{"event", "test", "event", "/from", currTime.toString(),
            "/to", currTime.toString()};
        Task eventTask = new EventTask("test event", currTime, currTime);

        StringBuilder expectedEventResponse = new StringBuilder("Got it. I've added this task:\n");
        expectedEventResponse.append(eventTask);
        expectedEventResponse.append("\nNow you have 6 tasks in the list.\n");

        assertEquals(expectedToDoResponse.toString(), parser.parse(toDoInput));
        assertEquals(expectedDeadlineResponse.toString(), parser.parse(deadlineInput));
        assertEquals(expectedEventResponse.toString(), parser.parse(eventInput));

    }

    @Test
    public void parse_invalidInput_throwException() {
        // dummy
        Storage storage = new StorageStub("./data/test-output.txt");
        TaskList taskList = new TaskList(storage.load());
        Ui ui = new Ui();
        Parser parser = new Parser(taskList, ui, storage);


        // test todo with empty description
        String[] toDoInput = new String[]{"todo"};
        String toDoResponse = parser.parse(toDoInput);

        assertEquals("Task description cannot be empty.", toDoResponse);

        // test deadline with no date
        String[] deadlineInput = new String[]{"deadline", "test"};
        String deadlineResponse = parser.parse(deadlineInput);

        assertEquals("Please specify a date using \"/by ...\"", deadlineResponse);

    }

}
