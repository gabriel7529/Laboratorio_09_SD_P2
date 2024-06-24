package edu.unsa.example_transaction_2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private int id;
    private String name;
    private Address address;
}
