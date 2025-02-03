package kz.Aseke.Again.repository;


import kz.Aseke.Again.model.GenreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel,Long> {

}
