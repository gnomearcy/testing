package hr.span.tmartincic.implementation;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import static hr.span.tmartincic.implementation.AccountGeneral.*;

public class MyAuthenticator extends AbstractAccountAuthenticator
{
    private static final String tag = "SyncAdapter";
    private Context context;

    public MyAuthenticator(Context context)
    {
        super(context);
        this.context = context;
        Log.d(tag, this.getClass().getSimpleName() + " - constructor");

    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType)
    {
        return null;
    }

    /** Called when the user wants to log in and add a new account to the device.
     * Returns a Bundle with Intent inside to start special activity
     * */
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException
    {
        Log.d(tag, this.getClass().getSimpleName() + " - addAccount");

        final Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(AuthActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        Bundle b = new Bundle();
        b.putParcelable(AccountManager.KEY_INTENT, intent);
        return b;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {
        //error handling for the token type we don't support - aka when other apps want to authenticate through this authenticator
        if (!authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        Log.d(tag, this.getClass().getSimpleName() + " getAuthToken");

        //ask account manager for the auth token if it exists, if it does, return same bundle as in addAccount()
        final AccountManager am = AccountManager.get(context);

        /** Tries to read the auth token from AccountManager's cache.
         * If it doesn't exist, null is returned.
         * */
        String authToken = am.peekAuthToken(account, authTokenType);

        /** Auth token doesn't exist in the cache. */
        if(TextUtils.isEmpty(authToken))
        {
            Log.d(tag, this.getClass().getSimpleName() + " - authToken is empty");

            String password = am.getPassword(account);
            if(password != null)
            {
                Log.d(tag, this.getClass().getSimpleName() + "> re-authenticating with the existing password");

                try
                {
                    //we have the password and no token in cache, retrieve it
                    authToken = sServerAuthenticate.userSignIn(account.name, password, authTokenType);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        //if there is a token in the cache, return it
        if(!TextUtils.isEmpty(authToken))
        {
            Log.d(tag, this.getClass().getSimpleName() + " - returning auth token from cache");

            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        Log.d(tag, this.getClass().getSimpleName() + " - unable to get password / authToken is empty");

        //we couldn't get the password and auth token is empty
        //re-prompt the user for their credentials -> display UI for authentication
        final Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(AuthActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(AuthActivity.ARG_ACCOUNT_NAME, account.name);
        intent.putExtra(AuthActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType)
    {
        if (AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
            return AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        else if (AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
            return AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        else
            return authTokenType + " (Label)";
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException
    {
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return result;
    }
}
