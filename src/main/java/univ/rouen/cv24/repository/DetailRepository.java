package univ.rouen.cv24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univ.rouen.cv24.entity.Detail;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Integer> {
}