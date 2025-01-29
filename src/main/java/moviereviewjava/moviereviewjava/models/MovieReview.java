package moviereviewjava.moviereviewjava.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;


@Entity
@Table(name="movie_reviews")
public class MovieReview {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Size(min = 2, max = 50, message = "Naziv filma mora imati između 2 i 50 znakova.") // Anotator za provjeru duljine unesenog teksta.
        @Column(nullable = false, length = 50)
        private String movieName;

        @Size(min = 2, max = 50, message = "Filmski redatelj mora imati između 2 i 50 znakova.")
        @Column(nullable = false, length = 50)
        private String movieDirector;


        @Size(min = 5, max = 5000, message = "Tekst recenzije mora imati između 5 i 5000 znakova.")
        @Column(nullable = false, length = 5000)
        private String reviewText;

        @DecimalMin(value = "1.00", message = "Ocjena ne može biti manja od 1.")
        @DecimalMax(value = "5.00", message = "Ocjena ne može biti veća od 5.")
        @Digits(integer = 1, fraction = 2, message = "Ocjena ne smije imati više od 2 decimale.")
        private BigDecimal review;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;


        public MovieReview(String movieName, String movieDirector, String reviewText, BigDecimal review) {
                this.movieName = movieName;
                this.movieDirector = movieDirector;
                this.reviewText = reviewText;
                this.review = review;
        }

        public MovieReview() {
        }
        public Long getId() {
                return id;
        }
        public void setId(Long id) {
                this.id = id;
        }
        public String getMovieName() {
                return movieName;
        }
        public void setMovieName(String movieName) {
                this.movieName = movieName;
        }
        public String getMovieDirector() {
                return movieDirector;
        }
        public void setMovieDirector(String movieDirector) {
                this.movieDirector = movieDirector;
        }
        public String getReviewText() {
                return reviewText;
        }
        public void setReviewText(String reviewText) {
                this.reviewText = reviewText;
        }
        public BigDecimal getReview() {
                return review;
        }
        public void setReview(BigDecimal review) {
                this.review = review;
        }
        public void setUser(User user) {
                this.user = user;
        }
        public User getUser() {
                return user;
        }


}
