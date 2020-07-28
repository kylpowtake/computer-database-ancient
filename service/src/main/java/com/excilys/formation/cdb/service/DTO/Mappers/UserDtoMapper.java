package com.excilys.formation.cdb.service.DTO.Mappers;


import com.excilys.formation.cdb.core.model.User;
import com.excilys.formation.cdb.service.Utility;
import com.excilys.formation.cdb.service.DTO.UserDto;

public class UserDtoMapper {

	private UserDtoMapper() {
	}

	public static User userDtoToUser(UserDto userDto) {
		User user = new User();
		if (userDto == null) {
			return null;
		}
		if (Utility.stringIsSomething(userDto.getUsername()) && Utility.stringIsSomething(userDto.getPassword())) {
			user = new User(userDto.getUsername(), user.getPassword());
		} else {
			return null;
		}
		if (userDto.getId() != null) {
			user.setId(userDto.getId());
		}
		return user;
	}

	public static UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		if (user == null) {
			return null;
		}
		userDto.setId(user.getId());
		if (user.getUsername() != null) {
			userDto.setUsername(user.getUsername());
		}
		if (user.getUsername() != null) {
			userDto.setPassword(user.getPassword());
		}
		return userDto;
	}
}