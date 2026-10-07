package nbcamp.deliveryapp.Repository;

import nbcamp.deliveryapp.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByUserLoginIdOrMenuUserLoginId(String userLoginId, String menuUserLoginId);
}
