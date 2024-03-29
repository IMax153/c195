
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.Country;

/**
 * Handles create, read, update, and delete operations for {@link Country}s.
 *
 * @author mab90
 */
public class CountryDao extends Dao<Country> {

    /**
     * Gets a {@link Country} specified by a unique identifier.
     *
     * @param id The id of the country.
     * @return The country.
     */
    @Override
    public Optional<Country> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM country WHERE countryId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Country country = getCountryFromResultSet(rs);
                return Optional.of(country);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets a {@link Country} by name.
     *
     * @param name The name of the country.
     * @return The country.
     */
    public Optional<Country> findByName(String name) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM country WHERE country = ?"
            );

            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Country country = getCountryFromResultSet(rs);
                return Optional.of(country);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets all {@link Country}s.
     *
     * @return The list of countries.
     */
    @Override
    public List<Optional<Country>> findAll() {
        List<Optional<Country>> countries = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM country"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Country country = getCountryFromResultSet(rs);
                countries.add(Optional.of(country));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return countries;
    }

    /**
     * Adds a {@link Country}.
     *
     * @param country The country to add.
     * @return True if the country was added successfully, otherwise false.
     */
    @Override
    public boolean add(Country country) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)"
            );

            pst.setString(1, country.getName());
            pst.setString(2, country.getCreatedBy());
            pst.setString(3, country.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Updates a {@link Country}.
     *
     * @param country The country to update.
     * @return True if the country was updated successfully, otherwise false.
     */
    @Override
    public boolean update(Country country) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE country "
                    + "SET country = ?, createdBy = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
                    + "WHERE countryId = ?"
            );

            pst.setString(1, country.getName());
            pst.setString(2, country.getCreatedBy());
            pst.setString(3, country.getUpdatedBy());
            pst.setInt(4, country.getId());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Deletes a {@link Country}.
     *
     * @param id The id of the country to delete.
     * @return True if the country was deleted successfully, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM country WHERE countryId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Checks if a {@link Country} with the specified name exists in the
     * database.
     *
     * @param name The name of the country to check.
     * @return True if the country exists in the database, otherwise false.
     */
    public boolean exists(String name) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT COUNT(countryId) FROM country WHERE country = ?;"
            );

            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Creates a {@link Country} from the specified {@link ResultSet}.
     *
     * @param rs The result set.
     * @return The country.
     * @throws SQLException
     */
    private Country getCountryFromResultSet(ResultSet rs) throws SQLException {
        return new Country(
                rs.getInt("countryId"),
                rs.getString("country"),
                rs.getString("createdBy"),
                rs.getString("lastUpdateBy"),
                rs.getTimestamp("createDate").toInstant(),
                rs.getTimestamp("lastUpdate").toInstant()
        );
    }
}
