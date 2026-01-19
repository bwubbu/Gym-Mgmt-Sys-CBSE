package com.gymmanagement.osgi.trainer.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Bundle Activator for Trainer Management Bundle
 * Handles bundle lifecycle events
 */
public class TrainerBundleActivator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("=========================================");
        System.out.println("Trainer Management Bundle ACTIVATED");
        System.out.println("Bundle ID: " + context.getBundle().getBundleId());
        System.out.println("=========================================");
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("=========================================");
        System.out.println("Trainer Management Bundle DEACTIVATED");
        System.out.println("Bundle ID: " + context.getBundle().getBundleId());
        System.out.println("=========================================");
    }
}
