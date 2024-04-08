package kmd.backend.magazine.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import kmd.backend.magazine.models.GuestRequest;

import java.util.List;

public interface GuestRequestRepo extends JpaRepository<GuestRequest, Integer> {
    List<GuestRequest> findByStatusAndEmail(GuestRequest.Status status, String email);
    List<GuestRequest> findByStatus(GuestRequest.Status status);
    List<GuestRequest> findByUserIdAndStatusAndStatusDate(int userId, GuestRequest.Status status, String createdDate);
}