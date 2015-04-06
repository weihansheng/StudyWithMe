package example.swm.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**  
* @Description  图片处理类 避免oom
* @author MR.Wang 
* @date 2014-8-16 下午10:47:35 
* @version V1.0  
*/ 

public class BitMapUtil {

	public static int computeSampleSize(BitmapFactory.Options options, 
            int minSideLength, int maxNumOfPixels) { 
        int initialSize = computeInitialSampleSize(options, minSideLength, 
                maxNumOfPixels); 
      
        int roundedSize; 
        if (initialSize <= 8) { 
            roundedSize = 1; 
            while (roundedSize < initialSize) { 
                roundedSize <<= 1; 
            } 
        } else { 
            roundedSize = (initialSize + 7) / 8 * 8; 
        } 
      
        return roundedSize; 
    } 
    private static int computeInitialSampleSize(BitmapFactory.Options options, 
            int minSideLength, int maxNumOfPixels) { 
        double w = options.outWidth; 
        double h = options.outHeight; 
      
        int lowerBound = (maxNumOfPixels == -1) ? 1 : 
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels)); 
        int upperBound = (minSideLength == -1) ? 128 : 
                (int) Math.min(Math.floor(w / minSideLength), 
                Math.floor(h / minSideLength)); 
      
        if (upperBound < lowerBound) { 
            // return the larger one when there is no overlapping zone. 
            return lowerBound; 
        } 
      
        if ((maxNumOfPixels == -1) && 
                (minSideLength == -1)) { 
            return 1; 
        } else if (minSideLength == -1) { 
            return lowerBound; 
        } else { 
            return upperBound; 
        } 
    }  
    
	public static Bitmap decodeBitmap(String picPath,int width,int height)
	{
	    
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 通过这个bitmap获取图片的宽和高
		BitmapFactory.decodeFile(picPath,options);
		options.inJustDecodeBounds = false;
		options.inSampleSize = computeSampleSize(options, -1, width*height);
		// 这正的bitmap
		Bitmap bitmap =null;
		try {
			bitmap = BitmapFactory.decodeFile(picPath, options);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return bitmap;
	}
	
    public static Bitmap PicZoom(Bitmap bmp, int width, int height) {
        int bmpWidth = bmp.getWidth();
        int bmpHeght = bmp.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);

        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
    }
}
