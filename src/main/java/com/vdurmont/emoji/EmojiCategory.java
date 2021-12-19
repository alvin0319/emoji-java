package com.vdurmont.emoji;

public enum EmojiCategory {
    ACTIVITY("Activities"),
    FLAGS("Flags"),
    FOOD("Food & Drink"),
    NATURE("Animals & Nature"),
    OBJECTS("Objects"),
    PEOPLE("People & Body"),
    SYMBOLS("Symbols"),
    TRAVEL("Travel & Places"),
    SMILEYS("Smileys & Emotion"),
    UNKNOWN("");

    private final String displayName;

    EmojiCategory(String displayName) {
        this.displayName = displayName;
    }

    public static EmojiCategory fromString(String str) {
        for (EmojiCategory category : values()) {
            if (category.displayName.equalsIgnoreCase(str))
                return category;
        }

        return UNKNOWN;
    }

    public String getDisplayName() {
        return displayName;
    }
}
