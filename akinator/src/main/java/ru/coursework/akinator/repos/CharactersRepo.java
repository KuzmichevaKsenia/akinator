package ru.coursework.akinator.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursework.akinator.domain.Characters;

import java.util.List;

public interface CharactersRepo extends JpaRepository<Characters, Long> {
    List<Characters> findByCharname (String charname);
}
