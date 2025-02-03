package kz.Aseke.Again.model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_musics")
@Getter
@Setter
public class MusicModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "music_name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @ManyToOne()
    private AuthorModel authorModel;

    @ManyToMany
    private List<GenreModel> genres;


    @PrePersist
    public void checkNegative(){
        if(this.duration <= 0){
            this.duration = 180;
        }
    }

}
