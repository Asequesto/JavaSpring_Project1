package kz.Aseke.Again.repository;


import kz.Aseke.Again.model.MusicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.multi.MultiScrollBarUI;
import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<MusicModel, Long> {

    List<MusicModel> findAllByDurationGreaterThanOrderByDurationDesc(int duration);

    @Query(value = "" +
            "Select mm " +
            "From MusicModel mm " +
            "Where mm.duration > 0 And Lower(mm.name) Like Lower(:searchName)" +
            "Order By mm.duration Desc")
    List<MusicModel> searchMusics(@Param("searchName") String musicName);

}
