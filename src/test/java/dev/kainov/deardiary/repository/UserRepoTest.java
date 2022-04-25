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
    void existsByEmail_Success() {
        // given
        User user = prepareValidUser();
        underTest.save(user);

        // when
        Boolean existsByEmail = underTest.existsByEmail(user.getEmail());

        // then
        assertThat(existsByEmail).isEqualTo(true);
    }

    @Test
    void existsByEmail_ShouldReturnFalse() {
        // when
        String email = "kirill@gmail.com";
        Boolean existsByEmail = underTest.existsByEmail(email);

        // then
        assertThat(existsByEmail).isEqualTo(false);
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
