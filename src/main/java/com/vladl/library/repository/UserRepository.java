package com.vladl.library.repository;

import com.vladl.library.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(int id);
    List<User> getAll();
    void persist();
}
