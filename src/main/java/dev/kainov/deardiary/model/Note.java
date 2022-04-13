package dev.kainov.deardiary.model;

import dev.kainov.deardiary.model.dto.NoteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "create_time")
    LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Note(String text, LocalDateTime createTime) {
        this.text = text;
        this.createTime = createTime;
    }

    public static Note toNote(NoteDTO noteDTO) {
        return new Note(noteDTO.getText(), noteDTO.getCreateTime());
    }
}