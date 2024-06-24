package edu.unsa.example_transaction_2.dao;

import edu.unsa.example_transaction_2.model.Customer;
import org.springframework.stereotype.Repository;


public interface CustomerDao {
    void create(Customer customer);
}
