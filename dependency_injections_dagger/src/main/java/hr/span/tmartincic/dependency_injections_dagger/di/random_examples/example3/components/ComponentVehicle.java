package eu.span.dev.osijek.dimvp.demo.components;

import dagger.Component;
import eu.span.dev.osijek.dimvp.demo.model.Vehicle;
import eu.span.dev.osijek.dimvp.demo.module.VehicleModule;

@Component(modules = {VehicleModule.class})
public interface ComponentVehicle
{
    Vehicle getVehicle();
}
