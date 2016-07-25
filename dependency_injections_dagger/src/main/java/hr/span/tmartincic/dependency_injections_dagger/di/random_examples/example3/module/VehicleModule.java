package eu.span.dev.osijek.dimvp.demo.module;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.dimvp.demo.model.Motor;
import eu.span.dev.osijek.dimvp.demo.model.Vehicle;

@Module
public class VehicleModule
{
    @Provides
    Vehicle provideVehicle(Motor motor)
    {
        return new Vehicle(motor);
    }

    @Provides
    Motor provideMotor()
    {
        return new Motor();
    }
}
