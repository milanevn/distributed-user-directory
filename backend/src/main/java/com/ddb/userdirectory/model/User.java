package com.ddb.userdirectory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String country;

    public User(String username, String email, String country) {
        this.username = username;
        this.email = email;
        this.country = country;
    }

    // Giữ constructor 3 tham số vì khi tạo user mới mình chưa có id.

}
