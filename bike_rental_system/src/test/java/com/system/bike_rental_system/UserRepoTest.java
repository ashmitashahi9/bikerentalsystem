package com.system.bike_rental_system;

import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepoTest {
    @Autowired
    private UserRepo userRepo;
    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveUserTest(){
        User user = User.builder()
                .fName("Abhyas").build();

        userRepo.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    public void getUserTest(){
        User userCreated=userRepo.findById(80).get();
        Assertions.assertThat(userCreated.getId()).isEqualTo(80);
    }
    @Test
    @Order(3)
    public void getListOfUserTest(){
        List<User> Users = userRepo.findAll();
        Assertions.assertThat(Users.size()).isGreaterThan(0);
    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest(){
        User user =userRepo.findById(80).get();
        user.setFName("Abhyas");
        User userUpdated = userRepo.save(user);
        Assertions.assertThat(userUpdated.getFName()).isEqualTo("Abhyas");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserTest(){
        User user = userRepo.findById(80).get();
        userRepo.delete(user);
        
        User user1 = null;
        Optional<User> optionalUser = userRepo.findById(1);
        if (optionalUser.isPresent()){
            user1 = optionalUser.get();
        }
        Assertions.assertThat(user1).isNull();
    }

//    50,3,/Suzuki_Avenis 125.jpg,Avenis 125,Suzuki,"10 Nm @ 5,500 rpm",54 kmpl,124 cc,1000,0,5.2 Litres,90,Automatic,2

}