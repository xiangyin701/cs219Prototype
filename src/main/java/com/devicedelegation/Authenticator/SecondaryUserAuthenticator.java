package com.devicedelegation.Authenticator;

// SecondaryUserAuthenticator is configured as to allow users to make a secondary confirmation whether the endpoint to be called is authenticated.
public class SecondaryUserAuthenticator {
    public SecondaryUserAuthenticator() {

    }

    public boolean isAuthenticated (Integer delegatorId, String endpoint, String args) {
        // In real implementation, this function should be binded with user's authentication mechanisms
        return delegatorId != null && endpoint != null && args != null;
    }
}
