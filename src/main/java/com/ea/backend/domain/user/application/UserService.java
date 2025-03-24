package com.ea.backend.domain.user.application;


import com.ea.backend.domain.user.application.dto.CreateUserDto;
import com.ea.backend.domain.user.application.dto.RegisterTeacherDto;
import com.ea.backend.domain.user.application.repository.UserProjection;
import com.ea.backend.domain.user.application.repository.UserRepository;
import com.ea.backend.domain.user.enterprise.entity.User;
import com.ea.backend.domain.user.enterprise.entity.UserRole;
import com.ea.backend.shared.DomainException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
    private final PasswordEncoder hashService;

    public  UserService(UserRepository userRepository, PasswordEncoder hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;

    }


    public void createAdmin(CreateUserDto createUserDto){
        User user = new User();

        var existUser = this.userRepository.findUserByEmail((createUserDto.getEmail()));

        if(existUser.isPresent()) {
      throw new DomainException("User already exists");
        }

        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPasswordHash(this.hashService.encode(createUserDto.getPassword()));
    user.setRole(UserRole.valueOf(createUserDto.getRole()));

        this.userRepository.save(user);
    }

  public void createTeacher(RegisterTeacherDto createUserDto) {
        User user = new User();

    var existUser = this.userRepository.findUserByEmail((createUserDto.email()));

        if(existUser.isPresent()) {
      throw new DomainException("User already exists");
        }

    user.setEmail(createUserDto.email());
    user.setName(createUserDto.name());
    user.setPasswordHash(this.hashService.encode(createUserDto.password()));
        user.setRole(UserRole.TEACHER);

        this.userRepository.save(user);

    }


    public User findByEmail(String email){
        var  user = this.userRepository.findUserByEmail(email);

        return user.orElse(null);
    }

  public Page<UserProjection> fetchUsersPaginated(int page, int pageSize) {
    return this.userRepository.findAllBy(PageRequest.of(page, pageSize));
    }


}
