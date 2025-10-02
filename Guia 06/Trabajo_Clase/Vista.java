package Task;

import java.util.List;

public class Vista {
    public void showTasks(List<Modelo> tasks) {
        System.out.println("\nLista de Tareas:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
