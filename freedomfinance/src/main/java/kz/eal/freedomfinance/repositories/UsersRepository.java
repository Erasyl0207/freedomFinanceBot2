package kz.eal.freedomfinance.repositories;

import kz.eal.freedomfinance.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users,Long> {
    List<Users> findAllByNotifyTrue();
    Users findByChatId(Long chatId);
}
