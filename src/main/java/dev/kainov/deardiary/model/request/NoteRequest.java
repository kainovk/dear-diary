package dev.kainov.deardiary.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NoteRequest {

    @NotBlank(message = "entry should not be blank")
    private String text;
}