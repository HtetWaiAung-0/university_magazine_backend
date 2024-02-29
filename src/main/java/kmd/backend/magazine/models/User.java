package kmd.backend.magazine.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(columnDefinition = "smallint")
    private int deleteStatus;

    @Column(name = "role", length = 50, nullable = false)
    private String role;

    @Column(name = "profile_photo", length = 50, nullable = false)
    private String profilePhoto; 

    @ManyToOne()
    @JoinColumn(name="faculty_id")
    private Faculty faculty;


}
