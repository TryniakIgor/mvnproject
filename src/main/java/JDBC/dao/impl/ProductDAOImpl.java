package JDBC.dao.impl;

import JDBC.config.MySQLConnector;
import JDBC.dao.ProductDAO;
import JDBC.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public int create(Product product) {
        int status = 400;
        String SQL = "INSERT INTO product(name, price) VALUES (?, ?)";

        try (
                Connection connection = MySQLConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.execute();
            status = 201;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public Product getById(long id) {
        int status = 400;
        String SQL = "select name, price from product where id = ?";
        Product product = null;

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setId(id);
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal(("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public int update(Product product) {
        return 0;
    }


    @Override
    public int deleteById(long id) {
        String SQL = "delete from product where id=?";

        try (Connection connection = MySQLConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 202;
    }


}
