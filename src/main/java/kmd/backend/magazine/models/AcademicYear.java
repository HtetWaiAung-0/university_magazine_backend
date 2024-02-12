package kmd.backend.magazine.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYear extends BaseEntity {

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "delet_status", columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "last_submit_date", nullable = false)
    private String lastSubmitDate;
    
    @Column(name = "end_date", nullable = false)
    private String endDate;
}
