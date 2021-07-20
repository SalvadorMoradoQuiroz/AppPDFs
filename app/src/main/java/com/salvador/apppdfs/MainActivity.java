package com.salvador.apppdfs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import harmony.java.awt.Color;

import static com.salvador.apppdfs.NombresDirectorios.ETIQUETA_ERROR;
import static com.salvador.apppdfs.NombresDirectorios.NOMBRE_DIRECTORIO;
import static com.salvador.apppdfs.NombresDirectorios.NOMBRE_DOCUMENTO;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Alumno> arrayListAlumnos;
    private Data data = new Data();
    private Button button_CrearPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_CrearPDF = findViewById(R.id.button_CrearPDF);

        arrayListAlumnos = data.getData();

        button_CrearPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearPDFAndroidQPlus();
            }
        });
    }

    private void crearPDFAndroidQPlus() {
        File directory;
        File file;
        directory = new File(getExternalFilesDir(null) + "/" + NOMBRE_DIRECTORIO.texto);
        if (!directory.exists()) {
            directory.mkdir();
        }

        if (arrayListAlumnos != null) {

            try {
                file = new File(directory, "/" + NOMBRE_DOCUMENTO.texto + "_" + DateHelper.obtenerFecha() + ".pdf");
                Document documento = new Document(PageSize.LETTER.rotate());
                OutputStream os = getContentResolver().openOutputStream(Uri.fromFile(file));
                dibujarPDF(documento, (FileOutputStream) os, "ING. EN SISTEMAS COMPUTACIONALES");
                Toast.makeText(MainActivity.this, "Se creo tu archivo pdf " + Uri.fromFile(file).getPath(), Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No hay datos para generar el pdf.", Toast.LENGTH_SHORT).show();
        }
    }


    private void dibujarPDF(Document documento, FileOutputStream ficheroPdf, String carrera) {
        try {
            /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_cb);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());

            writer.setPageEvent(new WaterMark(imagen));*/

            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            Font fontHeaderFooter = FontFactory.getFont(FontFactory.TIMES_ROMAN, FontFactory.defaultEncoding, FontFactory.defaultEmbedding, 12, Font.BOLD, Color.BLACK);
            Paragraph paragraphHeader = new Paragraph("TECNOLÓGICO NACIONAL DE MÉXICO\n" +
                    "INSTITUTO TECNOLÓGICO SUPERIOR DE URUAPAN\n\n" +
                    "REGISTRO DE ASISTENCIA NUEVO INGRESO\n" + carrera + "\n", fontHeaderFooter);

            Phrase phraseFooter = new Phrase("Educación para  tranformar la vida, TecNM campus Uruapan.\n", fontHeaderFooter);

            HeaderFooter cabecera = new HeaderFooter(new Phrase(Objects.requireNonNull(paragraphHeader)), false);
            cabecera.setAlignment(Element.ALIGN_CENTER);

            HeaderFooter pie = new HeaderFooter(new Phrase(phraseFooter), false);
            pie.setAlignment(Element.ALIGN_CENTER);

            documento.setHeader(cabecera);
            documento.setFooter(pie);

            Phrase numControl = new Phrase("NO. CONTROL", fontHeaderFooter);
            Phrase nombreEstudiante = new Phrase("NOMBRE COMPLETO", fontHeaderFooter);
            Phrase carreraAux = new Phrase("CARRERA", fontHeaderFooter);
            Phrase telefono = new Phrase("TELÉFONO", fontHeaderFooter);
            Phrase status = new Phrase("STATUS", fontHeaderFooter);

            PdfPCell cellNumControl = new PdfPCell(numControl);
            cellNumControl.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellNumControl.setBackgroundColor(Color.LIGHT_GRAY);

            PdfPCell cellNombreEstudiante = new PdfPCell(nombreEstudiante);
            cellNombreEstudiante.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellNombreEstudiante.setBackgroundColor(Color.LIGHT_GRAY);

            PdfPCell cellCarrera = new PdfPCell(carreraAux);
            cellCarrera.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCarrera.setBackgroundColor(Color.LIGHT_GRAY);

            PdfPCell cellTelefono = new PdfPCell(telefono);
            cellTelefono.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTelefono.setBackgroundColor(Color.LIGHT_GRAY);

            PdfPCell cellStatus = new PdfPCell(status);
            cellStatus.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellStatus.setBackgroundColor(Color.LIGHT_GRAY);

            documento.open();

            PdfPTable TABLA = new PdfPTable(5);
            TABLA.setWidthPercentage(100);
            TABLA.setWidths(new float[]{(float) 12.5, (float) 30.0, (float) 32.5, (float) 15.0, (float) 10.0});
            TABLA.addCell(cellNumControl);
            TABLA.addCell(cellNombreEstudiante);
            TABLA.addCell(cellCarrera);
            TABLA.addCell(cellTelefono);
            TABLA.addCell(cellStatus);
            TABLA.setHeaderRows(1);

            for (int i = 0; i < arrayListAlumnos.size(); i++) {
                if (carrera.equals(arrayListAlumnos.get(i).carrera)) {
                    TABLA.addCell(arrayListAlumnos.get(i).getId());
                    TABLA.addCell(arrayListAlumnos.get(i).getNombre());
                    TABLA.addCell(arrayListAlumnos.get(i).getCarrera());
                    TABLA.addCell(arrayListAlumnos.get(i).getTelefono());
                    TABLA.addCell(arrayListAlumnos.get(i).getStatus());
                }
            }
            TABLA.setHorizontalAlignment(Element.ALIGN_CENTER);
            documento.add(TABLA);

        } catch (DocumentException e) {
            Log.e(ETIQUETA_ERROR.texto, Objects.requireNonNull(e.getMessage()));
        } finally {
            // Cerramos el documento.
            documento.close();
        }
    }
}