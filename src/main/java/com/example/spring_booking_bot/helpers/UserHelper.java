package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.models.UserModel;
import com.example.spring_booking_bot.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    private static UserHelper helper = null;
    public UserHelper( ) {
        helper = this;
    }
    @Autowired
    UserRepo userRepo;

    public static void saveUser(UserModel u){
        helper.userRepo.save(u);
    }
    public static UserModel findUser (String tgid){
        UserModel userModel;
        userModel = helper.userRepo.findUserModelByTgid(tgid);
        return userModel;
    }
}
