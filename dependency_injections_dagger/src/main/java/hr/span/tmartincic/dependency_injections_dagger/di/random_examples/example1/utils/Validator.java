package eu.span.dev.osijek.di.scoping.data.utils;

import android.text.TextUtils;

public class Validator
{
    public Validator(){}

    public boolean validUsername(String username)
    {
        return !TextUtils.isEmpty(username);
    }
}
