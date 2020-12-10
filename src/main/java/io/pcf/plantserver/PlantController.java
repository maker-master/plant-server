package io.pcf.plantserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantController {

    private Map<Long, Plant> plantMap = new HashMap<>();

    private List<Plant> plants = Arrays.asList(
            new Plant(1L, "Plant 1", "Description 1", 1.1f),
            new Plant(2L, "Plant 2", "Description 2", 1.2f),
            new Plant(3L, "Plant 3", "Description 3", 1.3f),
            new Plant(4L, "Plant 4", "Description 4", 1.4f)
    );

    private long idCounter;

    private RestOperations restOperations;

    @Value("${random.greeting.endpoint}")
    private String random_greeting_endpoint;

    @Value("${greeting.server.endpoint}")
    private String greetingServerEndpoint;

    public PlantController(RestOperations restOperations) {
        plants.forEach(plant -> plantMap.put(plant.getId(), plant));
        idCounter = plants.size() + 1;

        this.restOperations = restOperations;
    }

    @GetMapping
    public ResponseEntity<List<Plant>> getPlants() {
        return new ResponseEntity<>(plants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        Plant plant = plantMap.get(id);
        HttpStatus status = plant != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(plant, status);
    }

    @PostMapping
    public ResponseEntity<Plant> createPlant(@RequestBody Plant plant) {
        Long newId = idCounter++;
        Plant createdPlant = new Plant(newId, plant.getName(), plant.getDescription(), plant.getHeight());
        plantMap.put(newId, createdPlant);
        return new ResponseEntity<>(createdPlant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @RequestBody Plant plant) {
        if (!plantMap.containsKey(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Plant updatedPlant = new Plant(id, plant.getName(), plant.getDescription(), plant.getHeight());
        plantMap.replace(id, updatedPlant);

        return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePlant(@PathVariable Long id) {
        if (!plantMap.containsKey(id)) return new ResponseEntity(HttpStatus.NOT_FOUND);

        plantMap.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}/greet")
    public ResponseEntity<String> getGreetingPlant(@PathVariable Long id) {
//        Greeting greeting = restOperations.getForObject(random_greeting_endpoint, Greeting.class);
        String endpoint = greetingServerEndpoint + "/api/v1/greetings/random";
        Greeting greeting = restOperations.getForObject(endpoint, Greeting.class);
        Plant plant = plantMap.get(id);
        String plantGreeting = greeting.getMessage() + " " + plant.getName();

        return new ResponseEntity<>(plantGreeting, HttpStatus.OK);
    }
}
