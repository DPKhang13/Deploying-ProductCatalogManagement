package com.example.project.service;

import com.example.project.dto.request.ProductRequest;
import com.example.project.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct (ProductRequest request, MultipartFile file);
    List<ProductResponse> getAllProduct();
    ProductResponse updateProduct(Long id, ProductRequest request, MultipartFile file);
    void deleteProduct (Long id);
    List<ProductResponse> search(String keyword);
    List<ProductResponse> getProductByCategory_Id(Long id);
    List<ProductResponse> getProductByCategory_Name(String name);

}
