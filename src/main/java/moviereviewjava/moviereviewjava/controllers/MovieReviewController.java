package moviereviewjava.moviereviewjava.controllers;

import moviereviewjava.moviereviewjava.models.MovieReview;
import moviereviewjava.moviereviewjava.models.User;
import moviereviewjava.moviereviewjava.models.UserDetails;
import moviereviewjava.moviereviewjava.repositories.MovieReviewRepository;
import moviereviewjava.moviereviewjava.repositories.UserRepository;
import moviereviewjava.moviereviewjava.services.MovieReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import java.util.List;

    @Controller
    public class MovieReviewController {

        @Autowired
        private MovieReviewRepository reviewRepository;
        @Autowired
        private MovieReviewService movieReviewService;

        @Autowired
        private UserRepository userRepository;


        @GetMapping("/home")
        public String getAllMovieReviews(
                @RequestParam(value = "page", defaultValue = "0") Integer page,
                @RequestParam(value = "size", defaultValue = "4") Integer size,
                Model model) {

            if (page < 0) {
                page = 0;
            }
            if (size <= 0) {
                size = 4;
            }

            Page<MovieReview> movieReviewPage = movieReviewService.findPaginated(page, size);

            model.addAttribute("movie_reviews", movieReviewPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", movieReviewPage.getTotalPages());
            model.addAttribute("size", size);

            return "home";
        }

        @GetMapping("/profile")
        public String getCurrentUserReviews(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            model.addAttribute("movieReview", new MovieReview());
            List<MovieReview> userReviews = reviewRepository.findByUserId(userDetails.getId());
            model.addAttribute("user_reviews", userReviews);
            return "profile";
        }


        @PostMapping("/movie_reviews/add")
        public String addReview(@Valid MovieReview review, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("movie_reviews", reviewRepository.findAll());
                return "profile";
            }
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen"));
            review.setUser(user);
            reviewRepository.save(review);
            return "redirect:/profile";
        }
        @PostMapping("/movie_reviews/delete/{id}")
        public String deleteReview(@PathVariable Long id) {
            reviewRepository.deleteById(id);
            return "redirect:/profile";
        }

        @PostMapping("/movie_reviews/edit")
        public String editReview(@ModelAttribute MovieReview movieReview, @AuthenticationPrincipal UserDetails userDetails) {
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen"));
            MovieReview existingReview = reviewRepository.findByIdAndUser(movieReview.getId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Recenzija nije pronađena ili nije autorizirana"));

            existingReview.setMovieName(movieReview.getMovieName());
            existingReview.setMovieDirector(movieReview.getMovieDirector());
            existingReview.setReview(movieReview.getReview());
            existingReview.setReviewText(movieReview.getReviewText());

            reviewRepository.save(existingReview);
            return "redirect:/profile";
        }
    }


