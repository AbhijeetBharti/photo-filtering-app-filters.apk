package info.itlance.photofilter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;

import info.itlance.photofilter.utility.TransformImage;
import  info.itlance.photofilter.utility.helper;
import com.squareup.picasso.Target;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;



public class ControlActivity extends AppCompatActivity {
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }
    Toolbar mControlToolbar;
    ImageView mTickImageView;
    ImageView mCenterImageView;
    TransformImage mtransformImage;
    int mScreenWidth;
    int mScreenHeight;
    SeekBar mSeekbar;
    Uri mSelectedImageuri;
    int mCurrenttFilter;
    ImageView cancelImageView;
   public int a;
    Target mApplySingleFilter = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int currentFilterValue = mSeekbar.getProgress();
           if (mCurrenttFilter==TransformImage.FILTER_BRIGHTNESS){
               mtransformImage.applyBrightnessSubFilter(currentFilterValue);
               helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS),mtransformImage.getmBitmap(TransformImage.FILTER_BRIGHTNESS));
               Picasso.get().invalidate(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS)));
               Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mScreenHeight/2).into(mCenterImageView);


           }else if (mCurrenttFilter==TransformImage.FILTER_CONTRAST){
               mtransformImage.applyContrastSubFilter(currentFilterValue);
               helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST),mtransformImage.getmBitmap(TransformImage.FILTER_CONTRAST));
               Picasso.get().invalidate(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST)));
               Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0,mScreenHeight/2).into(mCenterImageView);
           }else if (mCurrenttFilter==TransformImage.FILTER_VIGNETTE){
               mtransformImage.applyVignetteSubFilter(currentFilterValue);
               helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE),mtransformImage.getmBitmap(TransformImage.FILTER_VIGNETTE));
               Picasso.get().invalidate(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE)));
               Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0,mScreenHeight/2).into(mCenterImageView);

           }else if (mCurrenttFilter==TransformImage.FILTER_SATURATION){
               mtransformImage.applySaturationSubFilter(currentFilterValue);
               helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION),mtransformImage.getmBitmap(TransformImage.FILTER_SATURATION));
               Picasso.get().invalidate(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION)));
               Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0,mScreenHeight/2).into(mCenterImageView);

           }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    Target mSmallTarget= new Target(){
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mtransformImage = new TransformImage(ControlActivity.this,bitmap);
            mtransformImage.applyBrightnessSubFilter(TransformImage.DEFAULT_BRIGHTNESS);

            helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS),mtransformImage.getmBitmap(TransformImage.FILTER_BRIGHTNESS));
            Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).fit().centerInside().into(mFirstFilterPreviewImageView);
            //
            mtransformImage.applySaturationSubFilter(TransformImage.DEFAULT_SATURATION);
            helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION),mtransformImage.getmBitmap(TransformImage.FILTER_SATURATION));
            Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION))).fit().centerInside().into(mSecondFilterPreviewImageView);
            //
            mtransformImage.applyVignetteSubFilter(TransformImage.DEFAULT_VIGNETTE);
            helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE),mtransformImage.getmBitmap(TransformImage.FILTER_VIGNETTE));
            Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).fit().centerInside().into(mThirdFilterPreviewImageView);
            //
            mtransformImage.applyContrastSubFilter(TransformImage.DEFAULT_CONTRAST);
            helper.writeDataIntoExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST),mtransformImage.getmBitmap(TransformImage.FILTER_CONTRAST));
            Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST))).fit().centerInside().into(mFourthFilterPreviewImageView);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    final static int PICK_IMAGE = 2;
    final static int MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION = 3;
    ImageView mFirstFilterPreviewImageView;
    ImageView mSecondFilterPreviewImageView;
    ImageView mThirdFilterPreviewImageView;
    ImageView mFourthFilterPreviewImageView;
    public static  final String TAG =ControlActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        mSeekbar=(SeekBar)findViewById(R.id.seekBar);
        mFirstFilterPreviewImageView = (ImageView)findViewById(R.id.imageView18);
        mSecondFilterPreviewImageView = (ImageView)findViewById(R.id.imageView20);
        mThirdFilterPreviewImageView = (ImageView)findViewById(R.id.imageView21);
        mFourthFilterPreviewImageView = (ImageView)findViewById(R.id.imageView22);
        mControlToolbar = (Toolbar) findViewById(R.id.toolbar);
        mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setNavigationIcon(R.mipmap.icon);
        mTickImageView = (ImageView) findViewById(R.id.imageView4);

        cancelImageView=(ImageView)findViewById(R.id.imageView3);

        cancelImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             AlertDialog.Builder builder =new AlertDialog.Builder(ControlActivity.this);
             builder.setMessage("Are you sure to exit ?").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     finish();
                     System.exit(0);
                 }
             }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.cancel();
                 }
             });

              AlertDialog alertDialog=builder.create();
              alertDialog.show();
            }
        });
        mCenterImageView = (ImageView) findViewById(R.id.centerimageView);
        mCenterImageView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                onRequestPermission();
                if(ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                a=1;

            }

        });
        mFirstFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mSeekbar.setMax(TransformImage.MAX_BRIGHTNESS);
                mSeekbar.setProgress(TransformImage.DEFAULT_BRIGHTNESS);
                mCurrenttFilter=TransformImage.FILTER_BRIGHTNESS;
                Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });
        mSecondFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekbar.setMax(TransformImage.MAX_SATURATION);
                mSeekbar.setProgress(TransformImage.DEFAULT_SATURATION);
                mCurrenttFilter=TransformImage.FILTER_SATURATION;
                Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });
        mThirdFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSeekbar.setMax(TransformImage.MAX_VIGNETTE);
                mSeekbar.setProgress(TransformImage.DEFAULT_VIGNETTE);
                mCurrenttFilter=TransformImage.FILTER_VIGNETTE;
                Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0,mScreenHeight/2).into(mCenterImageView);

            }
        });
        mFourthFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekbar.setMax(TransformImage.MAX_CONTRAST);
                mSeekbar.setProgress(TransformImage.DEFAULT_CONTRAST);
                mCurrenttFilter=TransformImage.FILTER_CONTRAST;
                Picasso.get().load(helper.getFileFromExternalStorage(ControlActivity.this,mtransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picasso.get().load(mSelectedImageuri).into(mApplySingleFilter);
                if( a==1) {
                    Toast.makeText(ControlActivity.this,
                            "Filtered image saved in gallery", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ControlActivity.this,
                            "Select an image", Toast.LENGTH_LONG).show();
                }
            }
        });
       /* mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       mScreenHeight= displayMetrics.heightPixels;
        mScreenWidth=displayMetrics.widthPixels;
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        ControlActivity.super.onBackPressed();
                    }
                }).create().show();
    }



    public  void onRequestPermissionResult(int requestCode, String permissions[],int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    new MaterialDialog.Builder(ControlActivity.this).title("Permission Granted")
                            .content(R.string.permission_granted)
                            .positiveText("OK").canceledOnTouchOutside(true).show();
                }
                else{
                    Log.d(TAG,"Permission denied !");

                }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            mSelectedImageuri = data.getData();
            mCenterImageView.setImageURI(mSelectedImageuri);
            Picasso.get().load(mSelectedImageuri).fit().centerInside().into(mCenterImageView);
            Picasso.get().load(mSelectedImageuri).into(mSmallTarget);
        }
    }
    public void  onRequestPermission(){
        if(ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new MaterialDialog.Builder(ControlActivity.this).title("Permission Required")
                        .content(R.string.permission_content)
                        .negativeText("No")
                        .positiveText("Go to settings")
                        .canceledOnTouchOutside(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivityForResult(new Intent(Settings.ACTION_APPLICATION_SETTINGS),0);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(ControlActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION);
            }
            return;
        }
    }
}
