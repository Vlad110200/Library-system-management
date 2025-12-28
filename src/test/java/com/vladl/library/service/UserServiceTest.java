package com.vladl.library.service;

import com.vladl.library.model.User;
import com.vladl.library.model.UserStatus;
import com.vladl.library.repository.LoanRepository;
import com.vladl.library.repository.UserRepository;
import com.vladl.library.repository.memory.InMemoryLoanRepository;
import com.vladl.library.repository.memory.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setup(){
        UserRepository userRepository = new InMemoryUserRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();

        User.resetCounter();

        userService = new UserService(userRepository, loanRepository);
    }

    @Test
    void addUser_success(){
        userService.addUser("Test_name");
        User user = userService.getAllUsers().getFirst();

        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertEquals("Test_name", user.getName());
    }

    @Test
    void getById_success(){
        userService.addUser("Test_name");
        User createdUser = userService.getAllUsers().getFirst();

        User foundUser = userService.getById(createdUser.getId());

        assertEquals(createdUser.getId(), foundUser.getId());
        assertEquals("Test_name", foundUser.getName());
    }

    @Test
    void getById_userNotFound_shouldThrowException(){
        int nonExistingUserId = 999;

        assertThrows(IllegalArgumentException.class, () ->
                userService.getById(nonExistingUserId));
    }

    @Test
    void markDeletedUser_success(){
        userService.addUser("Test_name");

        User user = userService.getAllUsers().getFirst();
        userService.changeUserStatus(user.getId(), UserStatus.DELETED);
        assertEquals(UserStatus.DELETED, user.getStatus());
    }
}
