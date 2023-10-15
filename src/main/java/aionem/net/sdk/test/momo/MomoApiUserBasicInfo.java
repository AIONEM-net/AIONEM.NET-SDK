package aionem.net.sdk.test.momo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MomoApiUserBasicInfo {

    public static void main(String[] args) {

        String userUUID = "250782027141";
        userUUID = "46733123453";

        String bearerToken = MomoApiAccessToken.getToken();

        momoApiUserBasicinfo(bearerToken, userUUID);

    }

    public static void momoApiUserBasicinfo(String bearerToken, String accountHolderMSISDN) {

        try {

            String primaryKey = "7af9c6786eb3446f9f699d8a8c0ce600";
            primaryKey = "9e82c5f1eb524ea19b9e68f5a7b3b473";
            String baseUrl = "https://mtndeveloperapi.portal.mtn.co.rw";
            baseUrl = "https://sandbox.momodeveloper.mtn.com/collection/v1_0";
            String link = baseUrl + "/accountholder/msisdn/"+ accountHolderMSISDN +"/basicuserinfo";


            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("X-Target-Environment", "sandbox"); // mtnrwanda, sandbox
            httpURLConnection.setRequestProperty("Ocp-Apim-Subscription-Key", primaryKey);
            httpURLConnection.setRequestProperty("Authorization", bearerToken);

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response status: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Response body: " + response.toString());

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}