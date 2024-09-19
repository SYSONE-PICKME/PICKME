package project.pickme.user.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.user.domain.User;
import project.pickme.user.dto.UserDto;

@Mapper
public interface UserMapper {
	void save(User user);

	Optional<User> findUserById(String id);

	void deleteAll();

	void minusPoint(@Param("userId") String userId, @Param("price") long price);

	void updatePointById(UserDto.UpdatePoint userDto);

	void updatePassword(@Param("password") String password, @Param("userId") String userId);
}
