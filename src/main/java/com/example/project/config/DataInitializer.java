package com.example.project.config;

import com.example.project.entity.Category;
import com.example.project.entity.Product;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.repository.CategoryRepository;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Initialize test user
        if (userRepository.findByEmail("test@gmail.com").isEmpty()) {
            User testUser = new User();
            testUser.setEmail("test@gmail.com");
            testUser.setPassword(passwordEncoder.encode("1234"));
            testUser.setRole(Role.ADMIN);
            testUser.setIsActive(true);
            testUser.setFullName("Admin Test");
            testUser.setPhone("0123456789");
            testUser.setAddress("Hà Nội");
            userRepository.save(testUser);
            System.out.println("✅ Created test user: test@gmail.com / 1234");
        }

        // Initialize categories if empty
        if (categoryRepository.count() == 0) {
            Category electronics = createCategory("Electronics");
            Category fashion = createCategory("Fashion");
            Category books = createCategory("Books");
            Category homeAppliances = createCategory("Home Appliances");
            Category sports = createCategory("Sports");
            System.out.println("✅ Created 5 categories");

            // Initialize products if empty
            if (productRepository.count() == 0) {
                createProduct("iPhone 15", "Apple smartphone latest version", "1200.00", 50, "https://picsum.photos/seed/iphone15/400/400", electronics);
                createProduct("Samsung TV 55\"", "4K Smart TV", "850.00", 30, "https://picsum.photos/seed/samsungtv/400/400", electronics);
                createProduct("Nike Air Force 1", "Classic white sneakers", "120.00", 100, "https://picsum.photos/seed/nikeaf1/400/400", fashion);
                createProduct("Adidas Hoodie", "Comfortable sports hoodie", "75.00", 60, "https://picsum.photos/seed/adidashoodie/400/400", fashion);
                createProduct("Clean Code", "Programming best practices book", "45.00", 40, "https://picsum.photos/seed/cleancode/400/400", books);
                createProduct("Java Programming", "Learn Java from basics to advanced", "55.00", 35, "https://picsum.photos/seed/javabook/400/400", books);
                createProduct("Washing Machine", "Automatic washing machine 8kg", "500.00", 20, "https://picsum.photos/seed/washingmachine/400/400", homeAppliances);
                createProduct("Microwave Oven", "Digital microwave oven", "180.00", 25, "https://picsum.photos/seed/microwave/400/400", homeAppliances);
                createProduct("Football", "Standard size football", "30.00", 80, "https://picsum.photos/seed/football/400/400", sports);
                createProduct("Tennis Racket", "Professional tennis racket", "150.00", 15, "https://picsum.photos/seed/tennisracket/400/400", sports);
                System.out.println("✅ Created 10 products");
            }
        }
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    private void createProduct(String name, String description, String price, int stockQuantity, String imageUrl, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(new BigDecimal(price));
        product.setStockQuantity(stockQuantity);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        productRepository.save(product);
    }
}
