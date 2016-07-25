package hr.span.tmartincic.implementation;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static hr.span.tmartincic.implementation.AccountGeneral.*;

public class AuthActivity extends AccountAuthenticatorActivity
{
    public static final String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public final static String PARAM_USER_PASS = "USER_PASS";
    private final int REQ_SIGNUP = 1;
    private static final String tag = "SyncAdapter";

    private AccountManager accountManager;
    private String authTokenType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        accountManager = AccountManager.get(getBaseContext());

        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME); //todo ovog nema u myauthenticator dodavanju u intent
        authTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);

        if (authTokenType == null)
            authTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;

        if (accountName != null) ((TextView) findViewById(R.id.accountName)).setText(accountName);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Since there can only be one AuthenticatorActivity, we call the sign up activity, get his results,
                // and return them in setAccountAuthenticatorResult(). See finishLogin().
                Intent signup = new Intent(getBaseContext(), SignUpAct.class);
                signup.putExtras(getIntent().getExtras());
                startActivityForResult(signup, REQ_SIGNUP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQ_SIGNUP && resultCode == RESULT_OK)
        {
            finishLogin(data);
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void submit()
    {
        final String userName = ((TextView) findViewById(R.id.accountName)).getText().toString();
        final String userPass = ((TextView) findViewById(R.id.accountPassword)).getText().toString();

        Log.d(tag, "Username - " + userName);
        Log.d(tag, "password - " + userPass);
        final String accountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        Log.d(tag, "Account type - " + accountType);

        new AsyncTask<String, Void, Intent>()
        {
            @Override
            protected Intent doInBackground(String... params)
            {
                Log.d(tag, this.getClass().getSimpleName() + " started authenticating.");
                String authToken = null;
                Bundle data = new Bundle();

                try{
                    authToken = sServerAuthenticate.userSignIn(userName, userPass, accountType);
                    data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                    data.putString(PARAM_USER_PASS, userPass);
                }
                catch (Exception e){
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                final Intent i = new Intent();
                i.putExtras(data);
                return i;
            }

            @Override
            protected void onPostExecute(Intent intent)
            {
                //Error occured, show appropriate message
                if(intent.hasExtra(KEY_ERROR_MESSAGE))
                {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(AccountManager.KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    finishLogin(intent);
                }
            }
        }.execute();
    }

    //send the response back to Authenticator - create a new account OR set the new password
    /*
    * AccountManager operates on accounts, create one
    */
    private void finishLogin(Intent data)
    {
        Log.d(tag, this.getClass().getSimpleName() + " finishLogin");

        String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = data.getStringExtra(PARAM_USER_PASS);
        String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

        final Account account = new Account(accountName, accountType);

        //add the account explicitly to the manager, else update the password
        if(getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false))
        {
            Log.d(tag, this.getClass().getSimpleName() + " addAccountExplicitly");
            String authToken = data.getStringExtra(AccountManager.KEY_AUTHTOKEN);

            accountManager.addAccountExplicitly(account, accountPassword, null);
            accountManager.setAuthToken(account, authTokenType, authToken);
        }
        else
        {
            Log.d(tag, this.getClass().getSimpleName() + " updating password");
            accountManager.setPassword(account, accountPassword);
        }

        //AccountManager is set up
        //return the information back to the Authenticator
        setAccountAuthenticatorResult(data.getExtras());
        setResult(RESULT_OK, data);
        finish();
    }
}
