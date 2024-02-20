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

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity{

    @Column(name = "comment", length = 50, nullable = false)
    private String comment;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;
    @Column(columnDefinition = "datetime default NOW()")
    private String createdAt;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
