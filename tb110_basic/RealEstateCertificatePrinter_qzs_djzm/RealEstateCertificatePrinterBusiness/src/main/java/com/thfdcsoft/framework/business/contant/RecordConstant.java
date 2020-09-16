package com.thfdcsoft.framework.business.contant;

/**
 * 日志记录常量
 * 
 * @author 张嘉琪
 *
 */
public abstract class RecordConstant {

	// 日志相关文件目录
	public interface FileDir {

		// 身份证照片
		public static final String ID_PIC_DIR = StaticConstant.RECORD_ROOT + "pics/id/";

		// 现场照片
		public static final String DET_PIC_DIR = StaticConstant.RECORD_ROOT + "pics/det/";

		// 扫描件图片
		public static final String CERT_SCAN_DIR = StaticConstant.RECORD_ROOT + "pics/scan/";
		
		// 日志文件
		public static final String LOG_DIR = StaticConstant.RECORD_ROOT + "logs/";
	}

	// 日志相关文件后缀名
	public interface SuffixName {

		// 照片
		public static final String JPG = ".jpg";
		
		// 日志
		public static final String LOG = ".txt";
	}

	public interface FileName {

		public static final String ID_PIC_PATH = "/idPic.jpg";

		public static final String DET_PIC_PATH = "/detPic.jpg";

		public static final String RECORD_PATH = "/record.txt";
	}

	// 信息回传状态
	public interface TranStatus {
		
		// 等待回传
		public static final String WAITING = "00";
		
		// 回传成功
		public static final String SUCCESS = "01";
		
		// 回传失败
		public static final String FAILURE = "02";
	}

	//查询参数
	public interface ParametetsConstant{
		//用户名
		//测试系统（濮阳）
//		public static final String ACCOUNT ="000348";
//		public static final String PASSWORD="556b147394488fd7892afb7a06fab9fa";
		//正式系统（濮阳）
//		public static final String ACCOUNT ="000052";
//		public static final String PASSWORD="ffcb50633ad639010f28d87646e2ea82";

		//正式系统（范管理员）
//		public static final String ACCOUNT ="000025";
//		public static final String PASSWORD="9c3b6825e418b1c8caef90462daae15b";
		//正式系统（范机器用）
//		public static final String ACCOUNT ="000050";
//		public static final String PASSWORD="e207faedc98ec166e5ea51d1999907e9";

		//汤阴县
		public static final String ACCOUNT ="000033";
		public static final String PASSWORD="aef44202fbdd96168aafb6c2ccd3745f";

		//RSA公钥（正式库和测试库一样）
		public static final String RSA="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC14GfgsTkNHW3BrdMg3JDFzXDZ3qp5oeLDI7nKou1IuZN7Bu3higboeRxxNVqW+Dluo9hE6fT4U2Z4is5svpzBdKbIrdTBMbhOcq9RvseOz+zbZKMQLCf0ziLrm+4VcMzWiCxVajPWi1Atn/Xf8aWjqh3JQp4EkDUCnB7fOcQ9IQIDAQAB";

	}
	/*//管理系统URL
	public interface ManageUrl{
		public static final String MANAGEURL="http://localhost:8080/Manage/bussiness/";
	}*/

	// 打印类型
	public interface PrintType {
		// 登记证明
		public static final String PRINT_DJZM = "djzm";

		// 权证书
		public static final String PRINT_QZS = "qzs";
	}

	//
	public interface Code {
		public static final String SUCCESS = "200"; // 返回成功，并正确获取到数据（正常）
		public static final String EMPTY = "205"; // 返回成功，但未查询到信息
		public static final String ERROR = "204"; // 返回成功，查询到的信息出错
		public static final String FAILURE = "408"; // 返回失败，请求超时

	}
}
