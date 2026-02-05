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
        // Initialize test users
        if (userRepository.count() == 0) {
            createUser("Admin User", "admin@example.com", "admin123", "0900000001", "Hanoi, Vietnam", Role.ADMIN, true);
            createUser("Nguyen Van A", "a.nguyen@example.com", "user123", "0900000002", "Ho Chi Minh City, Vietnam",
                    Role.CUSTOMER, true);
            createUser("Tran Thi B", "b.tran@example.com", "user123", "0900000003", "Da Nang, Vietnam", Role.CUSTOMER,
                    true);
            createUser("Le Van C", "c.le@example.com", "user123", "0900000004", "Can Tho, Vietnam", Role.CUSTOMER,
                    false);
            createUser("Pham Thi D", "d.pham@example.com", "user123", "0900000005", "Hai Phong, Vietnam", Role.CUSTOMER,
                    true);
            createUser("Admin Test", "test@gmail.com", "1234", "0123456789", "Hà Nội", Role.ADMIN, true);
            System.out.println("✅ Created 6 test users");
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
                createProduct("iPhone 15", "Apple smartphone latest version", "1200.00", 50,
                        "https://picsum.photos/seed/iphone15/400/400", electronics);
                createProduct("Samsung TV 55\"", "4K Smart TV", "850.00", 30,
                        "https://picsum.photos/seed/samsungtv/400/400", electronics);
                createProduct("Nike Air Force 1", "Classic white sneakers", "120.00", 100,
                        "https://picsum.photos/seed/nikeaf1/400/400", fashion);
                createProduct("Adidas Hoodie", "Comfortable sports hoodie", "75.00", 60,
                        "https://picsum.photos/seed/adidashoodie/400/400", fashion);
                createProduct("Clean Code", "Programming best practices book", "45.00", 40,
                        "https://picsum.photos/seed/cleancode/400/400", books);
                createProduct("Java Programming", "Learn Java from basics to advanced", "55.00", 35,
                        "https://picsum.photos/seed/javabook/400/400", books);
                createProduct("Washing Machine", "Automatic washing machine 8kg", "500.00", 20,
                        "https://picsum.photos/seed/washingmachine/400/400", homeAppliances);
                createProduct("Microwave Oven", "Digital microwave oven", "180.00", 25,
                        "https://picsum.photos/seed/microwave/400/400", homeAppliances);
                createProduct("Football", "Standard size football", "30.00", 80,
                        "https://picsum.photos/seed/football/400/400", sports);
                createProduct("Tennis Racket", "Professional tennis racket", "150.00", 15,
                        "https://picsum.photos/seed/tennisracket/400/400", sports);
                System.out.println("✅ Created 10 products");
            }
        }
    }

    private void createUser(String fullName, String email, String password, String phone, String address, Role role,
            boolean isActive) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setPhone(phone);
            user.setAddress(address);
            user.setRole(role);
            user.setIsActive(isActive);
            userRepository.save(user);
        }
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    private void createProduct(String name, String description, String price, int stockQuantity, String imageUrl,
            Category category) {
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
