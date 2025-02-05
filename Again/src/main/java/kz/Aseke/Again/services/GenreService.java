package kz.Aseke.Again.services;


import kz.Aseke.Again.model.GenreModel;
import kz.Aseke.Again.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreModel> getGenre(){
        return genreRepository.findAll();
    }

    public GenreModel getGenreById(Long id){
        return genreRepository.findById(id).orElse(null);
    }

}
