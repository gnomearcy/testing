package eu.span.dev.osijek.proguard.rules;

import dagger.Component;

@DetailsScope
@Component(dependencies = ComponentMain.class, modules = ModuleDetails.class)
public interface ComponentDetails
{
    void inject(FragmentDetails target);
}
