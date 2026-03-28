package com.example.demoCNPM.entity;
import com.example.demoCNPM.enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "work_order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private Double unitPrice;


    @Enumerated(EnumType.STRING)
    private ItemType type;

    // repair_order
    @ManyToOne
    @JoinColumn(name = "repair_order_id")
    private RepairOrder repairOrder;

    // service
    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceClass service;

    // part
    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;
}
