package edu.unsa.example_transaction_2;

import edu.unsa.example_transaction_2.dao.CustomerManager;
import edu.unsa.example_transaction_2.model.Address;
import edu.unsa.example_transaction_2.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = ExampleTransaction2Application.class)
public class CustomerManagerIntegrationTest {
    @Autowired
    public CustomerManager customerManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS Customer");
        jdbcTemplate.execute("DROP TABLE IF EXISTS Address");
        jdbcTemplate.execute("CREATE TABLE Customer (id INT PRIMARY KEY, name VARCHAR(20))");
        jdbcTemplate.execute("CREATE TABLE Address (id INT, address VARCHAR(20), country VARCHAR(50), PRIMARY KEY (id))");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");

        Address address = new Address();
        address.setId(1);  // Añadir ID a la dirección
        address.setAddress("123 Main St");
        address.setCountry("USA");
        customer.setAddress(address);

        System.out.println("Creando cliente y dirección...");
        customerManager.createCustomer(customer);

        System.out.println("Verificando inserciones...");
        Integer customerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Customer", Integer.class);
        System.out.println("Número de clientes: " + customerCount);
        assertEquals(1, customerCount);

        Integer addressCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Address", Integer.class);
        System.out.println("Número de direcciones: " + addressCount);
        assertEquals(1, addressCount);
    }

    @Test
    @Transactional
    @Rollback
    public void testTransactionRollback() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setName("John Doe");

        Address address1 = new Address();
        address1.setId(1);
        address1.setAddress("123 Main St en una casa grande con varios espacios al lado de la tienda");
        address1.setCountry("USA");
        customer1.setAddress(address1);

        try {
            System.out.println("Creando cliente 1 y dirección...");
            customerManager.createCustomer(customer1);
        } catch (Exception e) {
            System.out.println("Error capturado: " + e.getMessage());
        }

        Integer customerCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Customer", Integer.class);
        System.out.println("Número de clientes: " + customerCount);
        assertEquals(1, customerCount);

        Integer addressCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Address", Integer.class);
        System.out.println("Número de direcciones: " + addressCount);
        assertEquals(0, addressCount);
    }
}