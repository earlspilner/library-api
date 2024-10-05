package dev.earlspilner.users.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static dev.earlspilner.users.model.UserRole.ROLE_VISITOR;

/**
 * @author Alexander Dudkin
 */
@Entity
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "full_name", nullable = false)
    private String name;

    @Setter
    @Column(name = "username", unique = true, nullable = false)
    private String username;

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
    @Column(name = "user_role")
    private List<UserRole> roles = Collections.singletonList(ROLE_VISITOR);

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

    public User(Integer id, String name, String username, String email,
                String password, Instant createdUtc, Instant updatedUtc, List<UserRole> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdUtc = createdUtc;
        this.updatedUtc = updatedUtc;
        this.roles = roles;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return name.equals(user.name) && username.equals(user.username) && email.equals(user.email) && password.equals(user.password) && createdUtc.equals(user.createdUtc) && Objects.equals(updatedUtc, user.updatedUtc) && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + createdUtc.hashCode();
        result = 31 * result + Objects.hashCode(updatedUtc);
        result = 31 * result + roles.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdUtc=" + createdUtc +
                ", updatedUtc=" + updatedUtc +
                ", roles=" + roles +
                '}';
    }

}
