package com.excilys.formation.cdb.service.DTO.Mappers;


import com.excilys.formation.cdb.core.model.User;
import com.excilys.formation.cdb.core.model.User.UserBuilder;
import com.excilys.formation.cdb.service.Utility;
import com.excilys.formation.cdb.service.DTO.UserDto;

public class UserDtoMapper {

	private UserDtoMapper() {
	}

	public static User userDtoToUser(UserDto userDto) {
		User user = null;
		if (userDto == null) {
			return user;
		}
		UserBuilder userBuilder = null;
		if (Utility.stringIsSomething(userDto.getPseudo()) && Utility.stringIsSomething(userDto.getPassword())) {
			userBuilder = new UserBuilder(userDto.getPseudo(), userDto.getPseudo());
		} else {
			return null;
		}
		if (userDto.getId() != null) {
			userBuilder.withId(userDto.getId());
		}
		user = userBuilder.build();
		return user;
	}

	public static UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		if (user == null) {
			return null;
		}
		userDto.setId(user.getId());
		if (user.getPseudo() != null) {
			userDto.setPseudo(user.getPseudo());
		}
		if (user.getPseudo() != null) {
			userDto.setPassword(user.getPassword());
		}
		return userDto;
	}
}