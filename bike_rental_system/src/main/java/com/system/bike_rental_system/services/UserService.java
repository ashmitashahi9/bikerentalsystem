package com.system.bike_rental_system.services;

import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.pojo.PasswordChangePojo;
import com.system.bike_rental_system.pojo.UserPojo;

import java.io.IOException;
import java.util.List;

public interface UserService {

    //Create ------------------------------------------------------
    void saveUser(UserPojo userPojo);
    void saveUser(User user);


    //Retrieve ----------------------------------------------------
    List<User> fetchAll();
    User fetchById(Integer id);
    User findByEmail(String email);


    //Update -------------------------------------------------------
    void updateGeneral(UserPojo userPojo) throws IOException;
    void updateDocs(UserPojo userPojo) throws IOException;
    void changePassword(PasswordChangePojo passwordChangePojo);


    //Delete -------------------------------------------------------
    void deleteAccount(PasswordChangePojo passwordChangePojo);

    void sendEmail();

    String updateResetPassword(String email);

    void processPasswordResetRequest(String email);

//    to send email
//    void sendEmail();
}
