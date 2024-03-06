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

@Table(name = "article") 
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "file_name", length = 255)
    private String fileName; 

    @Column(name = "file_type", length = 255)
    private String fileType;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(name = "cover_photo_name", length = 255)
    private String coverPhotoName; 

    @Column(name = "cover_photo_type", length = 255)
    private String coverPhotoType;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "cover_photo_data")
    private byte[] coverPhotoData;

    @Column(name = "approve_status")
    private boolean approveStatus;

    @Column(name = "delete_status", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean deleteStatus;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "updated_date")
    private String updatedDate;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;
    
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}

