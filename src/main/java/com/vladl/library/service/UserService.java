package com.vladl.library.service;

import com.vladl.library.model.User;
import com.vladl.library.model.UserStatus;
import com.vladl.library.repository.LoanRepository;
import com.vladl.library.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public UserService(UserRepository userRepository, LoanRepository loanRepository){
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public void addUser(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("User name cannot be empty");
        }
        userRepository.save(new User(name.trim()));
    }

    public User getById(int id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found, id=" + id));
    }

    public List<User> getAllUsers(){
        return userRepository.getAll();
    }

    public void changeUserStatus(int id, UserStatus status){
        User user = getById(id);

        if(loanRepository.hasActiveLoanForUser(id) && status == UserStatus.DELETED){
            throw new IllegalArgumentException(
                    "Cannot change status user with active loans, id=" + id);
        }

        user.changeUserStatus(id, status);
        userRepository.persist();
    }

    public User getByIdOrNull(int id){
        return userRepository.findById(id).orElse(null);
    }
}
