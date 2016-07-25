package hr.span.tmartincic.implementation;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static hr.span.tmartincic.implementation.AccountGeneral.*;

public class SignUpAct extends ActionBarActivity
{
    private static final String tag = "SyncAdapter";
    private String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountType = getIntent().getStringExtra(AuthActivity.ARG_ACCOUNT_TYPE);

        findViewById(R.id.alreadyMember).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createAccount();
            }
        });
    }

    private void createAccount()
    {
        new AsyncTask<String, Void, Intent>()
        {
            String name = ((TextView) findViewById(R.id.name)).getText().toString().trim();
            String accountName = ((TextView) findViewById(R.id.accountName)).getText().toString().trim();
            String accountPassword = ((TextView) findViewById(R.id.accountPassword)).getText().toString().trim();

            @Override
            protected Intent doInBackground (String...params){

            Log.d(tag, SignUpAct.this.getClass().getSimpleName() + " > Started authenticating");

            String authtoken = null;
            Bundle data = new Bundle();
            try
            {
                authtoken = sServerAuthenticate.userSignUp(name, accountName, accountPassword, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);

                data.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
                data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                data.putString(AuthActivity.PARAM_USER_PASS, accountPassword);
            }
            catch (Exception e)
            {
                data.putString(AuthActivity.KEY_ERROR_MESSAGE, e.getMessage());
            }

            final Intent res = new Intent();
            res.putExtras(data);
            return res;
        }

            @Override
            protected void onPostExecute (Intent intent)
            {
                if(intent.hasExtra(AuthActivity.KEY_ERROR_MESSAGE))
                {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(AuthActivity.KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }.execute();
    }
}
