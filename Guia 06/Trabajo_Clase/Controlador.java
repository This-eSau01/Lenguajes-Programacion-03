package Task;

import java.util.ArrayList;
import java.util.List;

public class Controlador {
    private List<Modelo> tasks;
    private Vista view;

    public Controlador(Vista view) {
        this.tasks = new ArrayList<>();
        this.view = view;
    }

    public void addTask(String description) {
        tasks.add(new Modelo(description));
        view.showMessage("Tarea añadida: " + description);
    }

    public void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setCompleted(true);
            view.showMessage("Tarea completada: " + tasks.get(index).getDescription());
        } else {
            view.showMessage("Índice inválido.");
        }
    }

    public void showTasks() {
        view.showTasks(tasks);
    }
}
