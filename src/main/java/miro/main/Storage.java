package miro.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import miro.task.DeadlineTask;
import miro.task.EventTask;
import miro.task.Task;
import miro.task.ToDoTask;

/**
 * Represents a storage to read and write into the task list.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the hard drive.
     *
     * @return The list of tasks currently stored in the hard drive.
     */
    public List<Task> load() throws IllegalArgumentException {
        List<Task> tasks = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return tasks;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitInput = line.split(" \\| ");

                Task task = switch (splitInput[0]) {
                case "T" -> new ToDoTask(splitInput[2]);
                case "D" -> new DeadlineTask(splitInput[2], formatDate(splitInput[3]));
                case "E" -> {
                    String[] dates = splitInput[3].split(" to ");
                    yield new EventTask(splitInput[2], formatDate(dates[0]), formatDate(dates[1]));
                }
                default -> throw new IllegalArgumentException("Error loading tasks.");
                };

                if (Integer.parseInt(splitInput[1]) == 1) {
                    task.mark();
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("An IOException occurred.");
        } catch (NumberFormatException e) {
            System.out.println("Unexpected marked value encountered during parsing.");
        }

        return tasks;
    }


    /**
     * Saves the given list to the hard drive.
     *
     * @param taskList The given list to be saved.
     */
    public void save(List<Task> taskList) {
        try {

            File file = new File(filePath);
            file.getParentFile().mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Task task : taskList) {
                bw.append(task.getOutputFormat());
                bw.append("\n");
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("An IOException occurred. " + e);
        }
    }

    public LocalDate formatDate(String date) {
        return LocalDate.parse(date);
    }
}
