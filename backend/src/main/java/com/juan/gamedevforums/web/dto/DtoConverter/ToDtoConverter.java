package com.juan.gamedevforums.web.dto.DtoConverter;

import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.web.dto.UserDto;
import java.util.Optional;

public final class ToDtoConverter {

    public static UserDto userToDto(final User user) {
	return new UserDto(
			   user.getEmail(),
			   user.getUsername(),
			   user.isEnabled(),
			   user.getImage(),
			   user.getBio()
			   );
    }
}
