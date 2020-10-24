package ru.coursework.akinator.repos;

import org.springframework.data.jpa.repository.Query;
import ru.coursework.akinator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u from User u inner join u.roles r where r in ('USER') order by score desc")
    List<User> findTopByScore();
}