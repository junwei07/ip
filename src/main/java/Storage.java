import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

public class Storage {
    private String filePath;

    Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the user's hard drive.
     *
     * @return The list of tasks currently stored in the user's hard drive.
     */
    public ArrayList<Task> load() throws IllegalArgumentException {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return tasks;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitInput = line.split(" \\| ");

                Task task;
                switch (splitInput[0]) {
                case "T":
                    task = new ToDoTask(splitInput[2]);
                    break;
                case "D":
                    task = new DeadlineTask(splitInput[2], splitInput[3]);
                    break;
                case "E":
                    task = new EventTask(splitInput[2], splitInput[3], splitInput[4]);
                    break;
                default:
                    throw new IllegalArgumentException("Error loading tasks.");
                }

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
     * Saves the given list to the user's hard drive.
     *
     * @param taskList The given list to be saved.
     */
    public void save(ArrayList<Task> taskList) {
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
}