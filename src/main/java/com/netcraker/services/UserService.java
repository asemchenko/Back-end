package com.netcraker.services;

import com.netcraker.model.Page;
import com.netcraker.model.Role;
import com.netcraker.model.User;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUsualUser(User user);
    User createAdminModerator(User user);
    User findByUserId(int userId);
    User findByEmail(String email);
    void updateUser(User oldUser, User newUser);
    void updateAdminModerator(User user);
    void deleteAdminModerator(String email);
    boolean activateUser(String code);
    boolean equalsPassword(User user, String password);
    User changePassword(int userId, String oldPass, String newPass);
    Page<User> searchUser(String searchExpression, int page, int pageSize);
    @NonNull List<Integer> getListId();
}