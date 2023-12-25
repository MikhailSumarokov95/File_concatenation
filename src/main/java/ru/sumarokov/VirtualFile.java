package ru.sumarokov;

import java.util.List;
import java.util.Objects;

public class VirtualFile {

    private final String path;

    private String contents;

    private List<VirtualFile> dependencies;

    private Boolean isSorted = false;

    public VirtualFile(String path) {
        this.path = path;
    }

    public VirtualFile(String path,
                       String contents,
                       List<VirtualFile> dependencies) {
        this.path = path;
        this.contents = contents;
        this.dependencies = dependencies;

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

    public List<VirtualFile> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<VirtualFile> dependencies) {
        this.dependencies = dependencies;
    }

    public Boolean getIsSorted() {
        return isSorted;
    }

    public void setIsSorted(Boolean sorted) {
        isSorted = sorted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirtualFile that = (VirtualFile) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    @Override
    public String toString() {
        return "VirtualFile{" +
                "path='" + path + '\'' +
                ", contents='" + contents + '\'' +
                ", dependencies=" + dependencies +
                ", isSorted=" + isSorted +
                '}';
    }
}


