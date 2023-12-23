package ru.sumarokov;

public class VirtualFile {

    private final String path;

    private String contents;

    public VirtualFile(String path) {
        this.path = path;
    }

    public VirtualFile(String path, String contents) {
        this.path = path;
        this.contents = contents;
    }

    public String getPath() {
        return path;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}


