package me.indexyz.strap.object;


public enum ChatType {
    PRIVATE("private"),
    UNKNOWN("unknown");

    public String text;

    ChatType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static ChatType fromString(String text) {
        for (ChatType b : ChatType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return ChatType.UNKNOWN;
    }
}
