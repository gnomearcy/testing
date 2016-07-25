package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.Motor;
import eu.span.dev.osijek.di.compdepend.model.Vehicle;

@Module
public class VehicleModule
{
    @Singleton
    @Provides
    Vehicle provideVehicle(Motor motor)
    {
        return new Vehicle(motor);
    }

    @Singleton
    @Provides
    Motor provideMotor()
    {
        return new Motor();
    }
}
