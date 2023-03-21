package com.rtechnologies.youtubetool.Service;

import com.rtechnologies.youtubetool.Model.User;
import com.rtechnologies.youtubetool.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User loginUser(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new Exception("Email address not found.");
        }
        if(!user.getPassword().equals(password)) {
            throw new Exception("Incorrect password.");
        }
        return user;
    }

    public User registerUser(User user){
        return userRepository.save(user);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

}

