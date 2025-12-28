package com.vladl.library.repository.memory;

import com.vladl.library.model.User;
import com.vladl.library.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user){
        users.add(user);
    }

    @Override
    public Optional<User> findById(int id){
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public List<User> getAll(){
        return new ArrayList<>(users);
    }

    @Override
    public void persist(){

    }

}
