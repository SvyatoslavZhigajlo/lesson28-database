import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        goProgram();
    }

    private void goProgram() {
        Scanner scanner = new Scanner(System.in);

//                ДЗ 29 Задание 1 - Реализовать программное консольное меню, позволяющее выполнять разные действия
//                под номерами 1-9 или буквами a-z.
        while (true) {
            System.out.println("Выберите необходимые действия");
            System.out.println("\t1 - Создать таблицу в БД");
            System.out.println("\t2 - Добавить запись по умолчанию");
            System.out.println("\t3 - Добавить студента");
            System.out.println("\t4 - Вывести список студентов А-Я");
            System.out.println("\t5 - Найти студента по имени или фамилии");
            System.out.println("\t7 - Удалить запись");
            System.out.println("\t8 - Удалить таблицу БД");
            System.out.println("\t9 - выход");
            int selectNumb = scanner.nextInt();

            switch (selectNumb) {
                //ДЗ 29 Задание 2 - Реализовать элементы меню по созданию и удалению таблицы со студентами.
                //Студент имеет минимум 4 поля.
                case 1:
                    createStudentsDatabase();
                    System.out.println("Файл создан");
                    break;
                case 2:
                    addDefaultStudent();
                    System.out.println("Запись сделана");
                    break;

                //ДЗ 29 Задание 3 - Реализовать элемент меню по добавлению нового студента в базу данных из
                //введенных пользователем данных с консоли.
                case 3:
                    addUserStudent();
                    System.out.println("Студент добавлен");
                    break;
                //ДЗ 29 Задание 4 -Реализовать элемент меню по выводу всех студентов,
                // отсортированных по имени от А до Я.
                case 4:
                    showListStudents();
                    break;
                //ДЗ 29 Задание 5 - Реализовать элемент меню по поиску студентов по тексту, введенному пользователем,
                // и выводу найденных упорядоченными по имени от А до Я.
                case 5:
                    searchStudent();
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    deleteDatabase();
                    System.out.println("База данных удалена!!!");
                    break;
                case 9:
                    System.out.println("Выход из программы");
                    break;
                default:
                    System.out.println("Не правильный выбор, повторите попытку");
            }
        }
    }
    // Case 1
    private void createStudentsDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE Students (id INTEGER, name VARCHAR(100), " +
                    "lastName VARCHAR(100), age INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Case 2
    private void addDefaultStudent() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Students VALUES(1, 'Svyatoslav', 'Zhigajlo', 33)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Case 3
    private void addUserStudent() {
        Scanner scanner = new Scanner(System.in);

        int id = 1;
        System.out.println("Введите имя студента: ");
        String name = scanner.nextLine();
        System.out.println("ведите фамилию студента: ");
        String lastName = scanner.nextLine();
        System.out.println("Введите возраст студента: ");
        int age = scanner.nextInt();

        String str = String.format("INSERT INTO Students VALUES(%d, '%s', '%s', %d)",
                id, name, lastName, age);
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Case 4
    private void showListStudents() {
        ArrayList <Student> studentsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            ResultSet cursor = statement.executeQuery("SELECT * FROM Students");
            while (cursor.next()) {
                studentsList.add(new Student(cursor.getInt("id"),
                        cursor.getString("name"),
                        cursor.getString("lastName"),
                        cursor.getInt("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(studentsList);
        System.out.println(studentsList);
    }

    //Case 5
    private void searchStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите текст для поиска:");

        String scannerText = scanner.nextLine();
        String searchText = scannerText.toLowerCase();

        ArrayList <Student> resultSearch = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            ResultSet cursor = statement.executeQuery("SELECT * FROM Students");
            while (cursor.next()) {
                String name = cursor.getString("name").toLowerCase();
                String lastName = cursor.getString("lastName").toLowerCase();
                if (name.contains(searchText) ||
                        lastName.contains(searchText) ) {
                    resultSearch.add(new Student(cursor.getInt("id"),
                            cursor.getString("name"),
                            cursor.getString("lastName"),
                            cursor.getInt("age")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(resultSearch);
        if (resultSearch.size() != 0) {
            System.out.println(resultSearch);
        }else {
            System.out.println("Поиск не дал результутов, повторите попытку");
        }
    }

    //Case8
    private void deleteDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE Students");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
