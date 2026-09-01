package com.moviebase.movies;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
public class MovieController {

    private final MovieService service;
    private final MovieScraperService scraperService;



    public MovieController(MovieService service, MovieScraperService scraperService) {
        this.service = service;
        this.scraperService = scraperService;
    }



    @GetMapping("/hello")
    public String sayHello() {
        return "Привет! Добро пожаловать в базу данных кино 🍿";
    }

    @GetMapping("/movies")
    public Page<MovieResponseDto> getAllMovies(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10" ) int size,
                                               @RequestParam(defaultValue = "id") String sortBy) {
        return service.getAllMovies(page, size, sortBy);
    }

    @GetMapping("/movies/search")
    public Movie searchMovie(@RequestParam String title) {
        return service.searchMovie(title);
    }

    @GetMapping("/movies/min-rating")
    public List<Movie> minRatingMovies(@RequestParam double minRating){
        return service.getTopMovies(minRating);
    }

    @PostMapping("/movies/add")
    public String addMovie(@Valid @RequestBody MovieCreateDto dto) {

        return service.addMovie(dto);

    }

    @PostMapping("/movies/add-all")
    public String addAllMovies(@Valid @RequestBody List<MovieCreateDto> dto){
        return service.addAllMovies(dto);

    }


    @DeleteMapping("/movies/delete")
    public String deleteMovie(@RequestParam Long id){
        return service.deleteMovie(id);

    }


    @PutMapping("/movies/update")
    public String updateMovie(@Valid @RequestParam Long id, @Valid @RequestBody MovieCreateDto updatedMovieDto){

        return service.updateMovie(id, updatedMovieDto);


    }

    @GetMapping("/movies/scrape")
    public String scrape() {
        return scraperService.scrapeWikipedia();
    }


}