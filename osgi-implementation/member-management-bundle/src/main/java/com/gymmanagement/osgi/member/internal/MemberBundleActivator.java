package com.gymmanagement.osgi.member.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Bundle Activator for Member Management Bundle
 * Handles bundle lifecycle events
 */
public class MemberBundleActivator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("=========================================");
        System.out.println("Member Management Bundle ACTIVATED");
        System.out.println("Bundle ID: " + context.getBundle().getBundleId());
        System.out.println("=========================================");
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("=========================================");
        System.out.println("Member Management Bundle DEACTIVATED");
        System.out.println("Bundle ID: " + context.getBundle().getBundleId());
        System.out.println("=========================================");
    }
}

