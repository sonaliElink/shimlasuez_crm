package elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.SelectDocument;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import elink.suezShimla.water.crm.BuildConfig;
import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.SelectDocument.adapter.SelectDocumentAdapter;
import elink.suezShimla.water.crm.ConnectionRequest.UploadDocuments.model.SelectedDocumentModel;
import elink.suezShimla.water.crm.R;

public class SelectDocumentActivity extends AppCompatActivity {

    private Context mCon;

    private AppCompatSpinner documentNameSpinner;

    private RecyclerView docRecyclerView;

    private LinearLayout linear_empty_view;

    private AppCompatImageView im_empty;

    private TextView tv_empty;

    private MaterialButton uploadButton;

    private RelativeLayout cameraRelativeLayout, galleryRelativeLayout, pdfRelativeLayout;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 11;

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 12;

    private static final int REQUEST_CAMERA = 3, SELECT_FILE = 0, SELECT_PDF = 2;

    private File destination;

    private String encodedBase64Image, docName, docType, docTypeName;

    private SelectDocumentAdapter selectDocumentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_document);

        mCon = this;

        docRecyclerView = findViewById(R.id.docRecyclerView);

        documentNameSpinner = findViewById(R.id.documentNameSpinner);

        linear_empty_view = findViewById(R.id.linear_empty_view);
        im_empty = findViewById(R.id.im_empty);
        tv_empty = findViewById(R.id.tv_empty);

        uploadButton = findViewById(R.id.uploadButton);

        // cameraRelativeLayout = findViewById(R.id.cameraRelativeLayout);
        galleryRelativeLayout = findViewById(R.id.galleryRelativeLayout);
        pdfRelativeLayout = findViewById(R.id.pdfRelativeLayout);

        selectDocumentAdapter = new SelectDocumentAdapter(mCon);

        docRecyclerView.setHasFixedSize(true);
        docRecyclerView.setLayoutManager(new GridLayoutManager(mCon, 3));
        docRecyclerView.setNestedScrollingEnabled(false);
        docRecyclerView.setAdapter(selectDocumentAdapter);

       /* cameraRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                } else {
                    docTypeName = documentNameSpinner.getSelectedItem().toString().trim();
                    if (docTypeName.equalsIgnoreCase("--Select Document--")) {
                        TextView view = (TextView) documentNameSpinner.getSelectedView();
                        view.setError(getResources().getString(R.string.select_options));
                        Toast.makeText(mCon, "Select document from above..", Toast.LENGTH_SHORT).show();
                    } else {
                        TextView view = (TextView) documentNameSpinner.getSelectedView();
                        view.setError(null);
                        cameraIntent();
                    }
                }
            }
        });*/
        galleryRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                } else {
                    selectTypeImage();
                }
            }
        });

       /* galleryRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                } else {
                    docTypeName = documentNameSpinner.getSelectedItem().toString().trim();
                    if (docTypeName.equalsIgnoreCase("--Select Document--")) {
                        TextView view = (TextView) documentNameSpinner.getSelectedView();
                        view.setError(getResources().getString(R.string.select_options));
                        Toast.makeText(mCon, "Select document from above..", Toast.LENGTH_SHORT).show();
                    } else {
                        TextView view = (TextView) documentNameSpinner.getSelectedView();
                        view.setError(null);
                        galleryIntent();
                    }
                }
            }
        });*/
        pdfRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                } else if (ContextCompat.checkSelfPermission(mCon,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(SelectDocumentActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                } else {
                    docTypeName = documentNameSpinner.getSelectedItem().toString().trim();
                    if (docTypeName.equalsIgnoreCase("--Select Document--")) {
                        TextView view = (TextView) documentNameSpinner.getSelectedView();
                        view.setError(getResources().getString(R.string.select_options));
                        Toast.makeText(mCon, "Select document from above..", Toast.LENGTH_SHORT).show();
                    } else {
                        TextView view = (TextView) documentNameSpinner.getSelectedView();
                        view.setError(null);
                        pdfIntent();
                    }
                }
            }
        });

    }

    private void selectTypeImage() {
        final MaterialDialog dialog = new MaterialDialog.Builder(mCon)
                .customView(R.layout.image_layout, true)
                .title("Add Photo")
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .negativeText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();


        View customView = dialog.getCustomView();
        if (customView != null) {

            RelativeLayout takePhoto = (RelativeLayout) customView.findViewById
                    (R.id.takephotoRelativeLayout);
            RelativeLayout selectFromGallery = (RelativeLayout) customView.findViewById
                    (R.id.selectPhotoRelativeLayout);

            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cameraIntent();
                    dialog.dismiss();
                }
            });


            selectFromGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    galleryIntent();
                    dialog.dismiss();
                }
            });

        }
        dialog.show();
    }

    private void pdfIntent() {
        Intent pdfIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pdfIntent.setType("application/pdf");
        startActivityForResult(pdfIntent, SELECT_PDF);
    }

    private void galleryIntent() {
        String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File insideFolder = new File(dir, getResources().getString(R.string.documents));
        if (!insideFolder.exists()) {
            insideFolder.mkdirs();
        }
        destination = new File(insideFolder, System.currentTimeMillis() + ".jpg");
        Uri photoURI = FileProvider.getUriForFile(mCon,
                BuildConfig.APPLICATION_ID + ".provider",
                destination);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (reqCode == SELECT_FILE) {

                Cursor returnCursor = null;
                try {
                    final Uri imageUri = data.getData();
                    if (imageUri != null) {
                        ContentResolver cr = mCon.getContentResolver();
                        String mime = cr.getType(imageUri);

                        returnCursor = mCon.getContentResolver().query(imageUri, null, null, null, null);
                        if (returnCursor != null) {

                            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                            returnCursor.moveToFirst();

                            if (mime != null && mime.equals("image/jpeg") || mime != null && mime.equals("image/jpg") || mime != null && mime.equals("image/png")) {

                                if (returnCursor.getLong(sizeIndex) > 200000) {
                                    Snackbar.make(uploadButton, R.string.doc_size, Snackbar.LENGTH_INDEFINITE)
                                            .setAction(R.string.ok, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            }).show();
                                } else {

                                    final InputStream imageStream = mCon.getContentResolver().openInputStream(imageUri);
                                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                                    File dir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));

                                    if (!dir.exists()) {
                                        dir.mkdirs();
                                    }

                                    File insideFolder = new File(dir, getResources().getString(R.string.documents));

                                    if (!insideFolder.exists()) {
                                        insideFolder.mkdirs();
                                    }

                                    File destination = new File(insideFolder, System.currentTimeMillis() + ".jpg");

                                    String strPath = String.valueOf(destination);


                                    // Log.d("length", "length = " + returnCursor.getLong(sizeIndex));

                                    FileOutputStream fo;
                                    try {
                                        destination.createNewFile();
                                        fo = new FileOutputStream(destination);
                                        fo.write(bytes.toByteArray());
                                        fo.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    if (!strPath.equalsIgnoreCase("")) {

                                        if (bytes.size() < 5000000) {
                                            byte[] imageBytes = bytes.toByteArray();
                                            encodedBase64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                                            docName = strPath.substring(strPath.lastIndexOf('/') + 1);
                                            docType = "image";
                                            selectDocumentAdapter.addItem(new SelectedDocumentModel(docTypeName, selectedImage, docType));
                                           /* prescriptionAdapter.addItem(new AddPrescriptionModel(docName, encodedBase64Image, selectedImage));
                                            prescriptionRecyclerView.setAdapter(prescriptionAdapter);*/
                                            docRecyclerView.setVisibility(View.VISIBLE);
                                            linear_empty_view.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            } else {
                                //Toast.makeText(SelectDocument.this, "Document Type Should be jpeg , jpg , png .", Toast.LENGTH_LONG).show();
                                Snackbar.make(uploadButton, R.string.imageType, Snackbar.LENGTH_INDEFINITE)
                                        .setAction(R.string.ok, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        }).show();
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //   Toast.makeText(mCon, R.string.someThingWentWrong, Toast.LENGTH_SHORT).show();
                } finally {
                    if (returnCursor != null) {
                        returnCursor.close();
                    }
                }
            } else if (reqCode == REQUEST_CAMERA) {
                try {
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap thumbnail = BitmapFactory.decodeFile(destination.getAbsolutePath(), bmOptions);

                    int h = thumbnail.getHeight();
                    int w = thumbnail.getWidth();

                    //depends on compression rate/file size/pixels (dynamic)
                    //like inSampleSize in ur code
                    h = h / 4;
                    w = w / 4;

                    thumbnail = Bitmap.createScaledBitmap(thumbnail, w, h, true);


                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

                    if (!destination.exists()) {
                        destination.createNewFile();
                    }

                    String strPath1 = String.valueOf(destination);

                    FileOutputStream fo;
                    try {
                        //  destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (!strPath1.equals("")) {
                        /*docTextView.setText(R.string.no_file_chosen);
                        imageView.setVisibility(View.GONE);*/
                        encodedBase64Image = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
                        docName = strPath1.substring(strPath1.lastIndexOf('/') + 1);
                        docType = "image";
                        selectDocumentAdapter.addItem(new SelectedDocumentModel(docTypeName, thumbnail, docType));
                        docRecyclerView.setAdapter(selectDocumentAdapter);
                       /* prescriptionAdapter.addItem(new AddPrescriptionModel(docName, encodedBase64Image, thumbnail));
                        prescriptionRecyclerView.setAdapter(prescriptionAdapter);*/

                        docRecyclerView.setVisibility(View.VISIBLE);
                        linear_empty_view.setVisibility(View.GONE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (reqCode == SELECT_PDF) {

                Uri uri = data.getData();
                if (uri != null) {
                    ContentResolver cr = mCon.getContentResolver();
                    String mime = cr.getType(uri);

                    if (mime != null && !mime.equals("") && mime.equals("application/pdf")) {

                        String uriString = uri.toString();
                        File myFile = new File(uriString);
                        String displayName = null;
                        Cursor cursor = null;
                        int sizeIndex = 0;

                        try {

                            if (uriString.startsWith("content://")) {

                                cursor = mCon.getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);

                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }


                            } else if (uriString.startsWith("file://")) {
                                cursor = mCon.getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);

                                    displayName = myFile.getName();
                                }
                            }

                            if (cursor != null) {
                                if (cursor.getLong(sizeIndex) < 200000) {

                                    // String docFilePath = getFileNameByUri(this, uri);

                                    File dir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));

                                    if (!dir.exists()) {
                                        dir.mkdirs();
                                    }

                                    File insideFolder = new File(dir, "Documents");

                                    if (!insideFolder.exists()) {
                                        insideFolder.mkdirs();
                                    }

                                    File destination = new File(insideFolder, System.currentTimeMillis() + ".pdf");

                                    String path = String.valueOf(destination);

                                    FileOutputStream fos = null;
                                    try {
                                        fos = new FileOutputStream(destination);
                                        try (BufferedOutputStream out = new BufferedOutputStream(fos);
                                             InputStream in = mCon.getContentResolver().openInputStream(uri)) {
                                            if (in != null) {
                                                byte[] buffer = new byte[8192];
                                                int len = 0;
                                                while ((len = in.read(buffer)) >= 0) {
                                                    out.write(buffer, 0, len);
                                                }
                                                out.flush();
                                            }
                                        } finally {
                                            fos.getFD().sync();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (Integer.parseInt(String.valueOf(destination.length() / 1024)) > 1024) {
                                        InputStream imageStream = null;

                                        try {
                                            imageStream = mCon.getContentResolver().openInputStream(uri);

                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    try {
                                        FileInputStream fileInputStreamReader = new FileInputStream(destination.getAbsoluteFile());
                                        byte[] bytes = new byte[(int) destination.length()];
                                        fileInputStreamReader.read(bytes);

                                        encodedBase64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                                        docName = path.substring(path.lastIndexOf('/') + 1);
                                        docType = "pdf";
                                        selectDocumentAdapter.addItem(new SelectedDocumentModel(docTypeName, null, docType));
                                        // docTextView.setText(docName);
                                        docRecyclerView.setAdapter(selectDocumentAdapter);

                                        docRecyclerView.setVisibility(View.VISIBLE);
                                        linear_empty_view.setVisibility(View.GONE);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    Toast.makeText(mCon, "Document Size Should be below 200kb .", Toast.LENGTH_SHORT).show();
                                    // IdentityProofFileTextView.setText(R.string.strNoFileChosen);
                                }
                            }
                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                        //uploadFile(path);
                    } else {
                        Snackbar.make(uploadButton, R.string.imageType, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                    }
                }
            }
        }
    }


}
