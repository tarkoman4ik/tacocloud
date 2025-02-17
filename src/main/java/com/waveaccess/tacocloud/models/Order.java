package com.waveaccess.tacocloud.models;

import com.waveaccess.tacocloud.models.Taco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="taco_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message="Необходимо заполнить имя")
    private String name;
    @NotBlank(message="Необходимо заполнить улицу")
    private String street;
    @NotBlank(message="Необходимо заполнить город")
    private String city;
    @NotBlank(message="Необходимо заполнить область")
    private String state;
    @NotBlank(message="Необходимо заполнить индекс")
    private String zip;
    @NotBlank(message="Необходимо заполнить номер карты")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Обязательный формат даты MM/YY")
    private String ccExpiration;
    @Digits(integer = 3,fraction = 0,message = "Некорректный CVV")
    private String ccCVV;

    @ToString.Exclude
    @ManyToMany(targetEntity = Taco.class)
    @JoinTable(name="Taco_Order_Tacos", joinColumns = { @JoinColumn(name="tacoOrder") },
            inverseJoinColumns = { @JoinColumn(name="taco") })
    private List<Taco> tacos = new ArrayList<>();



    private Date placedAt;

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    @PrePersist
    void placedAt(){
        this.placedAt = new Date();
    }
}
