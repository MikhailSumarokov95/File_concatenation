package ru.sumarokov;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Concatenation {

    public void concatenation(String pathParentFileDirectory, String pathResult) {
        File rootFile = new File(pathParentFileDirectory);
        List<File> files = new ArrayList<>();
        getFiles(rootFile, files);
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
}
