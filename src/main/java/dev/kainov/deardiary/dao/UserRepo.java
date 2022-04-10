package dev.kainov.deardiary.dao;

import dev.kainov.deardiary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
