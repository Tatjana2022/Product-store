package lt.bit.products.store.service;

import lt.bit.products.store.model.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final ProductItemsRepository productItemsRepository;

    public ProductService(ProductRepository repository, ProductItemsRepository productItemsRepository) {
        this.repository = repository;
        this.productItemsRepository = productItemsRepository;
    }

    public List<Product> findProducts() {
        return repository.findAll();
    }

    public Product findProduct(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    public void deleteProduct(Integer id) {
        repository.deleteStoreItems(id);
        repository.deleteById(id);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public ProductItems getProductItems(Integer productId) {
        return productItemsRepository.findById(productId).orElse(null);
    }
}


