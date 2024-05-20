import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.*;
import java.util.ArrayList;
import java.util.Objects;

public class main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String apiKey = Key.apiKey;
        String city = "Umeå";
        //https://openweathermap.org/current
        city = JOptionPane.showInputDialog("City");


        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        HttpRequest weatherGet = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(weatherGet, HttpResponse.BodyHandlers.ofString());
       // System.out.println(postResponse.body());


        ArrayList<String> storage = new ArrayList<>();
        StringBuilder in = new StringBuilder();
        //wow.append("noice");
        //wow.replace(0, 5, "wow");
        //System.out.println(wow);

        for(int i = 0; i < postResponse.body().length(); i++){
            in.append(postResponse.body().charAt(i));
            if(in.toString().contains("weather")){
                StringBuilder temp = new StringBuilder();
                for (int j = 0; j < 100; j++){
                    in.append(postResponse.body().charAt(i+j));
                    if(in.toString().contains("description")){
                        j+=4;
                        char pog = ',';
                        while (postResponse.body().charAt(i+j) != pog){
                            temp.append(postResponse.body().charAt(i+j));
                            j++;
                        }
                        temp.setLength(temp.length()-1);
                        storage.add("Weather: " + temp);
                        i = i + j;
                        in.setLength(0);
                        break;
                    }
                    if (j == 99){
                        in.setLength(0);
                    }
                }
            }
            if (in.toString().contains("stations")){
                StringBuilder temp = new StringBuilder();
                for (int j = 0; j < 100; j++){
                    in.append(postResponse.body().charAt(i+j));
                    if(in.toString().contains("temp")) {
                        j += 3;
                        char pog = ',';
                        while (postResponse.body().charAt(i + j) != pog) {
                            temp.append(postResponse.body().charAt(i + j));
                            j++;
                        }
                        temp.setLength(temp.length()-1);
                        storage.add("Temperature: " + kelvinConversion(Double.parseDouble( temp.toString())) + "°C");
                        j+=14;
                        temp.setLength(0);
                        while (postResponse.body().charAt(i + j) != pog) {
                            temp.append(postResponse.body().charAt(i + j));
                            j++;
                        }
                        storage.add("Feels like: " + kelvinConversion(Double.parseDouble( temp.toString())) + "°C");

                        i = i + j;
                        in.setLength(0);
                        break;
                    }
                }
            }

        }
      //  System.out.println(in);
        for (String s : storage) {
            System.out.println(s);
        }



    }
    public static double kelvinConversion(double temperature){
        return temperature -273.15;
        //Kelvin to Celsius
    }
}
