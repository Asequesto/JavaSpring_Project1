package kz.Aseke.Again.controller;

import kz.Aseke.Again.model.AuthorModel;
import kz.Aseke.Again.model.GenreModel;
import kz.Aseke.Again.model.MusicModel;
import kz.Aseke.Again.services.AuthorService;
import kz.Aseke.Again.services.GenreService;
import kz.Aseke.Again.services.MusicService;
import lombok.RequiredArgsConstructor;
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

    private final MusicService musicService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping(value = "/")
    public String indexPage(Model model,
                            @RequestParam(name = "key", required = false) String key) {
        model.addAttribute("muzikalar", musicService.searchMusic(key));
        return "index";
    }

    @PostMapping(value = "/add-music")
    public String addMusic(MusicModel music){
        musicService.addMusic(music);
        return "redirect:/";
    }

    @GetMapping(value = "add-music")
    public String addMusicPage(Model model){
        model.addAttribute("authors", authorService.getAuthors());
        return "add-music";
    }

    @GetMapping(value = "/details/{musicId}")
    public String musicDetails(@PathVariable(name = "musicId") Long id, Model model) {

            MusicModel music = musicService.getMusic(id);
            model.addAttribute("muzyka", music);

            List<AuthorModel> author = authorService.getAuthors();
            model.addAttribute("authors", author);

            List<GenreModel> genre = genreService.getGenre();
            genre.removeAll(music.getGenres());
            model.addAttribute("genres", genre);

            return "details";
    }

    @PostMapping(value = "/save-music")
    public String saveMusic(MusicModel music){
        musicService.saveMusic(music);
        return "redirect:/";
    }

    @PostMapping(value = "/delete-music")
    public String deleteMusic(@RequestParam(name = "id") Long id){
        musicService.deleteMusic(id);
        return "redirect:/";
    }

    @PostMapping(value = "assign-genre")
    public String assignGenre(@RequestParam(name = "music_id") Long musicId,
                             @RequestParam(name = "genre_id") Long genreId){
        musicService.assignGenre(musicId, genreId);
        return "redirect:/details/" + musicId;
    }
    @PostMapping(value = "unassign-genre")
    public String unassignGenre(@RequestParam(name = "music_id") Long musicId,
                              @RequestParam(name = "genre_id") Long genreId){
        musicService.unassignGenre(musicId, genreId);
        return "redirect:/details/" + musicId;
    }

}
