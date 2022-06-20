package lt.bit.products.store.service;

import lt.bit.products.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface ProductItemsRepository extends JpaRepository<ProductItems, Integer> {

}


//@Modifying(clearAutomatically = true)
// @Query(value = "DELETE  FROM store_items WHERE product_id = ?1", nativeQuery = true)
// void deleteStoreItems( Integer productId);
