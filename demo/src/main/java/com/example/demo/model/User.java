package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @NotEmpty(message = "Имя пользователя должно быть заполнено")
    @Size(min = 2, max = 16, message = "Имя пользователя должно содержать от 2 до 16 символов")
    @Column
    private String name;

    @NotEmpty(message = "Email должен быть заполнен")
    @Email(message = "Email должен быть валидным")
    @Column
    private String email;
    @NotNull(message = "Возраст должен быть заполнен")
    @Min(value = 6, message = "Возраст должен быть от 6 лет")
    @Column
    private int age;

    @NotNull(message = "OwnerId должен быть заполнен")
    @Min(value = 8, message = "OwnerId должен быть от 8 символов")
    @Column
    private long vkOwnerId;
    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 4, message = "Пароль должен содержать от 4 символов")
    @Column
    private String password;

    @Column
    private int countOfVideo;

    @NotEmpty(message = "Роль должна быть заполнена")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public User() {

    }

    public User(String name, String email, int age, long vkOwnerId, String password, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.vkOwnerId = vkOwnerId;
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getVkOwnerId() {
        return vkOwnerId;
    }

    public void setVkOwnerId(Long vkOwnerId) {
        this.vkOwnerId = vkOwnerId;
    }

    public int getCountOfVideo() {
        return countOfVideo;
    }

    public void setCountOfVideo(int countOfVideo) {
        this.countOfVideo = countOfVideo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

