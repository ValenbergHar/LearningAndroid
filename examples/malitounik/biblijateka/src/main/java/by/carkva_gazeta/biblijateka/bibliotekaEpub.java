package by.carkva_gazeta.biblijateka;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class bibliotekaEpub {

    private String navigation;
    private String rootDir = "/";
    private final ArrayList<ArrayList<String>> navig;
    private final String patch;
    private String content_opf = "content.opf";

    bibliotekaEpub(String dirPatch) {
        patch = getFullPatch(dirPatch);
        getBookNavigation();
        navig = new ArrayList<>();
    }

    @NonNull
    private String getFullPatch(String dirPatch) {
        StringBuilder builder = new StringBuilder();
        try {
            File file = new File(dirPatch + "/META-INF/container.xml");
            FileReader inputStream = new FileReader(file);
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        int t1 = builder.toString().indexOf("full-path=\"");
        int t2 = builder.toString().indexOf("\"", t1 + 11);
        content_opf = builder.toString().substring(t1 + 11, t2);
        int t3 = content_opf.lastIndexOf("/");
        if (t3 != -1) {
            rootDir = "/" + content_opf.substring(0, t3 + 1);
            content_opf = content_opf.substring(t3 + 1);
        }
        return dirPatch + rootDir;
    }

    private void getBookNavigation() {
        StringBuilder builder = new StringBuilder();
        try {
            File file = new File(patch + "toc.ncx");
            FileReader inputStream = new FileReader(file);
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        navigation = builder.toString();
    }

    String getBookTitle() {
        int t1 = navigation.indexOf("<docTitle>");
        int t2 = navigation.indexOf("<text>", t1);
        int t3 = navigation.indexOf("</text>", t2);
        return navigation.substring(t2 + 6, t3);
    }

    ArrayList<ArrayList<String>> getContent() {
        if (navig.size() == 0) {
            String[] rew = navigation.split("<navPoint");
            for (int i = 1; i < rew.length; i++) {
                ArrayList<String> temp = new ArrayList<>();
                int t1 = rew[i].indexOf("<navLabel>");
                int t2 = rew[i].indexOf("<text>", t1);
                int t3 = rew[i].indexOf("</text>", t2);
                int t4 = rew[i].indexOf("<content src=\"", t3);
                int t5 = rew[i].indexOf("\"", t4 + 14);
                int t6 = rew[i].indexOf("playOrder=\"");
                int t7 = rew[i].indexOf("\"", t6 + 11);
                temp.add(rew[i].substring(t2 + 6, t3));
                temp.add(rootDir + rew[i].substring(t4 + 14, t5));
                temp.add(rew[i].substring(t6 + 11, t7));
                navig.add(temp);
            }
        }
        return navig;
    }

    ArrayList<String> getContentList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < navig.size(); i++) {
            arrayList.add(navig.get(i).get(1) + "<str>" + navig.get(i).get(0));
        }
        return arrayList;
    }

    int setPage(String page) {
        int count = 1;
        for (int i = 0; i < navig.size(); i++) {
            if (navig.get(i).get(1).contains(page)) {
                count = Integer.parseInt(navig.get(i).get(2));
                break;
            }
        }
        return count - 1;
    }

    String getPageName(int page) {
        return navig.get(page).get(1);
    }

    String getTitleImage() {
        StringBuilder builder = new StringBuilder();
        try {
            File file = new File(patch + content_opf);
            FileReader inputStream = new FileReader(file);
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        String spineSrc = builder.toString();
        int t1 = spineSrc.indexOf("id=\"cover\"");
        int t2 = spineSrc.indexOf("href=\"", t1 + 10);
        int t3 = spineSrc.indexOf("\"", t2 + 6);
        builder = new StringBuilder();
        try {
            File file = new File(patch + spineSrc.substring(t2 + 6, t3));
            FileReader inputStream = new FileReader(file);
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inputStream.close();
        } catch (Throwable ignored) {
        }
        spineSrc = builder.toString();
        int t4 = spineSrc.indexOf("<img");
        int t5 = spineSrc.indexOf("src=\"", t4 + 4);
        int t6 = spineSrc.indexOf("\"", t5 + 5);
        String res = spineSrc.substring(t5 + 5, t6);
        try {
            res = new File(res).getCanonicalPath();
        } catch (IOException ignored) {
        }
        int t7 = res.indexOf("/");
        res = res.substring(t7 + 1);
        return patch + res;
    }
}
