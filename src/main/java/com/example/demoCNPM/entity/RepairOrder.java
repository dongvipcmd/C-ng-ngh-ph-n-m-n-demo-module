package com.example.demoCNPM.entity;


import com.example.demoCNPM.enums.RepairStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "repair_order")
public class RepairOrder {

    @Id
    @Column(name = "repair_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime receivedDate;

    private String description; // tình trạng xe

    private String note;

    @Enumerated(EnumType.STRING)
    private RepairStatus status;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "receptionist_id")
    private Employee receptionist; // tiếp tân nhận xe

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Employee technician; // kỹ thuật báo giá

    @OneToMany(mappedBy = "repairOrder", cascade = CascadeType.ALL)
    private List<WorkOrderItem> items = new ArrayList<>();
}
