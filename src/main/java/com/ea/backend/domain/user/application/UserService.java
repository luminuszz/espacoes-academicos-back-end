package com.ea.backend.domain.user.application;


import com.ea.backend.domain.user.application.dto.CreateUserDto;
import com.ea.backend.domain.user.enterprise.entity.User;
import com.ea.backend.domain.user.enterprise.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class UserService {


    private final  UserRepository userRepository;
    private final PasswordEncoder hashService;

    public  UserService(UserRepository userRepository, PasswordEncoder hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;

    }


    public void createAdmin(CreateUserDto createUserDto){
        User user = new User();

        var existUser = this.userRepository.findUserByEmail((createUserDto.getEmail()));

        if(existUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPasswordHash(this.hashService.encode(createUserDto.getPassword()));
        user.setRole(UserRole.ADMIN);

        this.userRepository.save(user);
    }

    public  void createTeacher(CreateUserDto createUserDto) {
        User user = new User();

        var existUser = this.userRepository.findUserByEmail((createUserDto.getEmail()));

        if(existUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPasswordHash(this.hashService.encode(createUserDto.getPassword()));
        user.setRole(UserRole.TEACHER);

        this.userRepository.save(user);

    }


    public User findByEmail(String email){
        var  user = this.userRepository.findUserByEmail(email);

        return user.orElse(null);
    }

    public Page<User> fetchTeachersPaginated(int page, int pageSize) {
        return this.userRepository.findAllByRole(UserRole.TEACHER, PageRequest.of(page, pageSize));
    }


}
