package kmd.backend.magazine.models;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor

public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


}