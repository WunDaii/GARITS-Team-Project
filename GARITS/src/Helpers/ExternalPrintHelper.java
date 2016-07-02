/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

/**
 *
 * @author Daven
 */
public class ExternalPrintHelper {

    /**
     * Prints a text file from a specified filepath.
     *
     * @param path the path where the text file is saved.
     * @throws PrintException an error when connecting to the printer.
     */
    public void printTextFile(String path) throws PrintException {
        try {
            String defaultPrinter
                    = PrintServiceLookup.lookupDefaultPrintService().getName();
            System.out.println("Default printer: " + defaultPrinter);

            PrintService service = PrintServiceLookup.lookupDefaultPrintService();

            FileInputStream in = null;
            String fileName = "";
            try {
                File file = new File(path);
                fileName = file.getName();
                in = new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExternalPrintHelper.class.getName()).log(Level.SEVERE, null, ex);
            }

            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(new Copies(1));
            pras.add(new JobName(fileName, Locale.getDefault()));

            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc doc = new SimpleDoc(in, flavor, null);
            DocPrintJob job = service.createPrintJob();

            PrintJobWatcher pjw = new PrintJobWatcher(job);
            job.print(doc, pras);
            pjw.waitForDone();
            in.close();

        } catch (IOException ex) {
            Logger.getLogger(ExternalPrintHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class PrintJobWatcher {

    boolean done = false;

    PrintJobWatcher(DocPrintJob job) {
        job.addPrintJobListener(new PrintJobAdapter() {
            @Override
            public void printJobCanceled(PrintJobEvent pje) {
                allDone();
            }

            @Override
            public void printJobCompleted(PrintJobEvent pje) {
                allDone();
            }

            @Override
            public void printJobFailed(PrintJobEvent pje) {
                allDone();
            }

            @Override
            public void printJobNoMoreEvents(PrintJobEvent pje) {
                allDone();
            }

            void allDone() {
                synchronized (PrintJobWatcher.this) {
                    done = true;
                    System.out.println("Printing done ...");
                    PrintJobWatcher.this.notify();
                }
            }
        });
    }

    public synchronized void waitForDone() {
        try {
            while (!done) {
                wait();
            }
        } catch (InterruptedException e) {
        }
    }
}
