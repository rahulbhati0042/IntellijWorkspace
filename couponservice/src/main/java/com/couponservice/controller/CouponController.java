package com.couponservice.controller;

import com.couponservice.model.Coupon;
import com.couponservice.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
//@RequestMapping("/couponapi")
public class CouponController {

    @Autowired
    private CouponRepo repo;

    @GetMapping("/showCreateCoupon")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showCreateCoupon() {
        ModelAndView mav = new ModelAndView("createCoupon");
        return mav;
    }

    @PostMapping("/saveCoupon")
    public ModelAndView save(Coupon coupon) {
        repo.save(coupon);
        ModelAndView mav = new ModelAndView("createResponse");
        return mav;
    }

    @GetMapping("/showGetCoupon")
    public ModelAndView showGetCoupon() {
        ModelAndView mav = new ModelAndView("getCoupon");
        return mav;
    }

    @PostMapping("/getCoupon")
    @PostAuthorize("returnObject.discount<60")
    public ModelAndView getCoupon(String code) {
        ModelAndView mav = new ModelAndView("couponDetails");
        System.out.println(code);
        mav.addObject(repo.findByCode(code));
        return mav;
    }
}
