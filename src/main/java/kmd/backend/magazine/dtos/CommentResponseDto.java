package kmd.backend.magazine.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private int id;
    private String comment;
    private UserResponseDto user;
    private ArticelResponseDto article;
    private String createdAt;

}
    

