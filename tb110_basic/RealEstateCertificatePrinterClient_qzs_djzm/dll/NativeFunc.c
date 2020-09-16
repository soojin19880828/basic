#include "jni.h"
#include "stdio.h"
#include "windows.h"
#include "sys/types.h"
#include "unistd.h"
#include "string.h"

HMODULE Stampdll = NULL;
HMODULE Termbdll = NULL;
HMODULE ControlSystemdll = NULL;

char dll_path[128];

//导入需要的dll文件以及设置dll文件路径
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_SetDllPath(JNIEnv *env, jclass jc, jstring dllpath) {
    char str[128];
    jboolean isCp;
	const char *c_dllpath = (*env) -> GetStringUTFChars(env, dllpath, &isCp);
	strcpy(dll_path, c_dllpath);
	(*env) -> ReleaseStringUTFChars(env, dllpath, c_dllpath);
    if(Stampdll == NULL) {
        sprintf(str, "%s\\ZT_Stamp.dll", dll_path);
        Stampdll = LoadLibrary(str);
        if(Stampdll == NULL) {
            printf("Load ZT_Stamp.dll fail!!!");
			return -1;
        }
    }
    if(Termbdll == NULL) {
        sprintf(str, "%s\\Termb.dll", dll_path);
        Termbdll = LoadLibrary(str);
        if(Termbdll == NULL) {
            printf("Load Termb.dll fail!!!");
			return -2;
        }
    }
    if(ControlSystemdll == NULL) {
        sprintf(str, "%s\\ControlSystem.dll", dll_path);
        ControlSystemdll = LoadLibrary(str);
        if(ControlSystemdll == NULL) {
            printf("Load ControlSystem.dll fail!!!");
			return -3;
        }
    }
	return 0;
}

//打开设备
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPOpenDevice(JNIEnv *env, jclass jc, jstring pstrCom, jintArray nHandle, jint nFage) {
	char current_path[128];
    char c_pstrCom[128];
    int c_nHandle;
	int res;
    jboolean isCp;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPOpenDevice)(char *pstrCom, int *nHandle, long nFage);
    STAMPOpenDevice func = (STAMPOpenDevice)GetProcAddress(Stampdll, "ZT_STAMP_OpenDevice");
    if(func == NULL) {
        printf("ZT_STAMP_OpenDevice function address is not find!!!\n");
        return -1;
    }
    const char *str = (*env) -> GetStringUTFChars(env, pstrCom, &isCp);
    jint *p = (*env) -> GetIntArrayElements(env, nHandle, &isCp);
    strcpy(c_pstrCom, str);
	chdir(dll_path);
    res = func(c_pstrCom, &c_nHandle, nFage);//这里调用dll中的函数
	chdir(current_path);
    *p = c_nHandle;
    (*env) -> ReleaseIntArrayElements(env, nHandle, p, 0);
    (*env) -> ReleaseStringUTFChars(env, pstrCom, str);
	return res;
}

