/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delegates;

/**
 *
 * @author Daven
 */
public interface PaymentInterface {
    /**
     * Tells the class that the user has paid for the Job or PartOrder.
     */
    public void paid();
}