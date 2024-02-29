package kmd.backend.magazine.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Table;

@Table(name = "faculty") 
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Faculty extends BaseEntity {
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(columnDefinition = "smallint")
    private int deleteStatus;
}
