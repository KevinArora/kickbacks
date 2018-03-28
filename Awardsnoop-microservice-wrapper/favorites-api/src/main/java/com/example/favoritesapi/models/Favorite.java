package com.example.favoritesapi.models;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor@NoArgsConstructor@Getter@Setter
@Entity@Builder@Table(name="FAVORITES")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    public Favorite(String title, String description){
        this.title=title;
        this.description=description;
    }
}
