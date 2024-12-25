package dev.quang.identity_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.quang.identity_service.Dto.Request.SaveUser;
import dev.quang.identity_service.Dto.Response.DetailUser;
import dev.quang.identity_service.Dto.Response.ListUsers;
import dev.quang.identity_service.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping()
    public boolean createUser(@RequestBody SaveUser request) throws Exception {
        userService.addUser(request);
        return true;
    }

    @GetMapping()
    public List<ListUsers> getallUsers() {
        return userService.getAllsUsers();
    }

    @GetMapping("/{id}")
    public DetailUser getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public boolean updateUser(@PathVariable String id, @RequestBody SaveUser request) throws Exception {
        userService.updateUser(id, request);;
        return true;
    }

    @DeleteMapping()
    public boolean deleteAll(@RequestBody(required = false) List<String> ids) throws Exception {
        userService.deleteAll(ids);
        return true;
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable("id") String id) throws Exception {
        userService.deleteUser(id);
        return true;
    }

    @PatchMapping()
    public boolean hideAll(@RequestBody(required = false) List<String> ids) throws Exception {
        userService.hideAll(ids);
        return true;
    }

    @PatchMapping("/{id}")
    public boolean hideUser(@PathVariable("id") String id) throws Exception {
        userService.hideUser(id);
        return true;
    }
}
