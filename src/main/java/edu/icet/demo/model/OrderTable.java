package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTable {
    private String itemCode;
    private String desc;
    private Integer qty;
    private Double unitPrice;
    private Double total;
    private Integer cartNum;
}
