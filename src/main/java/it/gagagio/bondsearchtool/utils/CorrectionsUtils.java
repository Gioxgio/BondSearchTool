package it.gagagio.bondsearchtool.utils;

import it.gagagio.bondsearchtool.model.Correction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class CorrectionsUtils {

    private static final String SEPARATOR = ",";
    private static final String PATH = "classpath:/corrections.csv";

    @Value(PATH)
    private Resource csvResource;

    public List<Correction> getCorrections() throws IOException {
        return Files.readAllLines(csvResource.getFile().toPath())
                .stream()
                .map(line -> Correction.fromCsvRow(line.split(SEPARATOR))).toList();
    }
}
