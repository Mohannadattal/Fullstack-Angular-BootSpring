package com.mocode.springbootecommerce.service;

import com.mocode.springbootecommerce.dto.PaymentInfo;
import com.mocode.springbootecommerce.dto.Purchase;
import com.mocode.springbootecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
