package dev.kainov.deardiary.model;

import dev.kainov.deardiary.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthday;

    private String status;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Note> notes = new HashSet<>();

    public User(String name, LocalDate birthday, String status) {
        this.name = name;
        this.birthday = birthday;
        this.status = status;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public static User toUser(UserDTO userDTO) {
        return new User(
                userDTO.getName(),
                userDTO.getBirthday(),
                userDTO.getStatus()
        );
    }
}
