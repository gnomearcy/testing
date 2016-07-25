package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1;

import java.util.Observable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import eu.span.dev.osijek.di.scoping.data.utils.SimpleObserver;

public class HeavyLibraryWrapper
{
    private HeavyExternalLibrary heavyExternalLibrary;

    private boolean isInitialized = false;

    ConnectableObservable<HeavyExternalLibrary> initObservable;

    public HeavyLibraryWrapper() {

        initObservable = Observable.create(new Observable.OnSubscribe<HeavyExternalLibrary>()
        {
            @Override
            public void call(Subscriber<? super HeavyExternalLibrary> subscriber) {
                HeavyLibraryWrapper.this.heavyExternalLibrary = new HeavyExternalLibrary();
                HeavyLibraryWrapper.this.heavyExternalLibrary.init();
                subscriber.onNext(heavyExternalLibrary);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).publish();

        initObservable.subscribe(new SimpleObserver<HeavyExternalLibrary>()
        {
            @Override
            public void onNext(HeavyExternalLibrary heavyExternalLibrary) {
                isInitialized = true;
            }
        });

        initObservable.connect();
    }


    public void callMethod()
    {
        if(isInitialized)
            heavyExternalLibrary.callMethod();
        else
        {
            initObservable.subscribe(new SimpleObserver<HeavyExternalLibrary>(){
                @Override
                public void onNext(HeavyExternalLibrary heavyExternalLibrary)
                {
                    heavyExternalLibrary.callMethod();
                }
            });
        }
    }

}
