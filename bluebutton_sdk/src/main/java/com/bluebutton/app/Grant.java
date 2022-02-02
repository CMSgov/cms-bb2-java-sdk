package com.bluebutton.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Grant {
    private static final String clientId = "";// clientId
    private static final String callBackUrl = "";// The url defined in WSO2
    private static final String authorizeUrl = "https://api.byu.edu/authorize";
    String authorizationRedirect = getAuthGrantType(callBackUrl);

    private static String getAuthGrantType(String callbackURL) {
        return authorizeUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + callbackURL
                + "&scope=openid";
    }

    // Wait for user to logIn and then
    // getAccessToken(with the authorizationCode from header name
    // 'authorization_code', callbackUrl);
    // Then call useBearerToken('access_token')
    private static void useBearerToken(String bearerToken) {
        BufferedReader reader = null;
        try {
            URL url = new URL("https://api.byu.edu:443/echo/v1/status?test=testing");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            StringWriter out = new StringWriter(
                    connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String response = out.toString();
            System.out.println(response);
        } catch (Exception e) {

        }
    }
}
