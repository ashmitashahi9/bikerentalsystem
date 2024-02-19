package com.system.bike_rental_system.pojo;

import com.system.bike_rental_system.entity.Bike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BikePojo {
    private Integer id;
    private String bikeName;
    private String brandName;
    private Integer topSpeed;
    private String mileage;
    private String power;
    private String transmission;
    private String tankCapacity;
    private String maxTorque;
    private Integer priceDay;
    private Integer availableNo;
    private MultipartFile bikeImage;
    private String bikeImageBase64;
    private Integer rentedNumber;
    private Integer category;

    public BikePojo(Bike bike) {
        this.id = bike.getId();
        this.bikeName = bike.getBikeName();
        this.brandName = bike.getBrandName();
        this.topSpeed = bike.getTopSpeed();
        this.mileage = bike.getMileage();
        this.power = bike.getPower();
        this.transmission = bike.getTransmission();
        this.tankCapacity = bike.getTankCapacity();
        this.maxTorque = bike.getMaxTorque();
        this.priceDay = bike.getPriceDay();
        this.availableNo = bike.getAvailableNo();
        this.rentedNumber = bike.getRentedNumber();
        this.category = bike.getCategory().getId();
    }
}
