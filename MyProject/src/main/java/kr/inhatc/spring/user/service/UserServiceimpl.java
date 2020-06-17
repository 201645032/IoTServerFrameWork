package kr.inhatc.spring.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;

@Service
public class UserServiceimpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<Users> userList() {
		List<Users> list = userRepository.findAllByOrderByIdDesc();
		return list;
	}

	@Override
	public void saveUsers(Users user) {
		//자동으로 채워줌
		userRepository.save(user);
	}

	@Override
	public Users userDetail(String id) {
		Optional<Users> optional =  userRepository.findById(id);
		if(optional.isPresent()) {
			Users user = optional.get();
			return user;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void userDelete(String id) {
		userRepository.deleteById(id);
	}

	
}
