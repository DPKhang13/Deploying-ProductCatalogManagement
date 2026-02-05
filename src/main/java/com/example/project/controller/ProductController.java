package com.example.project.controller;

import com.example.project.dto.request.ProductRequest;
import com.example.project.dto.response.ApiResponse;
import com.example.project.dto.response.ProductResponse;
import com.example.project.entity.Product;
import com.example.project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Product management endpoints for Admin (Customer endpoints will be added separately)")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create a new product",
               description = "Admin can add a new product with name (required), description, price (required, positive), stock quantity (required, non-negative), and category")
    @PostMapping("/createProduct")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestPart("product") ProductRequest request,
                                                                      @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        ProductResponse product = productService.createProduct(request,imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(product));
    }

    @Operation(summary = "Get all products (Admin)", description = "Admin can view all products in the admin board")
    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String categoryName
    ) {
        List<ProductResponse> products;

        if (categoryId != null) {
            products = productService.getProductByCategory_Id(categoryId);
        } else if (categoryName != null) {
            products = productService.getProductByCategory_Name(categoryName);
        } else {
            products = productService.getAllProduct();
        }

        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @Operation(summary = "Get product by ID (Admin)", description = "Admin can view details of a specific product")
    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductByIdAdmin(@PathVariable Long id) {
        ProductResponse product = productService.getAllProduct().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow();
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @Operation(summary = "Update a product", description = "Admin can edit product details (name, description, price, stock quantity, category)")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestPart("product") ProductRequest request,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        ProductResponse product = productService.updateProduct(id, request,imageFile);
        return ResponseEntity.ok(ApiResponse.success(product));
    }

    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String keyword) {
        return productService.search(keyword);
    }

    @Operation(summary = "Delete a product", description = "Admin can remove a product from the catalog")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
