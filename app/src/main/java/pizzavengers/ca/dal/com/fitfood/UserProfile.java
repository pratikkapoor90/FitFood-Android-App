package pizzavengers.ca.dal.com.fitfood;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class UserProfile extends AppCompatActivity {

    public static final String CAMERA_FOLDER = "camera_app";
    ImageButton btnCam,btnEdit;
    Button about_btn, faq_btn, logout_btn;
    ImageView imageView;
    static final int CAM_REQUEST = 1, SELECT_FILE = 0;
    String mImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        btnCam = (ImageButton) findViewById(R.id.clickProfilePicture);
        btnEdit = (ImageButton)findViewById(R.id.clickEditProfile);
        about_btn = (Button) findViewById(R.id.about_btn);
        faq_btn = (Button)findViewById(R.id.faq_btn);
        logout_btn = (Button) findViewById(R.id.logout_btn);
        bottomNavigation();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserProfile = new Intent(UserProfile.this,UserInputActivity.class);
                startActivity(intentUserProfile);
            }
        });
        //////////////////////////////////////////////////////////////////////

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserProfile = new Intent(UserProfile.this,About.class);
                startActivity(intentUserProfile);
            }
        });

        faq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserProfile = new Intent(UserProfile.this,Faq.class);
                startActivity(intentUserProfile);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserProfile = new Intent(UserProfile.this,Logout.class);
                startActivity(intentUserProfile);
            }
        });

        ///////////////////////////////////////////////////////////////////////
        imageView = (ImageView) findViewById(R.id.imageView);
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"camera", "gallery", "cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("camera")) {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = getFile();
                    camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(camera_intent, CAM_REQUEST);
                } else if (items[i].equals("gallery")) {
                    Intent gallery_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    gallery_intent.setType("image/*");
                    startActivityForResult(gallery_intent.createChooser(gallery_intent, "select file"), SELECT_FILE);

                } else if (items[i].equals("cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private File getFile() {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "camera_app");
        if (!folder.exists()) {
            folder.mkdir();
        }
        mImageName = String.valueOf(System.currentTimeMillis());
        File image_file = new File(folder, mImageName + ".jpg");
        return image_file;
    }


    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (resultcode == Activity.RESULT_OK) {
            if (requestcode == CAM_REQUEST) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                        CAMERA_FOLDER + File.separator + mImageName + ".jpg";
                imageView.setImageDrawable(Drawable.createFromPath(path));
                Bitmap myBitmap = BitmapFactory.decodeFile(new File(path).getAbsolutePath());
                imageView.setImageBitmap(getResizedBitmap(myBitmap, 300, 300));

            } else if (requestcode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);

            } else {
                Toast.makeText(this, "Photo cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void bottomNavigation() {
        BottomNavigationView btmNav = (BottomNavigationView) findViewById(R.id.btm_nav);
        /* btmNav.setOnNavigationItemSelectedListener(navListener);*/
        //BottomNavigationViewHelper.disableShiftMode(btmNav);

        Menu menu = btmNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.view1:
                        Intent intent1 = new Intent(UserProfile.this, CalculateStatsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.view2:
                        Intent intent2 = new Intent(UserProfile.this, RestaurantsData.class);
                        startActivity(intent2);
                        break;
                    case R.id.view3:

                        break;
                }
                return false;
            }
        });
    }
}