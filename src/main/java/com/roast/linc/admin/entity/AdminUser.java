package com.roast.linc.admin.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_users")
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // BCrypt ile hashlenmiş tutulacak

//    private String fullName;

    private boolean active = true;

    private LocalDateTime lastLogin;

    public AdminUser() {
    }

    // Getter ve Setter'lar (Lombok kullanmadığımız için manuel ekliyoruz)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   /* public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    */
}
