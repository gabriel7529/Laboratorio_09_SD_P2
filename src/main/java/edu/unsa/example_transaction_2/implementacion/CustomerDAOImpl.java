package edu.unsa.example_transaction_2.implementacion;

import edu.unsa.example_transaction_2.dao.CustomerDao;
import edu.unsa.example_transaction_2.model.Customer;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class CustomerDAOImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void create(Customer customer) {
        jdbcTemplate.update("INSERT INTO Customer (id, name) VALUES (?, ?)",
                customer.getId(), customer.getName());
        jdbcTemplate.update("INSERT INTO Address (id, address, country) VALUES (?, ?, ?)",
                customer.getId(), customer.getAddress().getAddress(), customer.getAddress().getCountry());
    }
}
