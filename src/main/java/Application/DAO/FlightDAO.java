package Application.DAO;

import Application.Model.Flight;
import Application.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    public List<Flight> getAllFlights() {
        Connection connection = ConnectionUtil.getConnection();
        List<Flight> flights = new ArrayList<>();
        try {
            String sql = "SELECT * FROM flight";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flights.add(new Flight(
                    rs.getInt("flight_id"),
                    rs.getString("departure_city"),
                    rs.getString("arrival_city")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public Flight getFlightById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM flight WHERE flight_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Flight(
                    rs.getInt("flight_id"),
                    rs.getString("departure_city"),
                    rs.getString("arrival_city")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Flight insertFlight(Flight flight) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO flight (departure_city, arrival_city) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, flight.getDeparture_city());
            ps.setString(2, flight.getArrival_city());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int newId = keys.getInt(1);
                return new Flight(newId, flight.getDeparture_city(), flight.getArrival_city());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateFlight(int id, Flight flight) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE flight SET departure_city = ?, arrival_city = ? WHERE flight_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, flight.getDeparture_city());
            ps.setString(2, flight.getArrival_city());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Flight> getAllFlightsFromCityToCity(String departure_city, String arrival_city) {
        Connection connection = ConnectionUtil.getConnection();
        List<Flight> flights = new ArrayList<>();
        try {
            String sql = "SELECT * FROM flight WHERE departure_city = ? AND arrival_city = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, departure_city);
            ps.setString(2, arrival_city);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flights.add(new Flight(
                    rs.getInt("flight_id"),
                    rs.getString("departure_city"),
                    rs.getString("arrival_city")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
