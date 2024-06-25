package edu.icet.demo.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String id;
    private String cusId;
    private String status;
    private String date;
    private String amount;

}
