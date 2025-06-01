package main;

import java.util.List;

public interface UserDao {
    void createUser(User user);
    User readUser(Long id);
    void updateUser(User user);
    void deleteUser(Long id);
    List<User> getAllUsers();
}

