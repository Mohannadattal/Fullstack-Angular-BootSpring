package com.mocode.springbootecommerce.dto;

import com.mocode.springbootecommerce.entity.Address;
import com.mocode.springbootecommerce.entity.Customer;
import com.mocode.springbootecommerce.entity.Order;
import com.mocode.springbootecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
