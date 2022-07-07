package dev.kainov.deardiary.model;

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
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private LocalDate birthday;

    private String status;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Note> notes = new HashSet<>();

    public User(String name, String email, LocalDate birthday, String status) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.status = status;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

    public void mapAttributes(String name, String email, LocalDate birthday, String status) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.status = status;
    }
}
