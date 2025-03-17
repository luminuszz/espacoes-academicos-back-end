package com.ea.backend.core.reservation.application.useCases;


import com.ea.backend.core.reservation.application.dto.CreateUserDto;
import com.ea.backend.core.reservation.application.repository.UserRepository;
import com.ea.backend.core.reservation.domain.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
public class CreateUser {

    private final UserRepository userRepository;
    private  final PasswordEncoder hashService;

    public CreateUser( UserRepository userRepository , PasswordEncoder hashService ) {
       this.userRepository = userRepository;
       this.hashService = hashService;

    }

    public void execute(CreateUserDto createUserDto) {

        User user = new User();

        var existUser = this.userRepository.findUserByEmail((createUserDto.getEmail()));

        if(existUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        String passwordHash = this.hashService.encode(createUserDto.getPassword());

        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPasswordHash(passwordHash);

        this.userRepository.save(user);


    }
}
