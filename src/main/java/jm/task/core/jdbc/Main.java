package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void addUser (UserServiceImpl userService, User user) {
        userService.saveUser(user.getName(),user.getLastName(),user.getAge());
        System.out.printf("User с именем — %s добавлен в базу данных \n", user.getName());
    }


    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        User firstUser = new User("Ivan", "Petrov", (byte) 11);
        User secondUser = new User("Stepan", "Sidorov", (byte) 33);
        User thirdUser = new User("Vlavlen", "Shishkin", (byte) 87);
        User forthUser = new User("Maria", "Burkina", (byte) 17);
        List<User> userList = new ArrayList<>();

        userService.createUsersTable();
        addUser(userService, firstUser);
        addUser(userService, secondUser);
        addUser(userService, thirdUser);
        addUser(userService, forthUser);

        userList = userService.getAllUsers();
        userList.forEach(user -> System.out.println(user.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
