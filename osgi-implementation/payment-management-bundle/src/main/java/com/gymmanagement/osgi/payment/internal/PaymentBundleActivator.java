package com.gymmanagement.osgi.payment.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Bundle Activator for Payment Management Bundle
 * Handles bundle lifecycle events
 */
public class PaymentBundleActivator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("=========================================");
        System.out.println("Payment Management Bundle ACTIVATED");
        System.out.println("Bundle ID: " + context.getBundle().getBundleId());
        System.out.println("Implements: UC-13, UC-14, UC-15, UC-16");
        System.out.println("=========================================");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("=========================================");
        System.out.println("Payment Management Bundle DEACTIVATED");
        System.out.println("Bundle ID: " + context.getBundle().getBundleId());
        System.out.println("=========================================");
    }
}
