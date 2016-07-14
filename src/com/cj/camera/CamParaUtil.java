package com.cj.camera;

import java.util.Collections;  
import java.util.Comparator;  
import java.util.List;  
  
import android.hardware.Camera;  
import android.hardware.Camera.Size;  
import android.util.Log;  
  
public class CamParaUtil {  
    private static final String TAG = "yanzi";  
    private CameraSizeComparator sizeComparator = new CameraSizeComparator();  
    private static CamParaUtil myCamPara = null;  
    private CamParaUtil(){
  
    }  
    public static CamParaUtil getInstance(){  
        if(myCamPara == null){  
            myCamPara = new CamParaUtil();  
            return myCamPara;  
        }  
        else{  
            return myCamPara;  
        }  
    }  
    
    public  Size getPropPreviewSize(List<Camera.Size> list, float th, int minWidth){  
        Collections.sort(list, sizeComparator);  
  
        int targetPosition = 0;
        float tarRate=10;
        
        for(int i=0;i<list.size();i++){
        	Size s=list.get(i);
        	if((s.width >= minWidth)){  
        		 float r = (float)(s.width)/(float)(s.height);
        		 if(r<=tarRate){
        			 tarRate=r;
        			 targetPosition=i;
        		 }
            }  
        }
        Size size=list.get(targetPosition);
        return size;
    }  
    public Size getMaxPictureSize(List<Camera.Size> list){
    	Collections.sort(list, sizeComparator);
    	return list.get(list.size()-1);  
    } 
  
    public boolean equalRate(Size s, float rate){  
        float r = (float)(s.width)/(float)(s.height);  
        if(Math.abs(r - rate) <= 0.03)  
        {  
            return true;  
        }  
        else{  
            return false;  
        }  
    }  
  
    public  class CameraSizeComparator implements Comparator<Camera.Size>{  
        public int compare(Size lhs, Size rhs) {  
            // TODO Auto-generated method stub  
            if(lhs.width == rhs.width){ 
            	if(lhs.height>rhs.height)
            		return 1;
            	else if(lhs.height< rhs.height)
            		return -1;
            	else 
            		return 0;
            }  
            else if(lhs.width > rhs.width){  
                return 1;  
            }  
            else{  
                return -1;
            }  
        }  
  
    }  
  
    /**打印支持的previewSizes 
     * @param params 
     */  
    public  void printSupportPreviewSize(Camera.Parameters params){  
        List<Size> previewSizes = params.getSupportedPreviewSizes();  
        for(int i=0; i< previewSizes.size(); i++){  
            Size size = previewSizes.get(i);  
            Log.i(TAG, "previewSizes:width = "+size.width+" height = "+size.height);  
        }  
      
    }  
  
    /**打印支持的pictureSizes 
     * @param params 
     */  
    public  void printSupportPictureSize(Camera.Parameters params){  
        List<Size> pictureSizes = params.getSupportedPictureSizes();  
        for(int i=0; i< pictureSizes.size(); i++){  
            Size size = pictureSizes.get(i);  
            Log.i(TAG, "pictureSizes:width = "+ size.width  
                    +" height = " + size.height);  
        }  
    }  
    /**打印支持的聚焦模式 
     * @param params 
     */  
    public void printSupportFocusMode(Camera.Parameters params){  
        List<String> focusModes = params.getSupportedFocusModes();  
        for(String mode : focusModes){  
            Log.i(TAG, "focusModes--" + mode);  
        }  
    }  
}  