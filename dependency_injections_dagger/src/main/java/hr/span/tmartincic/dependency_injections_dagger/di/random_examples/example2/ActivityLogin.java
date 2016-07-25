package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.span.dev.osijek.dimvp.demo2.presenter.LoginPresenter;

public class ActivityLogin extends AppCompatActivity
{
    /******************************************************************************************
     * - LoginActivity ONLY knows how to display views and sending events and data to the presenter
     * - LoginActivity doesn't know anything about the model (SynchronousLoginInteractor)
     * *******************************************************************************************
     */

    @Bind(R.id.login_email_edit_text)
    EditText emailEditText;

    @Bind(R.id.login_password_edit_text)
    EditText passwordEditText;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (BuildConfig.DEBUG) {
            emailEditText.setText("anthing@gmail.com");
            passwordEditText.setText("$uper$ecret");
        }
    }
}
