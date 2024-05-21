package univ.rouen.cv24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.rouen.cv24.entity.Identite;

@Repository
public interface IdentiteRepository extends JpaRepository<Identite, Integer> {
}
