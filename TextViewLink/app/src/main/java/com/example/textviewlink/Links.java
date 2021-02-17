package com.example.textviewlink;

public class Links {
    private String name;
    private String link;
    private int start;
    private int end;

    public Links(String name, String link, int start, int end) {
        this.name = name;
        this.link = link;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Links{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
