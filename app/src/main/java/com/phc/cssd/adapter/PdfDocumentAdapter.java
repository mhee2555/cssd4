package com.phc.cssd.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

public class PdfDocumentAdapter {

    Context context = null;
    String pathName = "";
    public PdfDocumentAdapter(Context ctxt, String pathName) {
        context = ctxt;
        this.pathName = pathName;
    }

    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes1, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
        if (cancellationSignal.isCanceled()) {
            layoutResultCallback.onLayoutCancelled();
        }
        else {
            PrintDocumentInfo.Builder builder=
                    new PrintDocumentInfo.Builder(" file name");
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                    .build();
            layoutResultCallback.onLayoutFinished(builder.build(),
                    !printAttributes1.equals(printAttributes));
        }
    }

    public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        InputStream in=null;
        OutputStream out=null;
        try {
            File file = new File(pathName);
            in = new FileInputStream(file);
            out=new FileOutputStream(parcelFileDescriptor.getFileDescriptor());

            byte[] buf=new byte[16384];
            int size;

            while ((size=in.read(buf)) >= 0
                    && !cancellationSignal.isCanceled()) {
                out.write(buf, 0, size);
            }

            if (cancellationSignal.isCanceled()) {
                writeResultCallback.onWriteCancelled();
            }
            else {
                writeResultCallback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
            }
        }
        catch (Exception e) {
            writeResultCallback.onWriteFailed(e.getMessage());
            Logger.getLogger( e+"" );
        }
        finally {
            try {
                in.close();
                out.close();
            }
            catch (IOException e) {
                Logger.getLogger( e+"" );
            }
        }
    }
}
