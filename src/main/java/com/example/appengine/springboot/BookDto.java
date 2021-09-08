package com.example.appengine.springboot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long id;

    private String title;
    private String author;
    private Integer publishYear;
    private String imageUrl;
    private Boolean finished;
    private Integer rating;
}
