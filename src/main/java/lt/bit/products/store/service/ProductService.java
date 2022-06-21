package lt.bit.products.store.service;

import java.util.List;
import lt.bit.products.store.model.Product;
import lt.bit.products.store.model.ProductItems;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

  private final ProductRepository repository;
  private final ProductItemsRepository productItemsRepository;

  public ProductService(ProductRepository repository,
      ProductItemsRepository productItemsRepository) {
    this.repository = repository;
    this.productItemsRepository = productItemsRepository;
  }

  public List<Product> findProducts() {
    return repository.findAll();
  }

  public long countProducts() {
    return repository.count();
  }

  public Product findProduct(Integer id) {
    return repository.findById(id).orElse(null);
  }

  public void deleteProduct(Integer id) {
//    productItemsRepository.deleteById(id);
    productItemsRepository.deleteAllByProductId(id);
    repository.deleteById(id);
  }

  public Product saveProduct(Product product) {
    return repository.save(product);
  }

  public ProductItems getProductItems(Integer productId) {
    return productItemsRepository.findById(productId).orElse(null);
  }
}
