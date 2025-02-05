package kz.Aseke.Again.services;

import kz.Aseke.Again.model.AuthorModel;
import kz.Aseke.Again.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<AuthorModel> getAuthors(){
        return authorRepository.findAll();
    }

}
