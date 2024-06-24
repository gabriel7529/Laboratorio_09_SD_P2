package edu.unsa.example_transaction_2.implementacion;

import edu.unsa.example_transaction_2.dao.CustomerDao;
import edu.unsa.example_transaction_2.dao.CustomerManager;
import edu.unsa.example_transaction_2.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerManagerImpl implements CustomerManager {

    private final CustomerDao customerDAO;

    @Autowired
    public CustomerManagerImpl(CustomerDao customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    @Transactional

    public void createCustomer(Customer cust) {
        customerDAO.create(cust);
    }

}