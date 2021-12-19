package com.vdurmont.emoji;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the emojis from a JSON database.
 *
 * @author Vincent DURMONT [vdurmont@gmail.com]
 */
public class EmojiLoader {
    /**
     * No need for a constructor, all the methods are static.
     */
    private EmojiLoader() {
    }

    /**
     * Loads a JSONArray of emojis from an InputStream, parses it and returns the
     * associated list of {@link com.vdurmont.emoji.Emoji}s
     *
     * @param stream the stream of the JSONArray
     * @return the list of {@link com.vdurmont.emoji.Emoji}s
     * @throws IOException if an error occurs while reading the stream or parsing
     *                     the JSONArray
     */
    public static List<Emoji> loadEmojis(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        JSONArray emojisJSON = new JSONArray(new JSONTokener(reader));
        List<Emoji> emojis = new ArrayList<>(emojisJSON.length());
        for (int i = 0; i < emojisJSON.length(); i++) {
            Emoji emoji = buildEmojiFromJSON(emojisJSON.getJSONObject(i));
            if (emoji != null) {
                emojis.add(emoji);
            }
        }
        return emojis;
    }

    protected static Emoji buildEmojiFromJSON(JSONObject json) {
        if (!json.has("emoji")) {
            return null;
        }

        byte[] bytes = json.getString("emoji").getBytes(StandardCharsets.UTF_8);
        boolean supportsFitzpatrick = json.optBoolean("skin_tones", false);
        List<String> aliases = jsonArrayToStringList(json.getJSONArray("aliases"));
        List<String> tags = jsonArrayToStringList(json.getJSONArray("tags"));
        String description = json.getString("description");
        EmojiCategory category = EmojiCategory.fromString(json.optString("category", "UNKNOWN"));
        return new Emoji(description, supportsFitzpatrick, category, aliases, tags, bytes);
    }

    private static List<String> jsonArrayToStringList(JSONArray array) {
        List<String> strings = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            strings.add(array.getString(i));
        }
        return strings;
    }
}
