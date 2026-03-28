package com.example.demoCNPM.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String licensePlate;// bien so xe

    private String color;

    private String brand;

    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


}
