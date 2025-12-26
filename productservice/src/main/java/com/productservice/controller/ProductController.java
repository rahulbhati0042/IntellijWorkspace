package com.productservice.controller;

import com.productservice.model.Coupon;
import com.productservice.model.Product;
import com.productservice.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/productapi")
public class ProductController {

    @Autowired
    private ProductRepo repo;

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> fetchAll(){
        return repo.findAll();
    }

    @PostMapping("/create")
    public Product create(@RequestBody Product request){

        Coupon coupon = restTemplate.getForObject("http://localhost:9091/couponapi/coupons/"+request.getCouponCode(), Coupon.class);

        request.setPrice(request.getPrice().subtract(coupon.getDiscount()));
        return repo.save(request);
    }

}
