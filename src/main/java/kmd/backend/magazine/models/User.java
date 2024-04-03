package kmd.backend.magazine.models;
import java.sql.Types;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Table;

@Table(name = "users") 
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    public enum Role {
        ADMIN, STUDENT, MANAGER, COORDINATOR, GUEST 
    }

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "email", length = 255, nullable = true, unique = true)
    private String email;

    @Column(name = "delete_status", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean deleteStatus;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public String getRoleAsString() {
        return role != null ? role.name() : null;
    }

    @Column(name = "profile_photo_name", length = 255)
    private String profilePhotoName; 

    @Column(name = "profile_photo_type", length = 255)
    private String profilePhotoType;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "profile_photo_data")
    private byte[] profilePhotoData;

    @ManyToOne()
    @JoinColumn(name="faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Article> articles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

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

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
}
