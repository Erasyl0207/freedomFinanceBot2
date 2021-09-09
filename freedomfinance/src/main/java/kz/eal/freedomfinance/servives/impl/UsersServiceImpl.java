package kz.eal.freedomfinance.servives.impl;

import kz.eal.freedomfinance.entities.Users;
import kz.eal.freedomfinance.repositories.UsersRepository;
import kz.eal.freedomfinance.servives.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users getByChatId(Long chatId) {
        return usersRepository.findByChatId(chatId);
    }

    @Override
    public void addUser(Users user) {
        usersRepository.save(user);
    }

    @Override
    public void updateUser(Users user) {
        usersRepository.save(user);
    }

    @Override
    public List<Users> getAllSubscribedUsers() {
        return usersRepository.findAllByNotifyTrue();
    }
}
