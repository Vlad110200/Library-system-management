package com.vladl.library.repository.json;

import com.vladl.library.model.User;
import com.vladl.library.repository.UserRepository;
import com.vladl.library.storage.JsonStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonUserRepository implements UserRepository {

    private final List<User> users;
    private final String FILE = "users.json";

    public JsonUserRepository(){
        this.users = new ArrayList<>(
                JsonStorage.load(FILE, User.class));
        int maxId = users.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
        User.setCounter(maxId + 1);
    }

    @Override
    public void save(User user){
        users.add(user);
        persist();
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
        JsonStorage.save(FILE, users);
    }
}
