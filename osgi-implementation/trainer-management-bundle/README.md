# Trainer Management Bundle

## Overview

This is the **Trainer Management Bundle** for the Gym Management System, implemented as an OSGi bundle. It provides the implementation for managing gym trainers.

## Features

- **Add Trainer**: Register new trainers.
- **Update Trainer**: Modify existing trainer details.
- **Delete Trainer**: Remove trainers from the system.
- **Get Trainer**: Retrieve specific trainer details.
- **Get All Trainers**: List all registered trainers.
- **Search**: Find trainers by name.

## OSGi Service

This bundle exports the `ITrainerService` implementation.

- **Interface**: `com.gymmanagement.osgi.base.service.ITrainerService`
- **Implementation**: `com.gymmanagement.osgi.trainer.internal.TrainerServiceImpl`
- **Component Name**: `com.gymmanagement.osgi.trainer.service`

## Dependencies

- **base-library-bundle**: Provides the `Trainer` entity and `ITrainerService` interface.

## Usage

When installed in an OSGi framework (like Apache Felix), this bundle automatically registers the `ITrainerService`. Other bundles can consume this service to perform trainer-related operations.
