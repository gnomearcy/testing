package eu.span.dev.osijek.dimvp.demo.components;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import eu.span.dev.osijek.dimvp.demo.module.ModuleApplication;

@Singleton
@Component(modules = ModuleApplication.class)
public interface ComponentApplication
{
    Application getApp();
}
