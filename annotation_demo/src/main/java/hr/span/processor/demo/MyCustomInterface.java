package hr.span.processor.demo;

import android.view.View;

import hr.span.processor.api.Generate;

@Generate
public interface MyCustomInterface
{
    String onCustomInterface(View v);
}
