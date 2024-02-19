package com.system.bike_rental_system.pojo;

import com.system.bike_rental_system.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPojo {
    private Integer id;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String mobileNo;
    private String address;
    private MultipartFile image;
    private MultipartFile citizenshipF;
    private MultipartFile citizenshipB;
    private MultipartFile license;
    private String status;

    public UserPojo(User user){
        this.id=user.getId();
        this.fName= user.getFName();
        this.lName= user.getLName();
        this.email= user.getEmail();
        this.password= user.getPassword();
        this.mobileNo=user.getMobileNo();
        this.address= user.getAddress();
        this.status=user.getStatus();
    }
}
