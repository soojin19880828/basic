package thFace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whminwei.constant.Constant;
import com.whminwei.util.PropertyFactory;

public class THface {
    private static final Logger logger = LoggerFactory.getLogger(THface.class);

    public native int THFaceInit(String config);

    public native float compareFaces(String picleft, String picRight);

    public native int antispoofing(String pic);

    public static void main(String[] args) {
       System.out.println(System.getProperty("java.library.path"));
        System.load("D:\\ide_workspace\\eclipse\\print_project\\business_project\\RealEstateCertificatePrinterClient_qzs_djzm\\thface\\tensorflow.dll");
        System.out.println("load tensorflow.dll success");
        System.load("D:\\ide_workspace\\eclipse\\print_project\\business_project\\RealEstateCertificatePrinterClient_qzs_djzm\\thface\\opencv_world310.dll");
        System.out.println("load opencv_world310.dll success");
        try {
            System.load("D:\\ide_workspace\\eclipse\\print_project\\business_project\\RealEstateCertificatePrinterClient_qzs_djzm\\thface\\thface.dll");
            System.out.println("load thface.dll success");
        } catch (Throwable e) {
            System.out.println("load thface.dll fail");
            e.printStackTrace();
        }
        
        THface thface = new THface();
		thface.THFaceInit("D:\\ide_workspace\\eclipse\\print_project\\business_project\\RealEstateCertificatePrinterClient_qzs_djzm\\thface");
    	
    }

    public int antispoofingT(String pic) {
        logger.info("in antispoofingT");
        System.loadLibrary("thface");
        THface face = new THface();

//    	face.THFaceInit("D:/HTFace3.8/HTfaceTest/libs");
        face.THFaceInit(PropertyFactory.getPath() + "thface");

        logger.info(String.format("antispoofing result is: %d", face.antispoofing(pic)));

        //用的物理活体，这个张总提供的暂时不用（怕有影响）
//    	int percent = face.antispoofing(pic);

        return 1;

    }

}
