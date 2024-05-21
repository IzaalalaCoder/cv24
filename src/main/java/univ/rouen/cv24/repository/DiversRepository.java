package univ.rouen.cv24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.rouen.cv24.entity.Divers;

@Repository
public interface DiversRepository extends JpaRepository<Divers, Integer> {
}