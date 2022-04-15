package dev.kainov.deardiary.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {

    private String name;
    private LocalDate birthday;
    private String status;
}
