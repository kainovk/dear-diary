package dev.kainov.deardiary.repository;

import dev.kainov.deardiary.dao.UserRepo;
import dev.kainov.deardiary.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo underTest;

    @Test
    void existsByEmail() {
        // given
        User user = prepareValidUser();
        underTest.save(user);

        // when
        Boolean existsByEmail = underTest.existsByEmail(user.getEmail());

        // then
        assertThat(true).isEqualTo(existsByEmail);
    }

    private User prepareValidUser() {
        return new User(
                "Kirill",
                "kirill@gmail.com",
                LocalDate.of(2001, 1, 8),
                "some"
        );
    }
}
