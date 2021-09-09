package kz.eal.freedomfinance.servives;

import kz.eal.freedomfinance.entities.Users;

import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();
    Users getByChatId(Long chatId);
    void addUser(Users user);
    void updateUser(Users user);
    List<Users> getAllSubscribedUsers();
}
