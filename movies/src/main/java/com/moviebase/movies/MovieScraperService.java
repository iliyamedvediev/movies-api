package com.moviebase.movies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class MovieScraperService {

    private final MovieService movieService; // Твой сервис

    public MovieScraperService(MovieService movieService) {
        this.movieService = movieService;
    }

    public String scrapeWikipedia() {
        try {
            String url = "https://ru.wikipedia.org/wiki/250_лучших_фильмов_по_версии_IMDb";
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .get();


            Elements allTables = doc.select("table");
            Element targetTable = null;


            for (Element table : allTables) {
                if (table.select("tr").size() > 200) {
                    targetTable = table;
                    System.out.println("Found the table, it's number of rows: " + table.select("tr").size());
                    break;
                }
            }

            if (targetTable == null) {
                return "Error can't find the table";
            }

            Elements rows = targetTable.select("tr");
            int savedCount = 0;


            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements columns = row.select("td");


                if (columns.size() >= 2) {
                    String title = columns.get(1).text();
                    String yearString = columns.get(2).text().replaceAll("[^0-9]", "");


                    if(!yearString.isEmpty()) {
                        int year = Integer.parseInt(yearString);
                        double rating = 9.0;

                        MovieCreateDto dto = new MovieCreateDto();
                        dto.setTitle(title);
                        dto.setYear(year);
                        dto.setRating(rating);

                        movieService.addMovie(dto);
                        savedCount++;
                    }
                }
            }
            return "Succesfully found and saved films: " + savedCount;

        } catch (Exception e) {
            // Если что-то сломается, мы увидим это в консоли!
            e.printStackTrace();
            return "Parsing error " + e.getMessage();
        }
    }
}