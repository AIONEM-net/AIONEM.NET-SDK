package aionem.net.sdk.utils;

import aionem.net.sdk.data.AlnData;
import aionem.net.sdk.api.AlnDaoRes;
import aionem.net.sdk.data.AlnDatas;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AlnTextUtils {

    public static boolean isEmpty(final CharSequence value) {
        return value == null || value.length() == 0;
    }

    public static String notEmpty(final Object object, final String holder) {
        return toString(object, holder);
    }

    public static String notEmpty(final Object object, final String holder1, final String holder2) {
        return toString(object, holder1, holder2);
    }

    public static String notEmptyUse(final Object object, final String value) {
        return !isEmpty(toString(object, "")) ? value : "";
    }

    public static String notEmptyUseElse(final Object object, final String useValue, final String elseValue) {
        return !isEmpty(toString(object, "")) ? useValue : elseValue;
    }

    public static String toString(final Object object, final String defaultValue) {
        return toString(object, defaultValue, "");
    }

    public static String toString(final Object object, final String default1, final String default2) {
        final String defaultValue = !isEmpty(default1) ? default1 : default2;
        if(object == null) return defaultValue;
        String value = String.valueOf(object);
        if(isEmpty(value) || value.equalsIgnoreCase("null")) {
            value = defaultValue;
        }
        return value;
    }

    private static ObjectMapper objectMapper;
    public static String toString(final Object object) {
        if(object == null) return null;

        String value = String.valueOf(object);
        try {

            if(!(object instanceof String || object instanceof Character || object instanceof StringBuilder ||
                    object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof Boolean)) {

                if(object instanceof JsonElement) {
                    value = ((JsonElement) object).getAsString();
                }else if(object instanceof AlnDaoRes) {
                    value = ((AlnDaoRes) object).getData().toString();
                }else if(object instanceof AlnData) {
                    value = ((AlnData) object).getData().toString();
                }  else if(object instanceof AlnDatas) {
                    value = ((AlnDatas) object).getDatas().toString();
                }else if(object instanceof ArrayList) {
                    JsonArray jsonArray = AlnJsonUtils.jsonArray();
                    for(Object item : ((ArrayList) object)) {
                        String itemValue = toString(item);
                        jsonArray.add(itemValue);
                    }
                    value = jsonArray.toString();
                }else if(object instanceof HttpURLConnection) {
                    value = toString(((HttpURLConnection) object).getInputStream());
                }else if(object instanceof InputStream) {
                    final StringBuilder response = new StringBuilder();
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream) object, StandardCharsets.UTF_8));
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    value = response.toString();
                    bufferedReader.close();
                }else if(object instanceof File) {
                    final StringBuilder response = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new FileReader((File) object, StandardCharsets.UTF_8));
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    value = response.toString();
                    bufferedReader.close();
                }else if(object.getClass().isArray()) {
                    if(Array.getLength(object) == 0) {
                        value = null;
                    }else {
                        value = toString(Array.get(object, 0));
                    }
                }else if(object instanceof Calendar) {
                    value = AlnDataUtils.Converter.DateUtils.calendarToString((Calendar) object);
                }else if(object instanceof Date) {
                    value = AlnDataUtils.Converter.DateUtils.dateToString((Date) object);
                }else {
                    if(objectMapper == null) objectMapper = new ObjectMapper();
                    value = objectMapper.writeValueAsString(object);
                }

            }
        }catch(Exception ignore) {
        }
        return value;
    }

    public static String notNull(final Object object) {
        return notEmpty(object, "");
    }

    public static boolean equals(final String text1, final String text2) {
        return text1 != null && text1.equals(text2);
    }
    public static boolean equalsIgnoreCase(final String text1, final String text2) {
        return text1 != null && text1.equalsIgnoreCase(text2);
    }

    public static boolean contains(final String text, final String keyword) {
        return text != null && text.contains(keyword);
    }
    public static boolean startsWith(final String text, final String keyword) {
        return text != null && text.startsWith(keyword);
    }
    public static boolean endsWith(final String text, final String keyword) {
        return text != null && text.endsWith(keyword);
    }

    public static String substring(final String text, final int beginIndex) {
        return text != null ? text.substring(beginIndex) : null;
    }
    public static String substring(final String text, final int beginIndex, final int endIndex) {
        return text != null ? text.substring(beginIndex, endIndex) : null;
    }

    public static int indexOf(final String text, final String keyword) {
        return text != null ? text.indexOf(keyword) : -1;
    }
    public static int lastIndexOf(final String text, final String keyword) {
        return text != null ? text.lastIndexOf(keyword) : -1;
    }

    public static int compareTo(final String text1, final String text2) {
        if(text1 == null && text2 == null) return 0;
        if(text1 == null) return -1;
        if(text2 == null) return 1;
        return text1.compareTo(text2);
    }
    public static int compareToIgnoreCase(final String text1, final String text2) {
        if(text1 == null && text2 == null) return 0;
        if(text1 == null) return -1;
        if(text2 == null) return 1;
        return text1.compareToIgnoreCase(text2);
    }

    public static String capitalizeFirstLetter(final String text) {
        return !isEmpty(text) ? Character.toUpperCase(text.charAt(0)) + text.substring(1) : text;
    }

    public static String join(final String... texts) {
        return joinWith(texts, "");
    }
    public static String joinWith(final String[] texts, final String separator) {
        final StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < texts.length; i++) {
            final String text = texts[i];
            if(i > 0 && i < texts.length - 1) {
                stringBuilder.append(text).append(separator);
            }else {
                stringBuilder.append(text);
            }
        }
        return stringBuilder.toString();
    }

}
