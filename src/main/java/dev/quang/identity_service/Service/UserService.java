package dev.quang.identity_service.Service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import dev.quang.identity_service.Dto.Request.SaveUser;
import dev.quang.identity_service.Dto.Response.DetailUser;
import dev.quang.identity_service.Dto.Response.ListUsers;
import dev.quang.identity_service.Entity.User;
import dev.quang.identity_service.Respository.UserRepository;
import dev.quang.identity_service.Specification.UserSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserSpecification userSpecification;

    public void addUser(SaveUser request) throws Exception {
        var user = findByEmail(request.getEmail()); 
        if (findByEmail(request.getEmail()) != null) {
            throw new Exception("Existing User");
        }

        user = User.builder()
            .email(request.getEmail())
            .name(request.getName())
            .password(request.getPassword())
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .dob(request.getDob())
            .build();
        userRepository.save(user);
    }

    public List<ListUsers> getAllsUsers() {
        var users = userRepository.findAll();
        return users
            .stream()
            .map(user -> ListUsers.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .build())
            .toList();
    }

    public DetailUser getUser(String id) {
        var user = userRepository.findById(id).orElse(null);
        return DetailUser.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .dob(user.getDob())
                .build();
    }

    public void updateUser(String id, SaveUser request) throws Exception {
        if (findByEmail(request.getEmail()) == null) {
            throw new Exception("Not Existing User");
        }

        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Not Existing User"));

        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());
                
        userRepository.save(user);
    }

    public void deleteAll(List<String> ids) throws Exception {
        if (ids == null) {
            userRepository.deleteAll();
        } else {
            var users = userRepository.findAllById(ids);
            if (users.isEmpty()) {
                throw new Exception("Id list is invalid");
            }
            userRepository.deleteAll(users);
        }
    }

    public void deleteUser(String id) throws Exception {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("Not Existing User"));
        userRepository.delete(user);
    }

    public void hideUser(String id) throws Exception {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new Exception("Not Existing User"));
        user.setHide(true);
        userRepository.save(user);
    }

    public void hideAll(List<String> ids) throws Exception {
        List<User> users;
        if (ids == null) {
            users = userRepository.findAll();
            users.stream().forEach(user -> user.setHide(true));
        } else {
            users = userRepository.findAllById(ids);
            if (users.isEmpty()) {
                throw new Exception("Id list is invalid");
            }
            users.stream()
                .map(user -> {
                    user.setHide(true);
                    return user;
                });   
        }
        userRepository.saveAll(users);    
    }
    
    public User findByEmail(String email) {
        return userRepository
            .findOne(Specification.where(userSpecification.hasEmail(email)))
            .orElse(null);
    }
    
}
