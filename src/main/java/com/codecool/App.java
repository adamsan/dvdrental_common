package com.codecool;

import com.codecool.dao.CustomerDao;
import com.codecool.dao.FilmDao;
import com.codecool.dao.JdbcCustomerDao;
import com.codecool.dao.JdbcFilmDaoImpl;
import com.codecool.model.Customer;
import com.codecool.model.Film;
import com.codecool.model.Rating;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private Connection con;

    public App(Connection con) {

        this.con = con;
    }

    public static void main(String[] args) throws SQLException {
        try (Connection con = getConnection()) {
            new App(con).run();
        }
    }

    private void run() {
//        runCustomerQueries();
        runFilmQueries();
    }

    private void runFilmQueries() {
        FilmDao dao = new JdbcFilmDaoImpl(con);
        System.out.println("\nFind all Films, print the first 5:");
        dao.findAll().stream()
                .limit(5)
                .forEach(System.out::println);

        System.out.println("\nFind film by id");
        Film f = dao.findById(384);
        System.out.println(f);

        System.out.println("\nInserting new film:");
        Film newFilm = new Film(null, 2022, "A noon of a scarry night", "Description later...", Rating.PG_13);
        dao.save(newFilm);
        System.out.println(newFilm);
    }

    private void runCustomerQueries() {
        CustomerDao dao = new JdbcCustomerDao(con);
        System.out.println("\nList of customers, whos email starts with 'am':");
        List<Customer> customers = dao.findByEmail("am%");
        customers.forEach(System.out::println);

        System.out.println("\nCreate new Customer:");
        Customer newCustomer = new Customer(null, "Geza", "Szines", "szinesgeza@freemail.hu");
        dao.save(newCustomer);
        System.out.println(newCustomer);

        System.out.println("\nDelete the previously created customer:");
        dao.deleteById(newCustomer.getId());
        System.out.println(dao.findById(newCustomer.getId())); // should be null

        System.out.println("\nFind customer:");
        Customer c = dao.findById(602);
        System.out.println(c);

        System.out.println("\nUpdate existing customer, switch names:");
        var tmp = c.getFirstName();
        c.setFirstName(c.getLastName());
        c.setLastName(tmp);
        dao.save(c);
        System.out.println(c);
        System.out.println("\nFinding all customers (displaying 10):");
        String lastNames = dao.findAll().stream()
                .limit(10)
                .map(Customer::getLastName)
                .collect(Collectors.joining(", "));
        System.out.println(lastNames);
    }

    private static Connection getConnection() {
        var ds = new PGSimpleDataSource();
        ds.setURL(System.getenv("DB_URL"));
        ds.setUser(System.getenv("DB_USER"));
        ds.setPassword(System.getenv("DB_PASSWORD"));
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
