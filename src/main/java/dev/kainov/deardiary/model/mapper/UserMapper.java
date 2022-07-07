package dev.kainov.deardiary.model.mapper;

import dev.kainov.deardiary.model.User;
import dev.kainov.deardiary.model.dto.UserDTO;
import dev.kainov.deardiary.model.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = NoteMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    User userRequestToUser(UserRequest userRequest);
}
