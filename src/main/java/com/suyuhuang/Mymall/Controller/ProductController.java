package com.suyuhuang.Mymall.Controller;

import com.suyuhuang.Mymall.Model.Product;
import com.suyuhuang.Mymall.Service.ProductService;
import com.suyuhuang.Mymall.dto.ProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> creatProduct(@RequestBody @Valid ProductRequest productRequest){
       Integer productId = productService.creatProduct(productRequest);

       Product product = productService.getProductById(productId);

       return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}
