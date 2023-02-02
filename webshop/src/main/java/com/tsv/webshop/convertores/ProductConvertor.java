package com.tsv.webshop.convertores;

import com.tsv.webshop.dtos.ProductDto;
import com.tsv.webshop.entities.Product;

public class ProductConvertor {
    public Product dtoToProduct(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        return product;
    };

    public ProductDto productToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
