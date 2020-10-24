package ru.coursework.akinator.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.coursework.akinator.domain.Band;
import ru.coursework.akinator.domain.User;

import java.util.List;

public interface BandRepo extends JpaRepository<Band, Long> {
    Band findByBandname(String bandname);
    List<Band> findByParent(User parent);

    @Query("SELECT b FROM User u INNER JOIN u.bands b WHERE u.id = :userId")
    List<Band> getUserBands(Long userId);
}
