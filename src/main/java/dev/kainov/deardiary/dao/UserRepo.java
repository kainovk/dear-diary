package dev.kainov.deardiary.dao;

import dev.kainov.deardiary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("" +
            "SELECT CASE WHEN COUNT(u) > 0" +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM User u " +
            "WHERE u.email = ?1"
    )
    public Boolean existsByEmail(String email);
}
