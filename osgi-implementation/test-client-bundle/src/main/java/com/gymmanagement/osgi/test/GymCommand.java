package com.gymmanagement.osgi.test;

import org.osgi.framework.BundleContext;

public class GymCommand {
    private final BundleContext context;

    public GymCommand(BundleContext context) {
        this.context = context;
    }

    public void machine() {
        System.out.println("Starting Machine Demo...");
        new InteractiveMachineDemo(context).startInteractiveMenu();
    }

    public void report() {
        System.out.println("Starting Report Demo...");
        new InteractiveReportDemo(context).startInteractiveMenu();
    }
}
