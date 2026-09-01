package com.moviebase.movies;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class MovieService {

    private final MovieRepository repository;


    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Page<MovieResponseDto> getAllMovies(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Movie> moviePage = repository.findAll(pageable);

        Page<MovieResponseDto> responsePage = moviePage.map(movie -> {
            MovieResponseDto dto = new MovieResponseDto();
            BeanUtils.copyProperties(movie, dto);
            return dto;
        });

        return responsePage;
    }

    public Movie searchMovie(String title) {

        return repository.findByTitleIgnoreCase(title);
    }

    public String addMovie(MovieCreateDto dto) {

        Movie newMovie = new Movie();
        BeanUtils.copyProperties(dto, newMovie);
        repository.save(newMovie);
        return "Movie '" + newMovie.getTitle() + "' Successfully added";
    }


    public String addAllMovies(List<MovieCreateDto> dtos) {

        List<Movie> moviesToSave = new ArrayList<>();


        for (MovieCreateDto dto : dtos) {
            Movie movie = new Movie();
            BeanUtils.copyProperties(dto, movie);
            moviesToSave.add(movie);
        }


        repository.saveAll(moviesToSave);

        return "Movie list is saved";
    }



    public String deleteMovie(Long id){
        repository.deleteById(id);
        return "Movie deleted";
    }



    public String updateMovie(Long id, MovieCreateDto updatedMovieDto){

        Movie existingMovie = repository.findById(id).orElse(null);
        if(existingMovie != null){

            copyProperties(updatedMovieDto, existingMovie, "id");

            repository.save(existingMovie);
            return "Movie " + existingMovie.getTitle() + "successfully updated";
        }

        return "Update unsuccessful";

    }

    public List<Movie> getTopMovies(double minRating){

        return repository.findByRatingGreaterThan(minRating);
    }


}
