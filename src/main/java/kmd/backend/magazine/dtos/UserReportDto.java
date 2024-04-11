package kmd.backend.magazine.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReportDto {
    int userId;
    String userName;
    int totalLoginCount;
}
