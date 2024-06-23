package edu.icet.demo.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String address;
}
