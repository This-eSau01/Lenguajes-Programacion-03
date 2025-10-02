package Task;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Vista view = new Vista();
        Controlador controller = new Controlador(view);

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n--- Gestor de Tareas (MVC) ---");
            System.out.println("1. Añadir tarea");
            System.out.println("2. Completar tarea");
            System.out.println("3. Mostrar tareas");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("Descripción de la tarea: ");
                    String description = scanner.nextLine();
                    controller.addTask(description);
                    break;
                case 2:
                    controller.showTasks();
                    System.out.print("Número de tarea a completar: ");
                    int index = scanner.nextInt() - 1;
                    controller.completeTask(index);
                    break;
                case 3:
                    controller.showTasks();
                    break;
                case 0:
                    System.out.println("Destruyendo todo");
                    break;
                default:
                    System.out.println("Opción INVALIDA meh");
            }
        } while (option != 0);

        scanner.close();
    }
}
