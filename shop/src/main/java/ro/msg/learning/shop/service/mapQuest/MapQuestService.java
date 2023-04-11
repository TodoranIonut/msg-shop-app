package ro.msg.learning.shop.service.mapQuest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.domain.entity.Stock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MapQuestService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();



    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private class MapBody {
        private String[] locations;
        private Map<String, Boolean> options;
    }

    public ResponseEntity<String> getMapQuestRouteResponse(String[] locations) throws JsonProcessingException {
        //build request url with MapQuest key
        String resourceUrl = "https://www.mapquestapi.com/directions/v2/routematrix";
        String mapQuestKey = "My3otDNiw3oOKwPY0Ut7Zh2a1jjs1Ytt";
        String requestUrl = resourceUrl + "?key=" + mapQuestKey;

        //build request object
        MapBody mapBody = new MapBody();
        mapBody.setLocations(locations);
        mapBody.setOptions(new HashMap<>() {{
            put("allToAll", true);
        }});
        String jsonObject = objectMapper.writeValueAsString(mapBody);

        //build request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //execute POST request with
        HttpEntity<String> request = new HttpEntity<>(jsonObject, headers);
        return restTemplate.postForEntity(requestUrl, request, String.class);
    }

    public List<Stock> getSortedByDistanceLocations(Stock[] stocks, String locationToShipped) throws JsonProcessingException {

        int numStocks = stocks.length;
        //build location object array
        String[] locations = new String[numStocks + 1];
        locations[0] = locationToShipped;
        for (int i = 0; i<numStocks;i++){
            locations[i+1] = stocks[i].getLocation().getCityAddress() + ", " +
                    stocks[i].getLocation().getProvinceAddress();
        }
        //get MapQuest response object
        String responseBody = getMapQuestRouteResponse(locations).getBody();
        JsonNode root = objectMapper.readTree(responseBody);

        //get distance matrix
        JsonNode matrix = root.get("distance");
        JsonNode firstRow = matrix.get(0);
        int numDistances = matrix.get(0).size() - 1;

        //build array of distances
        double[] distances = new double[numDistances];
        for (int i = 0; i < numDistances; i++) {
            distances[i] = firstRow.get(i+1).asDouble();
        }

        //sort distances
        double[] sortedDistances = Arrays.stream(distances).sorted().toArray();

        //build array of stocks by distances array order
        Stock[] newSortedStock = new Stock[numDistances];
        for (int i = 0; i < numDistances; i++) {
            for (int j = 0; j < numDistances; j++) {
                if (sortedDistances[i] == distances[j]) {
                    newSortedStock[i] = stocks[j];
                    break;
                }
            }
        }
        return Arrays.stream(newSortedStock).toList();
    }
}
