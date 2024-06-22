package edu.icet.demo.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeData {
    private String id;
    private String name;
    private String email;
    
    private static EmployeeData instance;
    private EmployeeData(){}
    
    public static EmployeeData getInstance(){
        if (instance==null){
            return instance=new EmployeeData();
        }
        return instance;
    }
}
