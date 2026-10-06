package nbcamp.deliveryapp.Repository;

import nbcamp.deliveryapp.Entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    //isDeleted 상태값(true 또는 false)을 파라미터로 받아서 해당 목록 전체 조회
    List<Menu> findAllByIsDeleted(boolean isDeleted);
    Optional<Menu> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
