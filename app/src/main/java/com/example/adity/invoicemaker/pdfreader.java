package com.example.adity.invoicemaker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.joanzapata.pdfview.PDFView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Simrandeep Singh
 *
 */
public class pdfreader extends AppCompatActivity {
   private PDFView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreader);
        imageView = (PDFView) findViewById(R.id.pdfview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Invoice Preview");

        try {
            openPDF();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Something Wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * method to Preview the pdf which is generated
     *
     */



    private void openPDF() throws IOException {

        if(getIntent().getStringExtra("from").equals("genrate")) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + getIntent().getStringExtra("inv") + "temp.pdf");
            imageView.fromFile(file);
            imageView.fromFile(file)
                    .defaultPage(1)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .swipeVertical(true)
                    .load();
        }
        else {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + getIntent().getStringExtra("inv") + ".pdf");
            if(file.exists()) {
                imageView.fromFile(file);
                imageView.fromFile(file)
                        .defaultPage(1)
                        .showMinimap(false)
                        .enableSwipe(true)
                        .swipeVertical(true)
                        .load();
            }
            else
            {
                Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * this method will delete the file after previewing it
     */


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra("from").equals("generate")) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + getIntent().getStringExtra("inv") + "temp.pdf");
            file.delete();
        }
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        onBackPressed();
        if(getIntent().getStringExtra("from").equals("generate")) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + getIntent().getStringExtra("inv") + "temp.pdf");
            file.delete();
        }
        return null;

    }
}
