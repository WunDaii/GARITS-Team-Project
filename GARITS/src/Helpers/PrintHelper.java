/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import Controllers.CustomerController;
import Entities.AveragePriceReport;
import Entities.AverageTimeReport;
import Entities.Customer;
import Entities.Discount;
import Entities.FixedDiscount;
import Entities.FlexibleDiscount;
import Entities.Invoice;
import Entities.Item;
import Entities.Job;
import Entities.JobStock;
import Entities.PartOrder;
import Entities.Stock;
import Entities.Task;
import Entities.VariableDiscount;
import Entities.Vehicle;
import Entities.VehiclesBookedReport;
import Panels.InvoicePanel;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;

/**
 *
 * @author Daven
 */
public class PrintHelper {

    /**
     * Prints a job sheet for a Job. This exports a job sheet as a text file in
     * the same format as the example in the Brief. The title of the text file
     * will be 'JOB_SHEET_[JOB_ID].txt'.
     *
     * @param job the job to create the job sheet from.
     * @param print whether the job sheet should be sent to the printer.
     * @see Job
     */
    public void printJobSheet(Job job, boolean print) {

        String filePath = "/Users/Daven/Downloads/JOB_SHEET_" + job.ID() + ".txt";

        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {

                writer.write("Quick Fix Fitters,\n19 High St.,\nAshford,\nKent CT16 8YY\n");
                writer.write("\nJOB NO.: " + job.ID() + "\n\n");
                String formatStr = "%-20s %-10s%n";
                Format formatter = new SimpleDateFormat("dd MMMM yyyy");
                String s = "";

                if (job.date() != null) {
                    s = formatter.format(job.date());
                }
                writer.write(String.format(formatStr, "Reg. No.: " + job.vehicle().regNo(), "Date Booked In: " + s));
                writer.write(String.format(formatStr, "Make: " + job.vehicle().make(), "Model: " + job.vehicle().model()));
                writer.write(String.format(formatStr, "Customer: " + job.customer().firstName().substring(0, 1) + " " + job.customer().lastName(), "Tel.: " + job.customer().telephone()));
                writer.write("\n____________\n\n");
                writer.write("DESCRIPTION OF WORK REQUIRED:\n");
                writer.write(job.workRequired());

                writer.write("\n\nEstimated Time (mins.): " + job.estTime());
                writer.write("\n____________\n\n");

                writer.write("DESCRIPTION OF WORK CARRIED OUT:\n");
                writer.write(job.workCarried());

                writer.write("\n\nActual Time (mins.): " + job.actualTime());

                writer.write("\n\nTASKS:\n");
                String work = "";
                for (int i = 0; i < job.tasks().size(); i++) {
                    Task task = job.tasks().get(i);
                    work = work + "" + (i + 1) + ") " + task.name() + "\n";
                }
                writer.write(work);

                writer.write("\n\nSPARE PARTS USED:\n");

                formatStr = "%-20s %-20s %-20s%n";
                writer.write(String.format(formatStr, "Item Name", "Part No.", "Qty."));

                for (JobStock stock : job.items()) {
                    writer.write(String.format(formatStr, stock.part().name(), stock.part().number(), stock.jobQuantity()));
                }

                writer.write("\nSignature:__________________________");

                writer.close();
                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints an invoice for a Job. This exports an invoice as a text file in
     * the same format as the example in the Brief. The title of the text file
     * will be 'INVOICE_JOB_[JOB_ID].txt'.
     *
     * @param invoice the invoice to create the text file from.
     * @param print whether the text file should be sent to the printer.
     * @see Invoice
     * @see Job
     */
    public void printJobInvoice(Invoice invoice, boolean print) {
        String filePath = "/Users/Daven/Downloads/INVOICE_JOB_" + invoice.job().ID() + ".txt";
        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
                Customer customer = invoice.job().customer();
                writer.write(customer.firstName() + " " + customer.lastName() + "\n");
                writer.write(customer.address() + "\n");

                writer.write(customer.postcode() + "\n");

                Format formatter = new SimpleDateFormat("dd MMMM yyyy");
                String s = formatter.format(new Date());
                writer.write(s + "\n\n");

                writer.write("Dear " + customer.firstName() + " " + customer.lastName() + ",\n\nINVOICE NO.: " + invoice.job().ID() + "\n\n");

                writer.write("Vehicle Registration No.: " + invoice.job().vehicle().regNo() + "\n");
                writer.write("Make: " + invoice.job().vehicle().make() + "\n");
                writer.write("Model: " + invoice.job().vehicle().model() + "\n");
                writer.write("\n");

                String work = "Work carried:\n";

                for (int i = 0; i < invoice.job().tasks().size(); i++) {
                    Task task = invoice.job().tasks().get(i);
                    work = work + "" + i + ") " + task.name() + "\n";
                }

                writer.write(work + "\n");

                String formatStr = "%-20s %-20s %-20s %-20s%n";
                writer.write(String.format(formatStr, "Item Name", "Part No.", "Cost", "Qty."));

                Double totalCost = 0.0;
                ConfigHelper configHelper = new ConfigHelper();
                Double VAT = configHelper.getVATRate();
                Double markup = configHelper.getMarkupRate();

                totalCost = invoice.job().items().stream().map((stock) -> {
                    writer.write(String.format(formatStr, stock.part().name(), stock.part().number(), String.format("£%.2f", stock.cost()), stock.jobQuantity()));
                    return stock;
                }).map((stock) -> ((stock.cost() * ((100 + markup) / 100)) * stock.jobQuantity())).reduce(totalCost, (accumulator, _item) -> accumulator + _item);

                writer.write("\n");
                writer.write(String.format(formatStr, "Mechanic", "Minutes Worked", "Hourly Rate (£/hour)", "Cost"));

                totalCost = invoice.job().mechanics().stream().map((mech) -> {
                    String newStr = String.format(formatStr, mech.firstName() + " " + mech.lastName(), mech.minsWorked(), mech.hourlyRate(), "£" + ((mech.hourlyRate() / 60) * mech.minsWorked()));
                    return mech;
                }).map((mech) -> (mech.hourlyRate() / 60) * mech.minsWorked()).reduce(totalCost, (accumulator, _item) -> accumulator + _item);

                writer.write("\n");
                writer.write("Total: £" + totalCost + "\n");
                writer.write("VAT: " + VAT + "%\n");

                totalCost = totalCost * ((100 + VAT) / 100);
                Discount discount = invoice.job().customer().discount();
                Double rate = 0.0;
                if (discount instanceof FixedDiscount) {
                    rate = ((FixedDiscount) discount).rate();
                    totalCost = ((FixedDiscount) discount).calculateCost(totalCost);
                } else if (discount instanceof FlexibleDiscount) {
                    rate = ((FlexibleDiscount) discount).rate(totalCost);
                    totalCost = ((FlexibleDiscount) discount).calculateCost(totalCost);
                } else if (discount instanceof VariableDiscount) {
                    rate = ((VariableDiscount) discount).rate(totalCost, invoice.job().jobType());
                    totalCost = ((VariableDiscount) discount).calculateCost(totalCost, invoice.job().jobType());
                }

                writer.write("Discount: -" + rate + "%\n\n");

                writer.write("Grand total: £" + String.format("%.2f", totalCost) + "\n\n");

                writer.write("Thank you for your valued custom. We look forward to receiving your payment in due course.\nYours sincerely,\n\n\n\n\n\nG.Lancaster");

                writer.close();

                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints an invoice for a PartOrder. This saves the invoice as a text file
     * in the same format as the example in the Brief.
     *
     * @param order the order from which the invoice will be created from.
     * @param print whether the text file should be sent to the printer.
     * @see PartOrder
     */
    public void printPartOrderInvoice(PartOrder order, boolean print) {
        String filePath = filePathForType("PART_ORDER_" + order.ID());

        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {

                Customer customer = order.customer();
                writer.write(customer.firstName() + " " + customer.lastName() + "\n");
                writer.write(customer.address() + "\n");

                writer.write(customer.postcode() + "\n");

                Format formatter = new SimpleDateFormat("dd MMMM yyyy");
                String s = formatter.format(new Date());
                writer.write(s + "\n\n");

                writer.write("Dear " + customer.firstName() + " " + customer.lastName() + ",\n\nINVOICE NO.: " + order.ID() + "\n\n");

                String formatStr = "%-20s %-20s %-20s %-20s%n";
                writer.write(String.format(formatStr, "Item Name", "Part No.", "Cost", "Qty."));

                Double totalCost = 0.0;
                ConfigHelper configHelper = new ConfigHelper();
                Double VAT = configHelper.getVATRate();
                Double markup = configHelper.getMarkupRate();

                totalCost = order.parts().stream().map((stock) -> {
                    writer.write(String.format(formatStr, stock.part().name(), stock.part().number(), String.format("£%.2f", stock.cost()), stock.jobQuantity()));
                    return stock;
                }).map((stock) -> ((stock.cost() * ((100 + markup) / 100)) * stock.jobQuantity())).reduce(totalCost, (accumulator, _item) -> accumulator + _item);

                writer.write("\n");
                writer.write("Total: £" + totalCost + "\n");
                writer.write("VAT: " + VAT + "%\n");
                writer.write("Grand total: £" + (totalCost * (100 + VAT) / 100) + "\n\n");

                writer.write("Thank you for your valued custom. We look forward to receiving your payment in due course.\nYours sincerely,\n\n\n\n\n\nG.Lancaster");

                writer.close();

                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets a formatted String for the file name. The current date is added to
     * the end of the file name to ensure better organisation.
     *
     * @param type the type of file to be saves.
     * @return the final file path for the file to be saved.
     */
    public String filePathForType(String type) {
        Format formatter = new SimpleDateFormat("dd_MMMM_yyyy");
        String s = formatter.format(new Date());
        return "/Users/Daven/Downloads/" + type + "_" + s + ".txt";
    }

    /**
     * Prints the current stock levels. This saves the stock ledger as a text
     * file in the same format as the example in the Brief.
     *
     * @param print whether the text file should be sent to the printer.
     * @see Stock
     */
    public void printStockLedger(boolean print) {

        Format formatter = new SimpleDateFormat("dd_MMMM_yyyy");
        String s = formatter.format(new Date());
        String filePath = "/Users/Daven/Downloads/STOCK_LEDGER_" + s + ".txt";
        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {

                GetListHelper listHelper = new GetListHelper();
                List<Stock> parts = (List<Stock>) listHelper.getListForListType(Item.ItemType.STOCK);
                formatter = new SimpleDateFormat("dd-MM-yyy");
                s = formatter.format(new Date());
                writer.write("STOCK LEDGER - CREATED " + s + "\n\n");

                String formatStr = "%-20s %-20s %-20s %-20s%-20s %-20s %-20s%n";
                writer.write(String.format(formatStr, "NAME", "PART NUMBER", "MANUFACTURER", "VEHICLE TYPE", "YEARS", "COST", "QUANTITY"));
                writer.write("\n");
                for (Stock stock : parts) {
                    writer.write(String.format(formatStr, stock.part().name(), stock.part().number(),
                            stock.part().manufacturer(), stock.part().vehicleType(), stock.part().years(),
                            String.format("£%.2f", stock.cost()), stock.quantity()));
                }

                writer.close();

                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints the average time report. This saves the report as a text file in
     * the same format as the example in the Brief.
     *
     * @param print whether the text file should be sent to the printer.
     */
    public void printAverageTimeReport(boolean print) {

        AverageTimeReport report = new AverageTimeReport();

        Format formatter = new SimpleDateFormat("dd_MMMM_yyyy");
        String s = formatter.format(new Date());
        String filePath = "/Users/Daven/Downloads/AVERAGE_TIME_REPORT_" + s + ".txt";
        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {

                writer.write("Average Time Report");
                writer.write("\n_______________________________________________________\n\n");

                writer.write("OVERALL : " + report.overallAverage() + "\n");
                writer.write("_______________________________________________________\n\n");

                writer.write("SERVICE REQUESTED\n\n");

                String formatStr = "%-20s %-20s%n";
                writer.write(String.format(formatStr, "Service Requested", "Average Time (Minutes)"));

                writer.write("\n");
                HashMap<String, Integer> map = report.jobTypeMap();

                for (String key : map.keySet()) {
                    writer.write(String.format(formatStr, key, "" + map.get(key)));
                }

                writer.write("_______________________________________________________\n\n");
                writer.write("MECHANIC\n\n");
                writer.write(String.format(formatStr, "ID) Mechanic Name", "Average Time (Minutes)"));
                writer.write("\n");
                map = report.mechanicsMap();

                for (String key : map.keySet()) {
                    writer.write(String.format(formatStr, key, "" + map.get(key)));
                }

                writer.write("_______________________________________________________\n\n");

                writer.close();

                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints the average price report. This saves the report as a text file in
     * the same format as the example in the Brief.
     *
     * @param print whether the text file should be sent to the printer.
     */
    public void printPriceReport(boolean print) {

        AveragePriceReport report = new AveragePriceReport();

        Format formatter = new SimpleDateFormat("dd_MMMM_yyyy");
        String s = formatter.format(new Date());
        String filePath = "/Users/Daven/Downloads/AVERAGE_PRICE_REPORT_" + s + ".txt";

        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {

                writer.write("Vehicles Booked In Report");
                writer.write("\n_______________________________________________________\n\n");

                writer.write("OVERALL Price Per Job : " + String.format("£%.2f", report.jobAverage()) + "\n");
                writer.write("_______________________________________________________\n\n");

                writer.write("MECHANICS\n\n");

                String formatStr = "%-20s %-20s%n";
                writer.write(String.format(formatStr, "ID) Mechanic Name", "Average Price"));
                writer.write("\n");
                HashMap<String, Double> map = report.mechanicAverage();

                for (String key : map.keySet()) {
                    writer.write(String.format(formatStr, key, String.format("£%.2f", map.get(key))));
                }

                writer.write("_______________________________________________________\n\n");

                writer.close();

                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Prints the number of vehicles booked report. This saves the report as a
     * text file in the same format as the example in the Brief.
     *
     * @param print whether the text file should be sent to the printer.
     * @see Vehicle
     */
    public void printReportVehiclesBooked(boolean print) {

        VehiclesBookedReport report = new VehiclesBookedReport();

        Format formatter = new SimpleDateFormat("dd_MMMM_yyyy");
        String s = formatter.format(new Date());
        String filePath = "/Users/Daven/Downloads/VEHICLES_REPORT_" + s + ".txt";
        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {

                writer.write("Vehicles Booked In Report");
                writer.write("\n_______________________________________________________\n\n");

                writer.write("OVERALL : " + report.overall() + "\n");
                writer.write("_______________________________________________________\n\n");

                writer.write("MONTHLY\n\n");

                String formatStr = "%-20s %-20s%n";
                writer.write(String.format(formatStr, "Month", "Number of Vehicles Booked"));
                writer.write("\n");
                HashMap<String, Integer> monthly = report.monthly();
                List<String> months = report.months();
                for (String month : months) {
                    writer.write(String.format(formatStr, month, "" + monthly.get(month)));
                }

                writer.write("_______________________________________________________\n\n");

                writer.write("SERVICE TYPE\n\n");
                writer.write(String.format(formatStr, "Service Type", "Number of Vehicles Booked"));
                writer.write("\n");
                HashMap<String, Integer> map = report.jobType();

                for (String key : map.keySet()) {
                    writer.write(String.format(formatStr, key, "" + map.get(key)));
                }

                writer.write("_______________________________________________________\n\n");
                writer.write("CUSTOMER TYPE\n\n");
                writer.write(String.format(formatStr, "Customer Type", "Number of Vehicles Booked"));
                writer.write("\n");
                map = report.customerType();

                for (String key : map.keySet()) {
                    writer.write(String.format(formatStr, key, "" + map.get(key)));
                }

                writer.write("_______________________________________________________\n\n");

                writer.close();

                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints all customer records. * This saves the records as a text file in
     * the same format as the example in the Brief.
     *
     * @param print whether the text file should be sent to the printer.
     * @see Customer
     */
    public void printCustomerRecords(boolean print) {

        Format formatter = new SimpleDateFormat("dd_MMMM_yyyy");
        String s = formatter.format(new Date());
        String filePath = "/Users/Daven/Downloads/CUSTOMER_RECORDS_" + s + ".txt";
        try {
            try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {

                GetListHelper listHelper = new GetListHelper();
                List<Customer> customers = (List<Customer>) listHelper.getListForListType(Item.ItemType.CUSTOMER);
                CustomerController control = new CustomerController();

                for (Customer customer : customers) {
                    writer.write(getPrintStringForCustomer(customer, control));
                }

                writer.close();

                if (print) {
                    try {
                        new ExternalPrintHelper().printTextFile(filePath);
                    } catch (PrintException ex) {
                        Logger.getLogger(PrintHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InvoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets a String to print for a Customer for their record.
     *
     * @param customer the Customer to print.
     * @param controller the CustomerController to get the vehicles.
     * @return the customer record as a formatted String.
     * @see Customer
     */
    private String getPrintStringForCustomer(Customer customer, CustomerController controller) {

        String pStr = "CUSTOMER/VEHICLE RECORD\n\n";

        Format format = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = "";
        if (customer.dateCreated() != null) {
            dateStr = format.format(customer.dateCreated());
        }

        pStr = pStr + "DATE: " + dateStr + "\n";
        pStr = pStr + "NAME: " + customer.firstName() + " " + customer.lastName() + "\n";
        pStr = pStr + "ADDRESS: " + customer.address() + "\n";
        pStr = pStr + "POSTCODE: " + customer.postcode() + "\n";
        pStr = pStr + "TEL: " + customer.telephone() + "\n";
        pStr = pStr + "EMAIL: " + customer.email() + "\n\n";

        List<Vehicle> vehs = controller.getVehiclesForCustomer(customer);

        String formatStr = "%-20s %-20s %-20s %-20s%-20s %-20s%n";
        pStr = pStr + String.format(formatStr, "REG NO.", "MAKE", "MODEL", "ENG. SERIAL",
                "CHASSIS NO.", "COLOUR");

        for (Vehicle v : vehs) {
            pStr = pStr + String.format(formatStr, v.regNo(),
                    v.make(), v.model(), v.engSerial(),
                    v.chassisNumber(), v.colour());
        }

        pStr = pStr + "\n_______________________________________\n\n";

        return pStr;
    }
}
