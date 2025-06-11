package Application.Service;

import Application.Model.Flight;
import Application.DAO.FlightDAO;

import java.util.List;

public class FlightService {
    FlightDAO flightDAO;

    public FlightService() {
        this.flightDAO = new FlightDAO();
    }

    public FlightService(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    public Flight addFlight(Flight flight) {
        // Always forward to DAO and return result
        return flightDAO.insertFlight(flight);
    }

    public Flight updateFlight(int flight_id, Flight flight) {
        // Ensure flight exists
        Flight existing = flightDAO.getFlightById(flight_id);
        if (existing == null) {
            return null;
        }
        // Perform update and return the latest version
        flightDAO.updateFlight(flight_id, flight);
        return flightDAO.getFlightById(flight_id);
    }

    public List<Flight> getAllFlights() {
        // Direct passthrough from DAO
        return flightDAO.getAllFlights();
    }

    public List<Flight> getAllFlightsFromCityToCity(String departure_city, String arrival_city) {
        // Direct passthrough from DAO for efficiency (as expected by the test)
        return flightDAO.getAllFlightsFromCityToCity(departure_city, arrival_city);
    }
}
