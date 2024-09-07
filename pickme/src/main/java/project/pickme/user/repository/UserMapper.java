package project.pickme.user.repository;

import org.apache.ibatis.annotations.Mapper;

import project.pickme.user.domain.User;

@Mapper
public interface UserMapper {
	void save(User user);
}
