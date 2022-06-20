package lt.bit.products.store.controller;

import lt.bit.products.store.model.Product;
import lt.bit.products.store.model.ProductRequest;
import lt.bit.products.store.service.ProductItems;
import lt.bit.products.store.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(ProductController.ROOT_MAPPING)
class ProductController {
    public static final String ROOT_MAPPING = "/products";
    public static final String ID_MAPPING = "/{id}";
    private final ProductService service;

    ProductController(ProductService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Product createProduct(@RequestBody ProductRequest productRequest) {
        return service.saveProduct(Product.from(productRequest));
    }

    @PutMapping(ID_MAPPING)
    ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Integer id) {
        try {
            service.findProduct(id);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.saveProduct(Product.from(productRequest, id)));
    }

    @GetMapping
    List<Product> fetchProducts() {
        return service.findProducts();
    }

    @GetMapping(ID_MAPPING + "/items")
    ResponseEntity<ProductItems> fetchProductsItems(@PathVariable("id") Integer productId) {
        ProductItems items = service.getProductItems(productId);
        return items == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(items);
    }


    @GetMapping(ID_MAPPING)
    ResponseEntity<Product> fetchProducts(@PathVariable Integer id) {
        Product product = service.findProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    //@ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(ID_MAPPING)
    ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer productId) {
        Product product = service.findProduct(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    boolean productExists(Integer id) {
        return service.findProduct(id) != null;
    }
}