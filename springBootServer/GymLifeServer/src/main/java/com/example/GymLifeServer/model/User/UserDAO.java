package com.example.GymLifeServer.model.User;

import com.example.GymLifeServer.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDAO {

    @Autowired
    private UserRepository repository;

    public Users save(Users u)
    {
        return repository.save(u);
    }

//    public Iterable<User> findAll()
//    {
//        return repository.findAll();
//    }

    public ArrayList<Users> findAll()
    {
        ArrayList<Users> users = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(users::add);
        return users;
    }


    public void delete(Users u)
    {
        repository.delete(u);
    }
}
