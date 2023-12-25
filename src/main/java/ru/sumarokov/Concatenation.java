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
        List<VirtualFile> dependencies = getDependencies(files, pathParentFileDirectory);
        List<VirtualFile> sortedFile = new ArrayList<>();
        for (VirtualFile file : dependencies) {
            sortFile(file, sortedFile);
        }
    }

    private void sortFile(VirtualFile file, List<VirtualFile> sortedFiles) {
        if (!file.getIsSorted()) {
            if (!file.getDependencies().isEmpty()) {
                for (VirtualFile dependence : file.getDependencies()) {
                    sortFile(dependence, sortedFiles);
                }
            }
            sortedFiles.add(file);
            file.setIsSorted(true);
        }
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

    private List<VirtualFile> getDependencies(List<File> files, String parentPath) {
        List<VirtualFile> virtualFiles = new ArrayList<>();
        files.forEach(f -> virtualFiles.add(new VirtualFile(f.getPath())));
        for (File file : files) {
            List<VirtualFile> fileDependencies = new ArrayList<>();
            StringBuilder fileContents = new StringBuilder();
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("require ‘") && line.endsWith("’")) {
                        line = line.replaceFirst("require ‘", "");
                        String path = line.substring(0, line.length() - 1);
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
                VirtualFile fileDependence = virtualFiles.
                        stream()
                        .filter(v -> v.getPath().equals(file.getPath()))
                        .findFirst()
                        .orElseThrow(() -> new InvalidPathException(file.getPath(), "The file the link points to does not exist"));
                fileDependence.setContents(fileContents.toString());
                fileDependence.setDependencies(fileDependencies);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return virtualFiles;
    }

    private String convertToSystemPath(String path, String parentPath) {
        return parentPath + File.separator + path.replace("/", File.separator) + ".txt";
    }
}
