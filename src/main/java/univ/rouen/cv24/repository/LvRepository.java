package univ.rouen.cv24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.rouen.cv24.entity.Lv;

@Repository
public interface LvRepository extends JpaRepository<Lv, Integer> {
}