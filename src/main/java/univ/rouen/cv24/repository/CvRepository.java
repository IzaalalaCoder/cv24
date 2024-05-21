package univ.rouen.cv24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.rouen.cv24.entity.Cv;
import univ.rouen.cv24.enums.GenreType;

@Repository
public interface CvRepository extends JpaRepository<Cv, Integer> {

    boolean existsByIdentiteGenreAndIdentiteNomAndIdentitePrenomAndIdentiteTel(
            GenreType genre, String nom, String prenom, String tel);
}