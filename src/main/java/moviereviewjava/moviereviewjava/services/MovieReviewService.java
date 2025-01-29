package moviereviewjava.moviereviewjava.services;

import moviereviewjava.moviereviewjava.models.MovieReview;
import moviereviewjava.moviereviewjava.repositories.MovieReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieReviewService {

    @Autowired
    private MovieReviewRepository movieReviewRepository;

    public Page<MovieReview> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieReviewRepository.findAll(pageable);
    }

}
