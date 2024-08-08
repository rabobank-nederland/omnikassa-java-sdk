package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import kong.unirest.json.JSONObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileConverter {

    public JSONObject convert(String fileName) {
        String fileContent = readFile(fileName);
        return new JSONObject(fileContent);
    }

    private String readFile(String fileName)  {
        try {
            URL resource = getClass().getClassLoader().getResource(fileName);
            Path path = Paths.get(resource.toURI()).toAbsolutePath();
            return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
