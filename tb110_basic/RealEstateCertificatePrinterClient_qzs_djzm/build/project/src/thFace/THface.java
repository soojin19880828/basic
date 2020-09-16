package thFace;

import com.thfdcsoft.estate.printer.util.PropertyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class THface {

    private static final Logger logger = LoggerFactory.getLogger(THface.class);

    public native int THFaceInit(String config);

    public native float compareFaces(String picleft, String picRight);

    public native int antispoofing(String pic);

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
//        System.loadLibrary("thface");
        System.load("D:\\workspace\\RealEstateCertificatePrinter\\RealEstateCertificatePrinterClient\\thface\\tensorflow.dll");
        //System.load("face");
        System.out.println("load tensorflow.dll success");

        System.load("D:\\workspace\\RealEstateCertificatePrinter\\RealEstateCertificatePrinterClient\\thface\\opencv_world310.dll");
        System.out.println("load opencv_world310.dll success");
        try {
            System.load("D:\\workspace\\RealEstateCertificatePrinter\\RealEstateCertificatePrinterClient\\thface\\thface.dll");
            System.out.println("load thface.dll success");
        } catch (Throwable e) {
            System.out.println("load thface.dll fail");
            e.printStackTrace();
        }
//        THface face = new THface();
//
//        String pic1 = "C:\\Users\\Rejects\\Pictures\\1.jpg";
//        String pic2 = "C:\\Users\\Rejects\\Pictures\\2.jpg";
//
////    	face.THFaceInit("D:/SDKdemo/javaDemo/libs");
//        face.THFaceInit("D:\\workspace\\RealEstateCertificatePrinter\\RealEstateCertificatePrinterClient\\thface");
//
//        float score = face.compareFaces(pic1, pic2);
//
//        System.out.println(String.format("compare result is: %.2f", score));
//
//        System.out.println(String.format("antispoofing result is: %d", face.antispoofing(pic1)));
//        System.out.println(String.format("antispoofing result is: %d", face.antispoofing(pic2)));

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
