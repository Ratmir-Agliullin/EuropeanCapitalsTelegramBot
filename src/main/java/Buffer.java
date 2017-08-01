import java.io.Serializable;

public class Buffer implements Serializable{
    private String text;

    public Buffer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
