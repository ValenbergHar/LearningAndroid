package by.carkva_gazeta.malitounik;

import java.io.File;

class my_natatki_files {

    final long lastModified;
    final String name;
    final File file;

    my_natatki_files(long lastModified, String name, File file) {
        this.lastModified = lastModified;
        this.name = name;
        this.file = file;
    }
}
