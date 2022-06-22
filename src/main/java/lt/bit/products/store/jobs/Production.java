package lt.bit.products.store.jobs;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lt.bit.products.store.model.Product;
import lt.bit.products.store.service.ProductItemsRepository;
import lt.bit.products.store.service.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class Production {

  private final static Logger LOG = LoggerFactory.getLogger(Production.class);

  @Value("${jobs.production.number_of_new_products}")
  private int numberOfNewProducts;
  private String[] ipAddressNumbers;

  private final ProductRepository productRepository;
  private final ProductItemsRepository productItemsRepository;

  Production(ProductRepository productRepository,
      ProductItemsRepository productItemsRepository) {
    this.productRepository = productRepository;
    this.productItemsRepository = productItemsRepository;

    try {
      String ipAddress = InetAddress.getLocalHost().getHostAddress();
      ipAddressNumbers = ipAddress.split("\\.");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }

  @Scheduled(fixedRate = 15000)
  void addNewProducts() {
    LOG.info("Production job started");
    LOG.info("Adding products...");

    for (int i = 0; i < numberOfNewProducts; i++) {
      Product generatedProduct = createProduct(i + 1);
      productRepository.save(generatedProduct);
      LOG.info("i=" + i + " -> " + generatedProduct + " - SAVED!");
    }
    LOG.info("Generated price: {}", generatePrice());
    LOG.info("Generated quantity: {}", generateQuantity());
  }

  private Product createProduct(int index) {
    Product product = new Product();
    product.setName(generateName(index));
    product.setDescription(generateDescription());
    product.setCreated(LocalDate.now());
    return product;
  }

  private String generateName(int index) {
    String dayOfWeek = LocalDateTime.now().format(DateTimeFormatter.ofPattern("E", Locale.ENGLISH));
    return String.format("%s-%d", dayOfWeek, index);
  }

  private String generateDescription() {
    return String.format("Desc %s", LocalDate.now());
  }

  private BigDecimal generatePrice() {
    LocalDateTime now = LocalDateTime.now();
    // 1
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("m.s");
    return new BigDecimal(now.format(formatter));
    // 2
    // int min = now.getMinute();
    // int sec = now.getSecond();
    // return new BigDecimal(String.format("%d.%d", min, sec));
  }

  private int generateQuantity() {
    // TODO: ipAddressNumbers.....
    return 0;
  }
}
