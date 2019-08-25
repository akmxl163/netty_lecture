package groupbyday;

public class BaseFinger {
    private String start;
    private String end;
    private String fPath;

    public BaseFinger(String start, String end, String fPath) {
        this.start = start;
        this.end = end;
        this.fPath = fPath;
    }

    @Override
    public String toString() {
        return "BaseFinger{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", fPath='" + fPath + '\'' +
                '}';
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getfPath() {
        return fPath;
    }

    public void setfPath(String fPath) {
        this.fPath = fPath;
    }
}
