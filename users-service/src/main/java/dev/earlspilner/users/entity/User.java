package dev.earlspilner.users.entity;

import dev.earlspilner.users.security.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static dev.earlspilner.users.security.UserRole.ROLE_USER;

/**
 * @author Alexander Dudkin
 */
@Entity @Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "full_name", nullable = false)
    private String name;

    @Setter
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name = "created_utc", nullable = false, updatable = false)
    private Instant createdUtc;

    @Setter
    @Column(name = "updated_utc")
    private Instant updatedUtc;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    private List<UserRole> roles = Collections.singletonList(ROLE_USER);

    @PrePersist
    protected void onCreate() {
        this.createdUtc = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedUtc = Instant.now();
    }

    public User() {
    }

    public User(Integer id, String name, String email, String password,
                Instant createdUtc, Instant updatedUtc, List<UserRole> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdUtc = createdUtc;
        this.updatedUtc = updatedUtc;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdUtc=" + createdUtc +
                ", updatedUtc=" + updatedUtc +
                ", roles=" + roles +
                '}';
    }

}
