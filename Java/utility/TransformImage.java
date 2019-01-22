package info.itlance.photofilter.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.SeekBar;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;

public class TransformImage {
    public static final int MAX_BRIGHTNESS= 100;
    public static final int MAX_VIGNETTE= 255;
    public static final int MAX_SATURATION= 100;
    public static final int MAX_CONTRAST= 5;

    public static final int DEFAULT_BRIGHTNESS = 70;
    public static final int DEFAULT_VIGNETTE = 100;
    public static final int DEFAULT_SATURATION = 5;
    public static final int DEFAULT_CONTRAST= 60;

    private  String mfilename;
    private Bitmap mBitmap;
    private Context mContext;

    private Bitmap brightnessFilteredBitmap;
    private Bitmap vignetteFilteredBitmap;
    private Bitmap saturationFilteredBitmap;
    private Bitmap contrastFilteredBitmap;

    public  static int FILTER_BRIGHTNESS=0;
    public  static int FILTER_VIGNETTE=1;
    public  static int FILTER_SATURATION=2;
    public  static int FILTER_CONTRAST=3;

    SeekBar mSeekbar;

    public String getFilename(int filter){
        if(filter== FILTER_BRIGHTNESS){
            return mfilename+"brightness.png";
        }
        else if(filter== FILTER_CONTRAST){
            return mfilename+"contrast.png";
        }
        else if(filter== FILTER_SATURATION){
            return mfilename+"saturation.png";
        }
        else if (filter == FILTER_VIGNETTE){
            return mfilename + "vignette.png";
        }
        return  mfilename;
    }

    public Bitmap getmBitmap(int filter){
        if(filter== FILTER_BRIGHTNESS){
            return brightnessFilteredBitmap;
        }
        else if(filter== FILTER_CONTRAST){
            return contrastFilteredBitmap;
        }
        else if(filter== FILTER_SATURATION){
            return saturationFilteredBitmap;
        }
        else if (filter == FILTER_VIGNETTE){
            return vignetteFilteredBitmap;
        }
        return  mBitmap;
    }
    public TransformImage(Context context,Bitmap bitmap){
        mContext=context;
        mBitmap=bitmap;
        mfilename=System.currentTimeMillis()+"";
    }
    public Bitmap applyBrightnessSubFilter(int brightness){
        Filter myFilter = new Filter();

        Bitmap workingBitmap =Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);


        myFilter.addSubFilter(new BrightnessSubfilter(brightness));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        brightnessFilteredBitmap = outputImage;
        return outputImage;

    }
    public Bitmap applyVignetteSubFilter(int vignette){
        Filter myFilter = new Filter();

        Bitmap workingBitmap =Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);


        myFilter.addSubFilter(new VignetteSubfilter(mContext,vignette));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        vignetteFilteredBitmap = outputImage;
        return outputImage;

    }
    public Bitmap applyContrastSubFilter(int contrast){
        Filter myFilter = new Filter();

        Bitmap workingBitmap =Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);


        myFilter.addSubFilter(new ContrastSubfilter(contrast));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        contrastFilteredBitmap = outputImage;
        return outputImage;

    }
    public Bitmap applySaturationSubFilter(int saturation){
        Filter myFilter = new Filter();

        Bitmap workingBitmap =Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);


        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        saturationFilteredBitmap = outputImage;
        return outputImage;


    }
}
