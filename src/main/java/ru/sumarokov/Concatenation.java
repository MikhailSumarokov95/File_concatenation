package ru.sumarokov;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Concatenation {

    public void concatenation(String pathParentFileDirectory, String pathResult) {
        File rootFile = new File(pathParentFileDirectory);
        List<File> files = new ArrayList<>();
        getFiles(rootFile, files);
        getDependencies(files);
    }

    private void getFiles(File rootFile, List<File> resultFiles) {
        File[] files = rootFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file, resultFiles);
            } else {
                resultFiles.add(file);
            }
        }
    }

    private Map<String, List<String>> getDependencies(List<File> files) {
        Map<String, List<String>> dependencies = new HashMap<>();
        for (File file : files) {
            List<String> fileDependencies = new ArrayList<>();
            StringBuilder fileContents = new StringBuilder();
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("require ‘") && line.endsWith("’")) {
                        line = line.replaceFirst("require ‘", "");
                        line = line.substring(0, line.length() - 1);
                        fileDependencies.add(line);
                    } else {
                        fileContents.append(line);
                    }
                }
                dependencies.put(fileContents.toString(), fileDependencies);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return dependencies;
    }
}
