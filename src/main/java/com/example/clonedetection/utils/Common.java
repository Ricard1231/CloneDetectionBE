package com.example.clonedetection.utils;

import com.example.clonedetection.constants.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Common {

    public static String[] getSnippets() throws IOException {
        String response = getStringFromStream(getResponseStreamFromUrl(new URL(Constant.C_DATASET_CSV_LINK), new HashMap<>()));

        String datasetRow = getRandomLineFromString(response);
        String[] datasetArr = datasetRow.split(",");

        URL code1Url = new URL(Constant.DATASET_FOLDER_LINK + datasetArr[0]);
        String code1Response = getStringFromStream(getResponseStreamFromUrl(code1Url, new HashMap<>()));

        URL code2Url = new URL(Constant.DATASET_FOLDER_LINK + datasetArr[3]);
        String code2Response = getStringFromStream(getResponseStreamFromUrl(code2Url, new HashMap<>()));
        return new String[] {
                getLinesFromString(code1Response, Integer.parseInt(datasetArr[1]), Integer.parseInt(datasetArr[2])),
                getLinesFromString(code2Response, Integer.parseInt(datasetArr[4]), Integer.parseInt(datasetArr[5]))
        };
    }

    public static InputStream getResponseStreamFromUrl(URL url, Map<String, String> headers) throws IOException {
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        if (!headers.isEmpty()) {
            headers.forEach(http::setRequestProperty);
        }
        Map<String, List<String>> header = http.getHeaderFields();
        return http.getInputStream();
    }

    public static Map<String, Object> getMapFromStream(InputStream stream) throws IOException {
        String str = getStringFromStream(stream);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(str.substring(1, str.length() - 1), Map.class);
    }

    public static String getStringFromStream(InputStream stream) throws IOException {
        if (stream == null) {
            return "No Contents";
        }

        Writer writer = new StringWriter();
        char[] buffer = new char[2048];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            int counter;
            while ((counter = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, counter);
            }
        } finally {
            stream.close();
        }
        return writer.toString();
    }

    public static String getRandomLineFromString(String str) {
        String[] strArr = str.split("\n");
        return strArr[(int) (Math.random() * (strArr.length - 1)) + 1];
    }

    public static String getLinesFromString(String str, int lineNumStart, int lineNumEnd) throws IOException {
        String[] strArr = str.split("\n");
        Writer writer = new StringWriter();
        for (int i = lineNumStart - 1; i < lineNumEnd; i++) {
            writer.write(strArr[i] + "\n");
        }
        return writer.toString();
    }

    public static List<String> extractFunctionsFromFile(String content) throws IOException {

        List<String> functions = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new StringReader(content));
        String line;
        while ((line = reader.readLine()) != null) {
            boolean isSkip = false;
            boolean isCurlyExists = false;
            String temp = line.replaceFirst("^\\s*", "");
            int countBrackets = 0;

            if (line.contains("(") && !temp.startsWith("else") && !temp.startsWith("if") && !temp.startsWith("while") && !temp.startsWith("for") && !temp.startsWith("switch") && !temp.startsWith("{")) {
                StringBuilder builder = new StringBuilder(line + "\n");
                reader.mark(content.length());
                boolean isFirstBracketCounted = false;
                int countCurly = 0;
                for (int i = 0; i < (line != null ? line.length() : 0); i++) {
                    if (line.charAt(i) == '(') {
                        isFirstBracketCounted = true;
                        countBrackets++;
                        continue;
                    }

                    if (line.charAt(i) == ')') {
                        countBrackets--;
                    }

                    if (line.charAt(i) == '{') {
                        isCurlyExists = true;
                        countCurly++;
                    }

                    if (line.charAt(i) == '}') {
                        countCurly--;
                    }

                    if (countBrackets == 0 && isFirstBracketCounted) {
                        if (line.contains("{")) {
                            continue;
                        } else {
                            break;
                        }
                    }

                    if (countBrackets < 0 || countCurly < 0) {
                        isSkip = true;
                        break;
                    }
                }

                if (isSkip) {
                    continue;
                }

                while (countBrackets > 0) {
                    line = reader.readLine();
                    builder.append(line).append("\n");

                    for (int i = 0; i < (line != null ? line.length() : 0); i++) {
                        if (line.charAt(i) == '(') {
                            countBrackets++;
                            continue;
                        }

                        if (line.charAt(i) == ')') {
                            countBrackets--;
                        }

                        if (line.charAt(i) == '{') {
                            isCurlyExists = true;
                            countCurly++;
                        }

                        if (line.charAt(i) == '}') {
                            countCurly--;
                        }

                        if (countBrackets == 0) {
                            if (line.contains("{")) {
                                continue;
                            } else {
                                break;
                            }
                        }

                        if (countBrackets < 0 || countCurly < 0) {
                            isSkip = true;
                            break;
                        }
                    }
                }

                if (isSkip) {
                    reader.reset();
                    continue;
                }

                if (countCurly == 0) {
                    line = reader.readLine();
                    builder.append(line).append("\n");

                    while (line.replaceFirst("^\\s*", "").startsWith(":") || line.replaceFirst("^\\s*", "").startsWith(",")) {
                        line = reader.readLine();
                        builder.append(line).append("\n");
                    }

                    for (int i = 0; i < (line != null ? line.length() : 0); i++) {

                        if (line.charAt(i) == '{') {
                            isCurlyExists = true;
                            countCurly++;
                        }

                        if (line.charAt(i) == '}') {
                            countCurly--;
                        }

                        if (countCurly == 0) {
                            break;
                        }

                        if (countCurly < 0) {
                            isSkip = true;
                            break;
                        }
                    }
                }

                if (isSkip || !isCurlyExists) {
                    reader.reset();
                    continue;
                }

                while (countCurly > 0 && !isSkip) {
                    line = reader.readLine();
                    builder.append(line).append("\n");

                    for (int i = 0; i < (line != null ? line.length() : 0); i++) {

                        if (line.charAt(i) == '{') {
                            countCurly++;
                        }

                        if (line.charAt(i) == '}') {
                            countCurly--;
                        }

                        if (countCurly == 0) {
                            break;
                        }

                        if (countCurly < 0) {
                            isSkip = true;
                            reader.reset();
                            break;
                        }
                    }
                }

                if (!isSkip) {
                    functions.add(builder.toString());
                }
            }
        }

//        // Regular expression pattern to match function definitions
//        String functionPattern = "^(?!else|if|for|while\\s+)([a-z0-9A-Z\s]+\\([^()]*\\)\\s*\\{[^{}]*\\})";
//
//        List<String> functions = new ArrayList<>();
//        Pattern pattern = Pattern.compile(functionPattern, Pattern.MULTILINE | Pattern.DOTALL);
//        Matcher matcher = pattern.matcher(content);
//
//        while (matcher.find()) {
//            String function = matcher.group();
//            functions.add(function);
//        }
//
        return functions;
    }

    public static String getRandomFunctionFromFile(String content) throws IOException {
        List<String> functions = extractFunctionsFromFile(content);

        for (int i = 0; i < functions.size(); i++) {
            System.out.println("function " + i + ":");
            System.out.println(functions.get(i));
        }

        if (!functions.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(functions.size());
            return functions.get(randomIndex);
        } else {
            return null;
        }
    }

    public static <T> T getRandomItemFromList(List<T> list) {

        if (!list.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(list.size());
            return list.get(randomIndex);
        } else {
            return null;
        }
    }
}
