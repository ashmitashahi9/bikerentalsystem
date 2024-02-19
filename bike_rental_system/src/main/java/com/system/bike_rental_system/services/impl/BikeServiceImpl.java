package com.system.bike_rental_system.services.impl;

import com.system.bike_rental_system.entity.Bike;
import com.system.bike_rental_system.pojo.BikePojo;
import com.system.bike_rental_system.repo.BikeRepo;
import com.system.bike_rental_system.repo.CategoryRepo;
import com.system.bike_rental_system.services.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BikeServiceImpl implements BikeService {
    private final BikeRepo bikeRepo;

    private final CategoryRepo categoryRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\images\\bikes\\";

    @Override
    public String saveBike(BikePojo bikePojo) throws IOException {
        Bike bike;
        try {
            bike = bikeRepo.findById(bikePojo.getId()).orElseThrow();
        } catch (Exception e){
            bike = new Bike();
        }
        bike.setBikeName(bikePojo.getBikeName());
        bike.setBrandName(bikePojo.getBrandName());
        bike.setAvailableNo(bikePojo.getAvailableNo());

        if(!Objects.equals(bikePojo.getBikeImage().getOriginalFilename(), "")){
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, bikePojo.getBikeImage().getOriginalFilename());
            Files.write(fileNameAndPath, bikePojo.getBikeImage().getBytes());

            bike.setBikeImage(bikePojo.getBikeImage().getOriginalFilename());
        }

        bike.setPriceDay(bikePojo.getPriceDay());
        bike.setMileage(bikePojo.getMileage());
        bike.setMaxTorque(bikePojo.getMaxTorque());
        bike.setPower(bikePojo.getPower());
        bike.setTankCapacity(bikePojo.getTankCapacity());
        bike.setTopSpeed(bikePojo.getTopSpeed());
        bike.setTransmission(bikePojo.getTransmission());
        bike.setCategory(categoryRepo.findById(bikePojo.getCategory()).orElseThrow());
        bikeRepo.save(bike);
        return "Bike Saved";
    }

    @Override
    public String saveBikeByEntity(Bike bike){
        bikeRepo.save(bike);
        return "Updated";
    }

    @Override
    public Bike fetchById(Integer id) {
        Bike bike = bikeRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));

        bike = Bike.builder()
                .id(bike.getId())
                .bikeName(bike.getBikeName())
                .brandName(bike.getBrandName())
                .availableNo(bike.getAvailableNo())
                .rentedNumber(bike.getRentedNumber())
                .bikeImage(bike.getBikeImage())
                .bikeImageBase64(getImageBase64(bike.getBikeImage()))
                .priceDay(bike.getPriceDay())
                .mileage(bike.getMileage())
                .maxTorque(bike.getMaxTorque())
                .power(bike.getPower())
                .tankCapacity(bike.getTankCapacity())
                .topSpeed(bike.getTopSpeed())
                .transmission(bike.getTransmission())
                .category(bike.getCategory())
                .build();

        return bike;
    }

    @Override
    public List<Bike> fetchByCategory(Integer categoryId) {
        List<Bike> allBikes = bikeRepo.findAllByCategory(categoryId).orElseThrow(()->new RuntimeException("Not Found"));

        allBikes=listMapping(allBikes);

        List<Bike> requiredBikes = new ArrayList<>();
        for (int i=0; i<8 && i<allBikes.size(); i++){
            requiredBikes.add(allBikes.get(i));
        }
        return requiredBikes;
    }

    @Override
    public List<Bike> similarBikes(Integer categoryId, Integer bikeId) {
        List<Bike> allBikes = bikeRepo.findAllByCategory(categoryId).orElseThrow(()->new RuntimeException("Not Found"));

        for (int i=0; i<allBikes.size(); i++){
            if (Objects.equals(allBikes.get(i).getId(), bikeId)){
                allBikes.remove(i);
                break;
            }
        }

        Collections.shuffle(allBikes);

        allBikes=listMapping(allBikes);

        List<Bike> requiredBikes = new ArrayList<>();
        for (int i=0; i<4 && i<allBikes.size(); i++){
            requiredBikes.add(allBikes.get(i));
        }
        return requiredBikes;
    }

    @Override
    public List<Bike> fetchAllByCategory(Integer categoryId) {
        return listMapping(bikeRepo.findAllByCategory(categoryId).orElseThrow(()->new RuntimeException("Not Found")));
    }

    @Override
    public List<Bike> fetchMostRented() {
        return listMapping(bikeRepo.findMostRented().orElseThrow(()->new RuntimeException("Not Found")));
    }

    public List<Bike> listMapping(List<Bike> list){
        Stream<Bike> allBikesWithImage=list.stream().map(bike ->
                Bike.builder()
                        .id(bike.getId())
                        .bikeName(bike.getBikeName())
                        .brandName(bike.getBrandName())
                        .availableNo(bike.getAvailableNo())
                        .rentedNumber(bike.getRentedNumber())
                        .bikeImageBase64(getImageBase64(bike.getBikeImage()))
                        .priceDay(bike.getPriceDay())
                        .mileage(bike.getMileage())
                        .maxTorque(bike.getMaxTorque())
                        .power(bike.getPower())
                        .tankCapacity(bike.getTankCapacity())
                        .topSpeed(bike.getTopSpeed())
                        .transmission(bike.getTransmission())
                        .category(bike.getCategory())
                        .build()
        );
        list = allBikesWithImage.toList();
        return list;
    }

    public String getImageBase64(String fileName) {
        if (fileName!=null) {
            String filePath = System.getProperty("user.dir")+"/images/bikes/";
            File file = new File(filePath + fileName);
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }
}
