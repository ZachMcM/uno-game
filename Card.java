public class Card {
    private String value;
    private String color;

    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String reset = "\u001B[0m";

    public Card(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    } 

    public String toString() {
        String data = "";
        if (value.equals("wild")) {
            data = "wild";
        } else if (value.equals("+4")) {
            data = "+4";
        } else {
            switch (color) {
                case "red": data += red + value + reset;
                            break;
                case "green": data += green + value + reset;
                            break;
                case "yellow": data += yellow + value + reset;
                            break;
                case "blue": data += blue + value + reset;
                            break;
            }
        }

        return data;
    }
}
