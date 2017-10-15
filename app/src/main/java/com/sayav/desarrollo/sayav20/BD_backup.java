package com.sayav.desarrollo.sayav20;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luna on 19/06/17.
 */

public class BD_backup {

    public static final String DATABASE_NAME = "BDSayavMovil.db";

    public BD_backup() {

    }

    public void bd_backup() throws IOException {

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        final String inFileName = "/data/data/com.example.SayavMovil/databases/" + DATABASE_NAME;
        File dbFile = new File(inFileName);
        FileInputStream fis = null;

        fis = new FileInputStream(dbFile);

        String directorio = "/home/luna/workspace/Sayav/BDmovil";
        File d = new File(directorio);
        if (!d.exists()) {
            d.mkdir();
        }
        String outFileName = directorio + "/" +DATABASE_NAME + " "+timeStamp;

        OutputStream output;
        output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        fis.close();

    }


}
