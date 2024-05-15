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


        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        HttpRequest weatherGet = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(weatherGet, HttpResponse.BodyHandlers.ofString());
        System.out.println(postResponse.body());


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
                        System.out.println(temp);
                        storage.add("weather: " + temp);
                        i = i + j;
                        in.setLength(0);
                        break;
                    }
                    if (j == 99){
                        in.setLength(0);
                    }
                }
            }
        }
        System.out.println(in);
        System.out.println(storage.get(0));



    }
}
