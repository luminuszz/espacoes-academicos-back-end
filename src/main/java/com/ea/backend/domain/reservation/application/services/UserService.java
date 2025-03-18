package com.ea.backend.domain.reservation.application.services;


import com.ea.backend.domain.reservation.application.dto.CreateUserDto;
import com.ea.backend.domain.reservation.application.repository.UserRepository;
import com.ea.backend.domain.reservation.enterprise.entity.User;
import com.ea.backend.infra.security.UserAuthenticated;
import org.springframework.beans.factory.annotation.Autowired;
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




    public void create(CreateUserDto createUserDto){
        User user = new User();

        var existUser = this.userRepository.findUserByEmail((createUserDto.getEmail()));

        if(existUser.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPasswordHash(this.hashService.encode(createUserDto.getPassword()));
        user.setRole(createUserDto.getRole());

        this.userRepository.save(user);
    }



    public User findByEmail(String email){
        var  user = this.userRepository.findUserByEmail(email);

        return user.orElse(null);
    }

}
