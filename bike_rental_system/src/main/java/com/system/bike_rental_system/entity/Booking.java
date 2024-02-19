package com.system.bike_rental_system.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @SequenceGenerator(name = "cms_user_seq_gen", sequenceName = "cms_user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "cms_user_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_bikeId"))
    private Bike bikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_userId"))
    private User userId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column
    private Date bookingDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column
    private Date releaseDate;

    @Column(columnDefinition = "varchar(255) default 'Booked'")
    private String status;

}
