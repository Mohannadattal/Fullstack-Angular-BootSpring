package com.mocode.springbootecommerce.service;

import com.mocode.springbootecommerce.dto.Purchase;
import com.mocode.springbootecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
