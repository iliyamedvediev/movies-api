package com.moviebase.movies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {



    Movie findByTitleIgnoreCase(String title);

    List<Movie> findByRatingGreaterThan(double minRating);

}