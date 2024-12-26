package dev.quang.identity_service.Service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import dev.quang.identity_service.Dto.Request.SaveUser;
import dev.quang.identity_service.Dto.Response.DetailUser;
import dev.quang.identity_service.Dto.Response.ListUsers;
import dev.quang.identity_service.Entity.User;
import dev.quang.identity_service.Exception.AppException;
import dev.quang.identity_service.Exception.ErrorCode;
import dev.quang.identity_service.Mapper.UserMapper;
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
    UserMapper userMapper;
    UserSpecification userSpecification;

    public DetailUser addUser(SaveUser request) {
        var user = findByEmail(request.getEmail()); 
        if (findByEmail(request.getEmail()) != null) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        user = userMapper.toUser(request);
        userRepository.save(user);

        return userMapper.toDetailUser(user);
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
        var user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        
        return userMapper.toDetailUser(user);
    }

    public DetailUser updateUser(String id, SaveUser request) {
        if (findByEmail(request.getEmail()) == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTS);
        }

        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        userMapper.updateUser(request, user);
                
        userRepository.save(user);

        return userMapper.toDetailUser(user);
    }

    public void deleteAll(List<String> ids) {
        if (ids == null) {
            userRepository.deleteAll();
        } else {
            var users = userRepository.findAllById(ids);
            if (users.isEmpty()) {
                throw new AppException(ErrorCode.ID_INVALID);
            }
            userRepository.deleteAll(users);
        }
    }

    public void deleteUser(String id) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        userRepository.delete(user);
    }

    public void hideUser(String id) {
        var user = userRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        user.setHide(true);
        userRepository.save(user);
    }

    public void hideAll(List<String> ids) {
        List<User> users;
        if (ids == null) {
            users = userRepository.findAll();
            users.stream().forEach(user -> user.setHide(true));
        } else {
            users = userRepository.findAllById(ids);
            if (users.isEmpty()) {
                throw new AppException(ErrorCode.ID_INVALID);
            }
            users.stream().forEach(user -> user.setHide(true));   
        }
        userRepository.saveAll(users);    
    }
    
    public User findByEmail(String email) {
        return userRepository
            .findOne(Specification.where(userSpecification.hasEmail(email)))
            .orElse(null);
    }
    
}
