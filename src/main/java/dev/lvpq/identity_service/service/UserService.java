package dev.lvpq.identity_service.service;

import dev.lvpq.identity_service.dto.request.UserCreationRequest;
import dev.lvpq.identity_service.dto.request.UserUpdateRequest;
import dev.lvpq.identity_service.entity.User;
import dev.lvpq.identity_service.exception.AppException;
import dev.lvpq.identity_service.exception.ErrorCode;
import dev.lvpq.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTS);

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public List<User> getAllRequest() {
        return userRepository.findAll();
    }

    public User getByIdRequest(String id) {
        return userRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }

    public User updateRequest(String id, UserUpdateRequest request) {
        User user = getByIdRequest(id);

        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public void deleteRequest(String id) {
        var user = getByIdRequest(id);
        userRepository.delete(user);
    }
}
