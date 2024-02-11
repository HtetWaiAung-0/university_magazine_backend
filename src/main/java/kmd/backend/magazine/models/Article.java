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

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "file_name", length = 50, nullable = false)
    private String fileName;

    @Column(name = "cover_photo", length = 50, nullable = false)
    private String coverPhoto;

    @Column(name = "approve_status", columnDefinition = "tinyint(1) default 0")
    private boolean approve;

    @Column(name = "delete_status", columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @Column(name = "created_date", columnDefinition = "timestamp default current_timestamp")
    private String createdDate;

    @Column(name = "updated_date", columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private String updatedDate;


    
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}

