package com.couponservice.controller;

import com.couponservice.model.Coupon;
import com.couponservice.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
public class CouponController {

    @Autowired
    private CouponRepo repo;

    @RequestMapping(value = "/coupon", method = RequestMethod.POST)
    public Coupon create(@RequestBody Coupon coupon){
        return repo.save(coupon);
    }


    @RequestMapping(value = "/coupons/{code}", method = RequestMethod.GET)
    public Coupon get(@PathVariable("code") String code){
        return repo.findByCode(code);
    }
}
