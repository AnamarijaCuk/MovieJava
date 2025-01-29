package moviereviewjava.moviereviewjava.repositories;

import moviereviewjava.moviereviewjava.models.MovieReview;
import moviereviewjava.moviereviewjava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
    List<MovieReview> findByUserId(Long userId);
    Optional<MovieReview> findByIdAndUser(Long id, User user);
}