//关闭设备
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPCloseDevice(JNIEnv *env, jclass jc, jint nHandle) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPCloseDevice)(long nHandle);
    STAMPCloseDevice func = (STAMPCloseDevice)GetProcAddress(Stampdll, "ZT_STAMP_CloseDevice");
    if(func == NULL) {
        printf("ZT_STAMP_CloseDevice function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle);
	chdir(current_path);
	return res;
}

//设备复位
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPReset(JNIEnv *env, jclass jc, jint nHandle) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPReset)(long nHandle);
    STAMPReset func = (STAMPReset)GetProcAddress(Stampdll, "ZT_STAMP_Reset");
    if(func == NULL) {
        printf("ZT_STAMP_Reset function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//获取设备状态
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPGetStatus(JNIEnv *env, jclass jc, jint nHandle) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPGetStatus)(long nHandle);
    STAMPGetStatus func = (STAMPGetStatus)GetProcAddress(Stampdll, "ZT_STAMP_GetStatus");
    if(func == NULL) {
        printf("ZT_STAMP_GetStatus function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//查询走纸张数
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPGetPaperCount(JNIEnv *env, jclass jc, jint nHandle) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPGetPaperCount)(long nHandle);
    STAMPGetPaperCount func = (STAMPGetPaperCount)GetProcAddress(Stampdll, "ZT_STAMP_GetPaperCount");
    if(func == NULL) {
        printf("ZT_STAMP_GetPaperCount function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//设置印章位置
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPSetStampPos(JNIEnv *env, jclass jc, jint nHandle, jint nStampNum, jint nLeftPosition, jint nRightPosition) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPSetStampPos)(long nHandle, long nStampNum, long nLeftPosition, long nRightPosition);
    STAMPSetStampPos func = (STAMPSetStampPos)GetProcAddress(Stampdll, "ZT_STAMP_SetStampPos");
    if(func == NULL) {
        printf("ZT_STAMP_SetStampPos function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle, nStampNum, nLeftPosition, nRightPosition);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//设置印章位置(A4)
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPSetStampPosEX(JNIEnv *env, jclass jc, jint nHandle, jint nStampNum, jint nLeftPosition, jint nRightPosition) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPSetStampPosEX)(long nHandle, long nStampNum, long nLeftPosition, long nRightPosition);
    STAMPSetStampPosEX func = (STAMPSetStampPosEX)GetProcAddress(Stampdll, "ZT_STAMP_SetStampPosEX");
    if(func == NULL) {
        printf("ZT_STAMP_SetStampPosEX function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle, nStampNum, nLeftPosition, nRightPosition);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//设置印章总页数
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPSetWorkCount(JNIEnv *env, jclass jc, jint nHandle, jint nsheets) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPSetWorkCount)(long nHandle, long nsheets);
    STAMPSetWorkCount func = (STAMPSetWorkCount)GetProcAddress(Stampdll, "ZT_STAMP_SetWorkCount");
    if(func == NULL) {
        printf("ZT_STAMP_SetWorkCount function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle, nsheets);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//设置每页盖章模式
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPSetWorksheet(JNIEnv *env, jclass jc, jint nHandle, jint nNum, jint nLR, jint nMode) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPSetWorksheet)(long nHandle, long nNum, long nLR, long nMode);
    STAMPSetWorksheet func = (STAMPSetWorksheet)GetProcAddress(Stampdll, "ZT_STAMP_SetWorksheet");
    if(func == NULL) {
        printf("ZT_STAMP_SetWorksheet function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle, nNum, nLR, nMode);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//盖章启动
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPStartUp(JNIEnv *env, jclass jc, jint nHandle) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPStartUp)(long nHandle);
    STAMPStartUp func = (STAMPStartUp)GetProcAddress(Stampdll, "ZT_STAMP_StartUp");
    if(func == NULL) {
        printf("ZT_STAMP_StartUp function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

//盖章停止
JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_STAMPStartStop(JNIEnv *env, jclass jc, jint nHandle) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
    typedef int (*STAMPStartStop)(long nHandle);
    STAMPStartStop func = (STAMPStartStop)GetProcAddress(Stampdll, "ZT_STAMP_Stop");
    if(func == NULL) {
        printf("ZT_STAMP_Stop function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nHandle);//这里调用dll中的函数
	chdir(current_path);
	return func(nHandle);
}


JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_Cvrinitcomm(JNIEnv *env, jclass jc, jint port) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*CVRInitComm)(int port);
	CVRInitComm func = (CVRInitComm)GetProcAddress(Termbdll, "CVR_InitComm");
    if(func == NULL) {
        printf("CVR_InitComm function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(port);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_Cvrclosecomm(JNIEnv *env, jclass jc) {
	char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*CVRCloseComm)();
	CVRCloseComm func = (CVRCloseComm)GetProcAddress(Termbdll, "CVR_CloseComm");
    if(func == NULL) {
        printf("CVR_CloseComm function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func();//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_Cvrauthenticate(JNIEnv *env, jclass jc) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*CVRAuthenticate)();
	CVRAuthenticate func = (CVRAuthenticate)GetProcAddress(Termbdll, "CVR_Authenticate");
    if(func == NULL) {
        printf("CVR_Authenticate function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func();//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_Cvrreadcontent(JNIEnv *env, jclass jc, jint active) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*CVRReadContent)(int active);
	CVRReadContent func = (CVRReadContent)GetProcAddress(Termbdll, "CVR_Read_Content");
    if(func == NULL) {
        printf("CVR_Read_Content function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(active);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_OpenDevice(JNIEnv *env, jclass jc, jint nPort, jlong nComBaud) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*OpenDevice)(int nPort, long nComBaud);
	OpenDevice func = (OpenDevice)GetProcAddress(ControlSystemdll, "OpenDevice");
    if(func == NULL) {
        printf("OpenDevice function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nPort, nComBaud);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_CloseDevice(JNIEnv *env, jclass jc) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*CloseDevice)();
	CloseDevice func = (CloseDevice)GetProcAddress(ControlSystemdll, "CloseDevice");
    if(func == NULL) {
        printf("CloseDevice function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func();//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_CtrlLed(JNIEnv *env, jclass jc, jint nLed, jint nStatus) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*CtrlLed)(int nLed, int nStatus);
	CtrlLed func = (CtrlLed)GetProcAddress(ControlSystemdll, "CtrlLed");
    if(func == NULL) {
        printf("CtrlLed function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nLed, nStatus);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_AllLed(JNIEnv *env, jclass jc, jint nStatus) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*AllLed)(int nStatus);
	AllLed func = (AllLed)GetProcAddress(ControlSystemdll, "AllLed");
    if(func == NULL) {
        printf("AllLed function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func(nStatus);//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_GetStatus(JNIEnv *env, jclass jc) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*GetStatus)();
	GetStatus func = (GetStatus)GetProcAddress(ControlSystemdll, "GetStatus");
    if(func == NULL) {
        printf("GetStatus function address is not find!!!\n");
        return -1;
    }
	chdir(dll_path);
	res = func();//这里调用dll中的函数
	chdir(current_path);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_GetVer(JNIEnv *env, jclass jc, jcharArray szVersion) {
    char current_path[128];
	int res;
	getcwd(current_path, sizeof(current_path));
	typedef int (*GetVer)();
	GetVer func = (GetVer)GetProcAddress(ControlSystemdll, "GetVer");
    if(func == NULL) {
        printf("GetVer function address is not find!!!\n");
        return -1;
    }
	char c_szVersion[1024];
	chdir(dll_path);
	res = func(c_szVersion);//这里调用dll中的函数
	chdir(current_path);	
	int slen = strlen(c_szVersion);
	int length = MultiByteToWideChar( CP_ACP, 0, (LPCSTR)c_szVersion, slen, NULL, 0);
	jboolean isCp;
	jchar *p_szVersion = (*env) -> GetCharArrayElements(env, szVersion, &isCp);
	MultiByteToWideChar( CP_ACP, 0, (LPCSTR) c_szVersion, slen, (LPWSTR) p_szVersion, length);
	(*env) -> ReleaseCharArrayElements(env, szVersion, p_szVersion, 0);
	return res;
}

JNIEXPORT jint JNICALL _Java_cn_worldflyng_jni_NativeFunc_CheckDll(JNIEnv *env, jclass jc) {
	printf("I am dll lib!!!\n");
    return 0;
}