package com.moviebase.movies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Включаем магию Mockito
class MovieServiceTest {

    @Mock
    private MovieRepository repository; // Создаем игрушечный репозиторий (заглушку)

    @InjectMocks
    private MovieService service; // Создаем наш реальный сервис и подкидываем ему игрушечный репозиторий

    @Test
    void searchMovie_ShouldReturnMovie_WhenTitleExists() {
        // 1. ПОДГОТОВКА (Arrange)
        // Создаем фейковый фильм, который "вернет" база
        Movie fakeMovie = new Movie("The Matrix", 1999, 8.7);

        // Учим наш игрушечный репозиторий: "Когда тебя попросят найти 'The Matrix', верни fakeMovie"
        Mockito.when(repository.findByTitleIgnoreCase("The Matrix")).thenReturn(fakeMovie);

        // 2. ДЕЙСТВИЕ (Act)
        // Вызываем реальный метод нашего сервиса!
        Movie result = service.searchMovie("The Matrix");

        // 3. ПРОВЕРКА (Assert)
        // Проверяем, что сервис вернул правильные данные
        assertNotNull(result, "Фильм не должен быть null");
        assertEquals("The Matrix", result.getTitle(), "Названия должны совпадать");
        assertEquals(1999, result.getYear(), "Год должен совпадать");

        // Проверяем, что сервис действительно вызывал метод репозитория 1 раз
        Mockito.verify(repository, Mockito.times(1)).findByTitleIgnoreCase("The Matrix");
    }

    @Test
    void addMovie_ShouldSaveMovieAndReturnSuccessMessage(){
        MovieCreateDto dto = new MovieCreateDto();
        dto.setRating(8.8);
        dto.setYear(2010);
        dto.setTitle("Inception");

        String result = service.addMovie(dto);

        assertTrue(result.contains("успешно добавлен"));
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Movie.class));

    }

    @Test
    void updateMovie_ShouldReturnError_WhenMovieNotFound(){
        MovieCreateDto dto = new MovieCreateDto();
        dto.setRating(8.8);
        dto.setYear(2010);
        dto.setTitle("Inception");


        Mockito.when(repository.findById(99L)).thenReturn(java.util.Optional.empty());

        String response = service.updateMovie(99L, dto);

        assertEquals("Не удалось обновить фильм", response,"Должно вернуть null");

        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Movie.class));
    }

}