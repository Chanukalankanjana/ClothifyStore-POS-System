package edu.icet.demo.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String id;
    private String cusId;
    private Date date;
    private double amount;

}
