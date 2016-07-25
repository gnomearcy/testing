package hr.span.tmartincic.authenticatorapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;

import hr.span.tmartincic.implementation.AccountGeneral;


public class AuthenticatorExampleAppAct extends ActionBarActivity
{
    private static final String tag = "SyncAdapter";
    private static final String STATE_DIALOG = "state_dialog";
    private static final String STATE_INVALIDATE = "state_invalidate";

    private AlertDialog alertDialog;
    private AccountManager accountManager;
    private boolean invalidate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator_example_app);

        accountManager = AccountManager.get(this);

        findViewById(R.id.btnAddAccount).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addNewAccount(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });

        findViewById(R.id.btnGetAuthToken).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showAccountPicker(AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, false);
            }
        });

        findViewById(R.id.btnGetAuthTokenConvenient).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });

        findViewById(R.id.btnInvalidateAuthToken).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showAccountPicker(AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, true);
            }
        });
    }

    //TODO test documentation
    /**
     * Get auth token for an account.
     * If account doesn't exist - add it and then return it's auth token
     * if account exists - return its' auth token
     * If more than one exist - show the dialog and return the auth token of the selected one.
     * */
    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType)
    {
        AccountManagerFuture<Bundle> future = accountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>()
                {
                    @Override
                    public void run(AccountManagerFuture<Bundle> accountManagerFuture)
                    {
                        Bundle b = null;
                        try
                        {
                            b = accountManagerFuture.getResult();
                            String authToken = b.getString(AccountManager.KEY_AUTHTOKEN);
//                            Toast.makeText(AuthenticatorExampleAppAct.this, "authToken - " + authToken)

                            String toastMsg = authToken != null ? "SUCCESS! " + authToken : "FAIL";
                            Toast.makeText(AuthenticatorExampleAppAct.this, toastMsg, Toast.LENGTH_LONG).show();
                            Log.d(tag, AuthenticatorExampleAppAct.this.getClass().getSimpleName() + " - getTokenForAccountCreateIfNeeded is " + b);

                        }
                        catch (OperationCanceledException | AuthenticatorException | IOException e)
                        {
                            e.printStackTrace();
                            Log.d(tag, "GetTokenForAccountCreateIfNeeded - ERROR");
                        }

                    }
                }, null);
    }

    private void addNewAccount(String accountType, String authTokenType)
    {
//        final AccountManagerFuture<Bundle> future = accountManager.addAccount(accountType, authTokenType, null, null, this, new AccountManagerCallback<Bundle>() {
        accountManager.addAccount(accountType, authTokenType, null, null, this, new AccountManagerCallback<Bundle>()
        {
            //Callback when the request completes
            @Override
            public void run(AccountManagerFuture<Bundle> future)
            {
                try
                {
                    Bundle bnd = future.getResult();
//                    showMessage("Account was created");
                    Log.d(tag, "AddNewAccount Bundle is " + bnd);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
//                    showMessage(e.getMessage());
                }
            }
        }, null);
    }

    //shows all accounts registered in the account manager. request an auth token upon select
    private void showAccountPicker(final String authTokenType, final boolean inv)
    {
        invalidate = inv;

        final Account[] accounts = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

        if(accounts.length == 0)
        {
            Toast.makeText(this, "No accounts available", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String[] names = new String[accounts.length];
            for(int i = 0; i < accounts.length; i++)
            {
                names[i] = accounts[i].name;
            }

//            DialogInterface.OnClickListener dialogList =

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, names);

            //Account picker
            alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Pick account")
                    .setAdapter(adapter,
                            new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which)
                                {
                                    if(invalidate)
                                    {
                                        invalidateAuthToken(accounts[which], authTokenType);
                                    }
                                    else
                                    {
                                        getExistingAccountAuthToken(accounts[which], authTokenType);
                                    }
                                }
                            })
                    .create();

            alertDialog.show();
        }
    }

    /**
     * AccountManagerFuture parameters:
     *
     * account - the account to fetch the token for
     * authTokenType - auth token type, authenticator specific string
     * options - authenticator specific options for the request --- documentation missing????
     * activity - context to start the authenticator sub-activity from
     * callback - invoked callback when request completes
     * handler - handler identifying the thread where callback executes, null for main thread
     *
     * RETURN VALUES:
     * - result Bundle with at least the following (key_account_name, key_account_type, key_authtoken we wanted)
     *
     * throws 3 different exceptions if an auth token could not be fetched
     *
     * @see http://developer.android.com/reference/android/accounts/AccountManager.html#getAuthToken(android.accounts.Account, java.lang.String, android.os.Bundle, android.app.Activity, android.accounts.AccountManagerCallback<android.os.Bundle>, android.os.Handler)
     * */
    private void invalidateAuthToken(final Account account, String authTokenType)
    {
//        accountManager.invalidateAuthToken(account.type, authTokenType);
        final AccountManagerFuture<Bundle> future = accountManager.getAuthToken(account, authTokenType, null, this, null, null);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Bundle b = future.getResult();
                    String authToken = b.getString(AccountManager.KEY_AUTHTOKEN);

                    //remove the authToken from the cache in accountManager
                    accountManager.invalidateAuthToken(account.type, authToken);

                    Looper.prepare();
                    Toast.makeText(AuthenticatorExampleAppAct.this, account.name + " invalidated", Toast.LENGTH_SHORT).show();
                }
                catch (OperationCanceledException | AuthenticatorException | IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(AuthenticatorExampleAppAct.this, "Error occured invalidating account '" + account.name + "'.", Toast.LENGTH_SHORT).show();
                }

            }
        }).start();
    }

    /**
     * Get an existing auth token for the account on the account manager
     * */
    private void getExistingAccountAuthToken(Account account, String authTokenType)
    {
        accountManager.getAuthToken(account, authTokenType, null, this,
            new AccountManagerCallback<Bundle>()
            {
                @Override
                public void run(AccountManagerFuture<Bundle> accountManagerFuture)
                {
                    try
                    {
                        Bundle b = accountManagerFuture.getResult();
                        String authToken = b.getString(AccountManager.KEY_AUTHTOKEN);
                        String toastMsg = authToken != null ? "SUCCESS!" : "FAIL";
                        Toast.makeText(AuthenticatorExampleAppAct.this, toastMsg, Toast.LENGTH_LONG).show();
                        Log.d(tag, AuthenticatorExampleAppAct.this.getClass().getSimpleName() + " - GetTokenBundle is " + b);
                    }
                    catch (OperationCanceledException | IOException | AuthenticatorException e)
                    {
                        e.printStackTrace();
                        Log.d(tag, "getExistingAccountAuthToken - ERROR");
                    }
                }
            }, null);
    }
}
