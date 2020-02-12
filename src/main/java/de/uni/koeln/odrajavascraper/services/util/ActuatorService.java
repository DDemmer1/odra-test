package de.uni.koeln.odrajavascraper.services.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.*;


@Service
public class ActuatorService {

    @Value("${server.port}")
    private String port;

    public List<String> getMappings() throws IOException, JSONException {
        String address = InetAddress.getLocalHost().getHostAddress();
        URL url = new URL("http://"+address + ":" + port + "/actuator/mappings");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String siteContent = content.toString();

        JSONObject obj = new JSONObject(siteContent);
        JSONArray dispServ = obj.getJSONObject("contexts").getJSONObject("application").getJSONObject("mappings").getJSONObject("dispatcherServlets").getJSONArray("dispatcherServlet");

        List<String> list = new ArrayList<>();
        for (int i = 0; i < dispServ.length(); i++) {
            JSONObject current = dispServ.getJSONObject(i);
            String mapping = current.getJSONObject("details").getJSONObject("requestMappingConditions").getJSONArray("patterns").getString(0);
            if(!mapping.contains("actuator") && !mapping.equals("/") && !mapping.equals("/error")){
                list.add(mapping);

            }
        }

        return list;
    }

}
