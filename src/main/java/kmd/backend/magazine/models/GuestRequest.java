package kmd.backend.magazine.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Table(name = "guest_request") 
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
public class GuestRequest extends BaseEntity{

    public enum Status {
        PENDING, ACTIVE, REJECTED, EXPRIRED
    }

    @Column(name = "email", length = 255, nullable = true)
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public GuestRequest() {
        this.status = Status.PENDING; // Set default value to PENDING
    }

    @Column(name = "status_date", nullable = true)
    private String statusDate;

    public String getStatusAsString() {
        return status != null ? status.name() : null;
    }
    
    @Column(name = "faculty_id", nullable = false)
    private int facultyId;

    @Column(name = "user_id", nullable = true)
    private int userId;

    
}
