/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daven
 */
public class Mechanic extends User {

    public Mechanic(ResultSet rs) {

        super(rs);
        try {
            setHourlyRate(rs.getDouble("HOUR_RATE"));

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Mechanic(User user, Double rate) {
        setFirstName(user.firstName());
        setLastName(user.lastName());
        setUsername(user.username());
        setPassword(user.password());
        setHourlyRate(rate);
    }

    @Override
    public ItemType type() {
        return ItemType.MECHANIC;
    }

    @Override
    public UserType userType() {
        return UserType.MECHANIC;
    }

    public void setHourlyRate(Double rate) {
        map().put("HOUR_RATE", rate);
    }

    public double hourlyRate() {
        return (double) map().get("HOUR_RATE");
    }

    public void setMinutesWorked(int mins) {
        map().put("MINS_WORKED", mins);
    }

    public int minsWorked() {
        return (int) map().get("MINS_WORKED");
    }

    @Override
    public Map<String, Object> map() {
        return map;
    }
}
