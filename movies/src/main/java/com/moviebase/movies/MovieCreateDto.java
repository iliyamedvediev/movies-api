package com.moviebase.movies;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class MovieCreateDto {
    @NotBlank(message = "Название не может быть пустым")
    private String title;
    @Min(value = 1895, message = "Кино еще не изобрели!")
    private int year;
    @DecimalMin(value = "0.0") @DecimalMax(value = "10.0")
    private double rating;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }
}
