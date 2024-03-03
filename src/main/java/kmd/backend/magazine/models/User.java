package kmd.backend.magazine.models;
import java.sql.Types;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
public class User extends BaseEntity {

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "delete_status", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean deleteStatus;

    @Column(name = "role", length = 50, nullable = false)
    private String role;

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


}
