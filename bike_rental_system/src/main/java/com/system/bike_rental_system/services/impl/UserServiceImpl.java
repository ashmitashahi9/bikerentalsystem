package com.system.bike_rental_system.services.impl;

import com.system.bike_rental_system.entity.User;
import com.system.bike_rental_system.exception.AppException;
import com.system.bike_rental_system.pojo.PasswordChangePojo;
import com.system.bike_rental_system.pojo.UserPojo;
import com.system.bike_rental_system.repo.BookingRepo;
import com.system.bike_rental_system.repo.EmailCredRepo;
import com.system.bike_rental_system.repo.UserRepo;
import com.system.bike_rental_system.services.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JavaMailSender getJavaMailSender;
    private final EmailCredRepo emailCredRepo;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;
    private final UserRepo userRepo;
    private final BookingRepo bookingRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\images\\user-documents\\";

    @Override
    public void saveUser(UserPojo userPojo) {
        User user= new User();
        user.setEmail(userPojo.getEmail());
        user.setFName(userPojo.getFName());
        user.setLName(userPojo.getLName());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userPojo.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateGeneral(UserPojo userPojo) throws IOException {
        User user= fetchById(userPojo.getId());
        user.setFName(userPojo.getFName());
        user.setLName(userPojo.getLName());
        user.setMobileNo(userPojo.getMobileNo());
        user.setAddress(userPojo.getAddress());

        if(!Objects.equals(userPojo.getImage().getOriginalFilename(), "")){
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY+"profile-picture\\", userPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, userPojo.getImage().getBytes());

            user.setImage("profile-picture\\"+userPojo.getImage().getOriginalFilename());
        }

        if(user.getLicense()!=null &&
                user.getCitizenshipF()!=null &&
                user.getCitizenshipB()!=null) {
            user.setStatus("Submitted");}
        else {
            user.setStatus("Not Submitted");
        }

        userRepo.save(user);
    }

    public void updateDocs(UserPojo userPojo) throws IOException {
        User user = fetchById(userPojo.getId());

        if(!Objects.equals(userPojo.getCitizenshipF().getOriginalFilename(), "")){
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY+"citizenship-front\\", userPojo.getCitizenshipF().getOriginalFilename());
            Files.write(fileNameAndPath, userPojo.getCitizenshipF().getBytes());

            user.setCitizenshipF("citizenship-front\\"+userPojo.getCitizenshipF().getOriginalFilename());
        }

        if(!Objects.equals(userPojo.getCitizenshipB().getOriginalFilename(), "")){
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY+"citizenship-back\\", userPojo.getCitizenshipB().getOriginalFilename());
            Files.write(fileNameAndPath, userPojo.getCitizenshipB().getBytes());

            user.setCitizenshipB("citizenship-back\\"+userPojo.getCitizenshipB().getOriginalFilename());
        }

        if(!Objects.equals(userPojo.getLicense().getOriginalFilename(), "")){
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY+"license\\", userPojo.getLicense().getOriginalFilename());
            Files.write(fileNameAndPath, userPojo.getLicense().getBytes());

            user.setLicense("license\\"+userPojo.getLicense().getOriginalFilename());
        }

        if(user.getMobileNo()!=null &&
                user.getAddress()!=null &&
                user.getImage()!=null)
        {user.setStatus("Submitted");}
        else {
            user.setStatus("Rejected");
        }

        userRepo.save(user);
    }

    @Override
    public List<User> fetchAll() {
        List<User> megaList = new ArrayList<>();
        megaList.addAll(listMapping(userRepo.allUsers("Submitted")));
        megaList.addAll(listMapping(userRepo.allUsers("Rejected")));
        megaList.addAll(listMapping(userRepo.allUsers("Approved")));
        return megaList;
    }

    @Override
    public User fetchById(Integer id) {
        User user = userRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));

        user= User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fName(user.getFName())
                .lName(user.getLName())
                .mobileNo(user.getMobileNo())
                .address(user.getAddress())
                .password(user.getPassword())
                .imageBase64(getImageBase64(user.getImage()))
                .image(user.getImage())
                .citizenshipF(user.getCitizenshipF())
                .citizenshipFBase64(getImageBase64(user.getCitizenshipF()))
                .citizenshipB(user.getCitizenshipB())
                .citizenshipBBase64(getImageBase64(user.getCitizenshipB()))
                .license(user.getLicense())
                .licenseBase64(getImageBase64(user.getLicense()))
                .status(user.getStatus())
                .build();

        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException("Invalid User email", HttpStatus.BAD_REQUEST));

        user= User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fName(user.getFName())
                .lName(user.getLName())
                .mobileNo(user.getMobileNo())
                .address(user.getAddress())
                .imageBase64(getImageBase64(user.getImage()))
                .image(user.getImage())
                .citizenshipF(user.getCitizenshipF())
                .citizenshipFBase64(getImageBase64(user.getCitizenshipF()))
                .citizenshipB(user.getCitizenshipB())
                .citizenshipBBase64(getImageBase64(user.getCitizenshipB()))
                .license(user.getLicense())
                .licenseBase64(getImageBase64(user.getLicense()))
                .status(user.getStatus())
                .build();

        return user;
    }

    @Override
    public void deleteAccount(PasswordChangePojo passwordChangePojo) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> optionalUser = userRepo.findByEmail(passwordChangePojo.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (encoder.matches(passwordChangePojo.getOldPassword(), user.getPassword())) {
                bookingRepo.deleteBookingsByUserId(userRepo.findById(user.getId()).orElseThrow());
                userRepo.deleteById(user.getId());
            }
        }
    }

    @Override
    public void changePassword(PasswordChangePojo passwordChangePojo) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> optionalUser = userRepo.findByEmail(passwordChangePojo.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (encoder.matches(passwordChangePojo.getOldPassword(), user.getPassword())) {
                if (passwordChangePojo.getNewPassword().equals(passwordChangePojo.getRepeatPassword())) {
                    user.setPassword(encoder.encode(passwordChangePojo.getNewPassword()));
                    userRepo.save(user);
                } else {
                    throw new AppException("New Password doesn't match.", HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new AppException("Old Password doesn't match existing password.", HttpStatus.BAD_REQUEST);
            }
        }

    }

    public String getImageBase64(String fileName) {
        if (fileName!=null) {
            String filePath = System.getProperty("user.dir") + "/images/user-documents/";
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

    public List<User> listMapping(List<User> list){
        Stream<User> allUsersWithImage=list.stream().map(user ->
                User.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .fName(user.getFName())
                        .lName(user.getLName())
                        .mobileNo(user.getMobileNo())
                        .address(user.getAddress())
                        .imageBase64(getImageBase64(user.getImage()))
                        .image(user.getImage())
                        .citizenshipF(user.getCitizenshipF())
                        .citizenshipFBase64(getImageBase64(user.getCitizenshipF()))
                        .citizenshipB(user.getCitizenshipB())
                        .citizenshipBBase64(getImageBase64(user.getCitizenshipB()))
                        .license(user.getLicense())
                        .licenseBase64(getImageBase64(user.getLicense()))
                        .status(user.getStatus())
                        .build()
        );

        list = allUsersWithImage.toList();
        return list;
    }

    @Override
    public void sendEmail() {
        try {
            Map<String, String> model = new HashMap<>();

            MimeMessage message = getJavaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template template = emailConfig.getTemplate("emailTemp.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessageHelper.setTo("sendfrom@yopmail.com");
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject("Registration");
            mimeMessageHelper.setFrom("sendTo@yopmail.com");

            taskExecutor.execute(new Thread() {
                public void run() {
                    getJavaMailSender.send(message);
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public String updateResetPassword(String email) {
        User user = (User) userRepo.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Invalid User email"));
        String updated_password = generatePassword();
        try {
            userRepo.updatePassword(updated_password, email);
            return "CHANGED";
        } catch (Exception e){
            e.printStackTrace();
        }
        return "ds";
    }

    public String generatePassword() {
        int length = 8;
        String password = "";
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            int randomChar = (int)(r.nextInt(94) + 33);
            char c = (char)randomChar;
            password += c;
        }
        return password;
    }

    private void sendPassword(String email, String password ){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your new password is:");
        message.setText(password);
        getJavaMailSender.send(message);
    }
    @Override
    public void processPasswordResetRequest(String email){
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            String password = generatePassword();
            sendPassword(email, password);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(password);
            user.setPassword(encodePassword);
            userRepo.save(user);
        }
    }
}
