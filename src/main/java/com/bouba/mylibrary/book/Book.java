package com.bouba.mylibrary.book;

import com.bouba.mylibrary.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "isbn", unique = true)
    private String isbn;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "total_explaire")
    private Long totalExplaire;
    @Column(name = "author")
    private String author;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "code")
    private Category category;

}
