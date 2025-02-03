package kz.Aseke.Again.controller;

import kz.Aseke.Again.beans.TestA;
import kz.Aseke.Again.model.AuthorModel;
import kz.Aseke.Again.model.GenreModel;
import kz.Aseke.Again.model.MusicModel;
import kz.Aseke.Again.repository.AuthorRepository;
import kz.Aseke.Again.repository.GenreRepository;
import kz.Aseke.Again.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MusicRepository musicRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping(value = "/")
    public String indexPage(Model model,
                            @RequestParam(name = "key", required = false) String key) {
        if(key != null) {
            List<MusicModel> musicModelList = musicRepository.searchMusics("%"+key+"%");
            model.addAttribute("muzikalar", musicModelList);
        }
        else{
            List<MusicModel> musicModelList = musicRepository.findAllByDurationGreaterThanOrderByDurationDesc(0);
            model.addAttribute("muzikalar", musicModelList);
        }
        return "index";
    }

    @PostMapping(value = "/add-music")
    public String addMusic(MusicModel music){
        musicRepository.save(music);
        return "redirect:/";
    }

    @GetMapping(value = "add-music")
    public String addMusicPage(Model model){
        List<AuthorModel> authorModelList = authorRepository.findAll();
        model.addAttribute("authors", authorModelList);
        return "add-music";
    }

    @GetMapping(value = "/details/{musicId}")
    public String musicDetails(@PathVariable(name = "musicId") Long id, Model model) {
            MusicModel music = musicRepository.findById(id).orElse(null);
            model.addAttribute("muzyka", music);

            List<AuthorModel> authorModelList = authorRepository.findAll();
            model.addAttribute("authors", authorModelList);

            List<GenreModel> genreModelList = genreRepository.findAll();
            genreModelList.removeAll(music.getGenres());
            model.addAttribute("genres", genreModelList);

            return "details";
    }

    @PostMapping(value = "/save-music")
    public String saveMusic(MusicModel music){
        musicRepository.save(music);
        return "redirect:/";
    }

    @PostMapping(value = "/delete-music")
    public String deleteMusic(@RequestParam(name = "id") Long id){
        musicRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping(value = "assign-genre")
    public String assignGenre(@RequestParam(name = "music_id") Long musicId,
                             @RequestParam(name = "genre_id") Long genreId){

        MusicModel music = musicRepository.findById(musicId).orElseThrow();
        GenreModel genre = genreRepository.findById(genreId).orElseThrow();

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

        return "redirect:/details/" + musicId;
    }
    @PostMapping(value = "unassign-genre")
    public String unassignGenre(@RequestParam(name = "music_id") Long musicId,
                              @RequestParam(name = "genre_id") Long genreId){

        MusicModel music = musicRepository.findById(musicId).orElseThrow();
        GenreModel genre = genreRepository.findById(genreId).orElseThrow();

        if(music.getGenres() != null && !music.getGenres().isEmpty()){
            music.getGenres().remove(genre);
        }

        musicRepository.save(music);

        return "redirect:/details/" + musicId;
    }

}
