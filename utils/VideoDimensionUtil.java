package com.offlinew.practica.utils;


import android.media.MediaCodecInfo;
import android.media.MediaCodecList;

public class VideoDimensionUtil {

    public static long HQ_MAX = 2764800;//HQ_MAX = 1920 * 1440
    public static long HQ_MAX_SIDE = 1920;// 1920 max side limited to 1920
    public static long LQ_MAX = 230400; //480 * 480 // 172800;//LQ_MAX = 480 * 360

    /**
     * 4320p (8k): 7680x4320
     * 2160p (4K): 3840x2160
     * 1440p (QHD): 2560x1440
     * 0000p (2k): 2048 x 1080
     * 1080p (FHD): 1920x1080
     * 720p (HD): 1280x720
     * 480p (FW-VGA): 854x480
     * 360p (SD): 640x360
     * 240p (LD): 426x240
     */
    public static long Q_8K = 33377600;
    public static long Q_4K = 8294400;
    public static long Q_QHD = 3686400;
    public static long Q_2K = 2211840;
    public static long Q_FHD = 2073600;
    public static long Q_HD = 777600;
    public static long Q_FV = 409920;
    public static long Q_SD = 230400;
    public static long Q_LD = 102240;



    public static String get_video_res_str(long vid_w,long vid_h){
        long total_pixels = vid_w * vid_h;
        if(total_pixels>Q_FHD){
            return "HQ";
        }
        if(total_pixels>Q_HD){
            return "FHD";
        }
        if(total_pixels>Q_FV){
            return "HD";
        }
        if(total_pixels>Q_SD){
            return "FVG";
        }
        if(total_pixels>Q_LD){
            return "SD";
        }
        return "LQ";
    }


    private static long[] forceMaxSingleSide(long a, long b, long mxSide){
        long mx = Math.max(a,b);
        long mn = Math.min(a,b);

        if(mx>mxSide){
            double ratio = mxSide/(double)mx;
            mx = (long)(mx*ratio);
            mn = (long)(mn*ratio);
        }
        if((mx&1) == 1){
            mx--;
        }
        if((mn&1) == 1){
            mn--;
        }

        if(a>b) {
            return new long[]{mx,mn};
        }
        return new long[]{mn,mx};
    }

    public static long[] get_optimal_res(long orig_vid_w, long orig_vid_h, long maxPix, long maxOneSide){
        long[] opt_res = get_optimal_res( orig_vid_w, orig_vid_h, maxPix);
        return forceMaxSingleSide(opt_res[0],opt_res[1],maxOneSide);
    }
    public static long[] get_optimal_res(long orig_vid_w,long orig_vid_h,long maxPix){
        if((orig_vid_w*orig_vid_h) <= maxPix) {
            return new long[]{orig_vid_w,orig_vid_h};
        }

        long[] len_arr_dec = {3840, 2560, 2160, 2048,  1920, 1440, 1280, 1080, 960, 854, 720, 640, 480, 426, 360, 240, 160};//7680 4320 3840

        long v_l = Math.max(orig_vid_w,orig_vid_h);
        long v_s = Math.min(orig_vid_w,orig_vid_h);
        long l_side=0,s_side=0;

        for(long len_i:len_arr_dec) {
            l_side = len_i;
            s_side = (len_i * v_s) / v_l;

            if (l_side * s_side <= maxPix) {
                break;
            }
        }
        if((s_side&1)==1){
            s_side--;
        }
        if(orig_vid_w>orig_vid_h){
            return new long[]{l_side,s_side};
        }
        return new long[]{s_side,l_side};
    }


    public static boolean isVideoResolutionSupported(int width, int height) {
        MediaCodecList codecList = new MediaCodecList(MediaCodecList.ALL_CODECS);
        MediaCodecInfo[] codecInfos = codecList.getCodecInfos();

        for (MediaCodecInfo codecInfo : codecInfos) {
            if (!codecInfo.isEncoder()) {
                for (String type : codecInfo.getSupportedTypes()) {
                    if (type.contains("video")) {

                        MediaCodecInfo.CodecCapabilities capabilities = codecInfo.getCapabilitiesForType(type);
                        MediaCodecInfo.VideoCapabilities videoCapabilities = capabilities.getVideoCapabilities();

                        if (videoCapabilities != null && videoCapabilities.isSizeSupported(width, height)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
