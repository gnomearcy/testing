package eu.span.dev.osijek.di.scoping.data.ui.presenter;

import hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1.HeavyLibraryWrapper;
import eu.span.dev.osijek.di.scoping.data.api.UserManager;
import eu.span.dev.osijek.di.scoping.data.ui.view.ActivitySplash;
import eu.span.dev.osijek.di.scoping.data.utils.Validator;

public class PresenterActivitySplash
{
    public String username;

    // TODO change to IViewSplash
    private ActivitySplash view;
    private Validator validator;
    private UserManager userManager;
    private HeavyLibraryWrapper heavyLibraryWrapper;

    public PresenterActivitySplash(
            ActivitySplash view,
            Validator validator,
            UserManager manager,
            HeavyLibraryWrapper libraryWrapper)
    {
        this.view = view;
        this.validator = validator;
        this.userManager = manager;
        this.heavyLibraryWrapper = libraryWrapper;

        this.heavyLibraryWrapper.callMethod();
        this.heavyLibraryWrapper.callMethod();
        this.heavyLibraryWrapper.callMethod();
        this.heavyLibraryWrapper.callMethod();
    }
}
