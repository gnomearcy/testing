package eu.span.dev.osijek.di.compdepend.module;

import dagger.Module;
import dagger.Provides;
import eu.span.dev.osijek.di.compdepend.model.Car;
import eu.span.dev.osijek.di.compdepend.model.Engine;
import eu.span.dev.osijek.di.compdepend.model.Seat;

@Module
public class ModuleCar
{
    @Provides
    Car provideCar(
                    Seat frontLeft,
                    Seat frontRight,
                    Seat backLeft,
                    Seat backRight,
                    Engine engine)
    {
        return new Car(
                frontLeft,
                frontRight,
                backLeft,
                backRight,
                engine);
    }
}
