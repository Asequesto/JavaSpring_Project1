package kz.Aseke.Again.services;

import kz.Aseke.Again.model.GenreModel;
import kz.Aseke.Again.model.MusicModel;
import kz.Aseke.Again.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final GenreService genreService;

    public List<MusicModel> searchMusic(String key){

        if(key != null) {
            return musicRepository.searchMusics("%"+key+"%");
        }
        else{
            return musicRepository.findAllByDurationGreaterThanOrderByDurationDesc(0);
        }
    }

    public MusicModel addMusic(MusicModel music){
        return musicRepository.save(music);
    }

    public MusicModel getMusic(Long id){
        return musicRepository.findById(id).orElse(null);
    }

    public MusicModel saveMusic(MusicModel music){
        return musicRepository.save(music);
    }

    public void assignGenre(Long musicId, Long genreId){

        MusicModel music = getMusic(musicId);
        GenreModel genre = genreService.getGenreById(genreId);

        if(music.getGenres() != null && !music.getGenres().isEmpty()){
            if(!music.getGenres().contains(genre)){
                music.getGenres().add(genre);
            }
        }else{
            List<GenreModel> genreModelList = new ArrayList<>();
            genreModelList.add(genre);
            music.setGenres(genreModelList);
        }

        musicRepository.save(music);

    }

    public void unassignGenre(Long musicId, Long genreId){

        MusicModel music = getMusic(musicId);
        GenreModel genre = genreService.getGenreById(genreId);

        if(music.getGenres() != null && !music.getGenres().isEmpty()){
            music.getGenres().remove(genre);
        }

        musicRepository.save(music);
    }

    public void deleteMusic(Long id){
        musicRepository.deleteById(id);
    }

}
