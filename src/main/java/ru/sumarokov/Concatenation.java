package ru.sumarokov;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.*;

public class Concatenation {

    public void concatenation(String pathParentFileDirectory, String pathResult) {
        File rootFile = new File(pathParentFileDirectory);
        List<File> files = new ArrayList<>();
        getFiles(rootFile, files);
        Map<VirtualFile, List<VirtualFile>> dependence = getDependencies(files, pathParentFileDirectory);
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

    private Map<VirtualFile, List<VirtualFile>> getDependencies(List<File> files, String parentPath) {
        List<VirtualFile> virtualFiles = new ArrayList<>();
        files.forEach(f -> virtualFiles.add(new VirtualFile(f.getPath())));
        Map<VirtualFile, List<VirtualFile>> dependencies = new HashMap<>();
        for (File file : files) {
            List<VirtualFile> fileDependencies = new ArrayList<>();
            StringBuilder fileContents = new StringBuilder();
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("require ‘") && line.endsWith("’")) {
                        line = line.replaceFirst("require ‘", "");
                        line = line.substring(0, line.length() - 1);
                        String path = line;
                        VirtualFile codependent = virtualFiles.
                                stream()
                                .filter(v -> v.getPath().equals(convertToSystemPath(path, parentPath)))
                                .findFirst()
                                .orElseThrow(() -> new InvalidPathException(convertToSystemPath(path, parentPath), "The file the link points to does not exist"));
                        fileDependencies.add(codependent);
                    } else {
                        fileContents.append(line).append("\n");
                    }
                }
                VirtualFile fileDependence = new VirtualFile(file.getPath(), fileContents.toString());
                dependencies.put(fileDependence, fileDependencies);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return dependencies;
    }

    private String convertToSystemPath(String path, String parentPath) {
       return parentPath + File.separator + path.replace("/", File.separator) + ".txt";
    }
}
