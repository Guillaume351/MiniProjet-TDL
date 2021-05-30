package fr.n7.stl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdresseTools {

    final private String apiEndpoint = "https://api-adresse.data.gouv.fr/search/?q=";

    public static void main(String[] args) {
        AdresseTools adresseTools = new AdresseTools();

        CoordGps gps = adresseTools.adresseToGps("2 Rue Charles Camichel 31000");

        System.out.println("Long " + gps.longitude + " Lat " + gps.latitude);
    }

    public CoordGps adresseToGps(String adresse) {
        float longitude = 0;
        float latitude = 0;

        StringBuilder requete = new StringBuilder(this.apiEndpoint);

        String[] motsAdresse = adresse.split(" ");

        for (String mot : motsAdresse) {
            requete.append(mot).append("+");
        }
        requete.append("limit=1");


        try {
            URL url = new URL(requete.toString());
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            // On lit la reponse
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();


            //System.out.println(content);

            // On ferme la connexion
            con.disconnect();

            // On parse pas très proprement pour récupérer les coordonnées
            int index = content.indexOf("\"coordinates\"");
            String coordinatesString = content.substring(index, index + 35);
            coordinatesString = coordinatesString.replace("\"coordinates\": ", "");
            coordinatesString = coordinatesString.replace("[", "").replace("]", "");

            String[] coords = coordinatesString.split(",");
            longitude = Float.parseFloat(coords[0]);
            latitude = Float.parseFloat(coords[1]);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CoordGps(latitude, longitude);
    }

}

