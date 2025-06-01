package main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class UserServiceApp {

    private static final UserDaoImpl userDao = new UserDaoImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в User Service!");

        while (true) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Прочитать пользователя");
            System.out.println("3. Обновить пользователя");
            System.out.println("4. Удалить пользователя");
            System.out.println("5. Показать всех пользователей");
            System.out.println("0. Выйти");

            System.out.print("Ваш выбор: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createUser(scanner);
                    break;
                case "2":
                    readUser(scanner);
                    break;
                case "3":
                    updateUser(scanner);
                    break;
                case "4":
                    deleteUser(scanner);
                    break;
                case "5":
                    listUsers();
                    break;
                case "0":
                    exit(scanner);
                    return;
                default:
                    System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    private static void createUser(Scanner scanner) {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        System.out.print("Введите возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        user.setCreatedAt(LocalDateTime.now());

        userDao.createUser(user);
        System.out.println("Пользователь создан: " + user);
    }

    private static void readUser(Scanner scanner) {
        System.out.print("Введите ID пользователя: ");
        Long id = Long.parseLong(scanner.nextLine());

        User user = userDao.readUser(id);
        if (user == null) {
            System.out.println("Пользователь с ID " + id + " не найден.");
        } else {
            System.out.println("Найден пользователь: " + user);
        }
    }

    private static void updateUser(Scanner scanner) {
        System.out.print("Введите ID пользователя для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());

        User user = userDao.readUser(id);
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        System.out.print("Новое имя (" + user.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.trim().isEmpty()) user.setName(name);

        System.out.print("Новый email (" + user.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) user.setEmail(email);

        System.out.print("Новый возраст (" + user.getAge() + "): ");
        String ageStr = scanner.nextLine();
        if (!ageStr.trim().isEmpty()) user.setAge(Integer.parseInt(ageStr));

        userDao.updateUser(user);
        System.out.println("Пользователь обновлен: " + user);
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("Введите ID пользователя для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        userDao.deleteUser(id);
        System.out.println("Операция удаления завершена (если пользователь существовал).");
    }

    private static void listUsers() {
        List<User> users = userDao.getAllUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("Пользователи не найдены.");
            return;
        }

        System.out.println("Список пользователей:");
        users.forEach(System.out::println);
    }

    private static void exit(Scanner scanner) {
        System.out.println("Завершение работы...");
        scanner.close();
        userDao.close();
    }
}

