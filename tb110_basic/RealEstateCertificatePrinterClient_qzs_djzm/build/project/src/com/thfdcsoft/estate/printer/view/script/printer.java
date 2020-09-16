package com.thfdcsoft.estate.printer.view.script;

/**
 * 该类存放的是初代的打印方法，已弃置，为了保险起见留置的
 * 如有需要将其移入ClientScript中调用即可
 */
public class printer {

    /// 此方法为首次编写 测试无问题 代码冗长
//    public void print11(String value, String sum) {
//        // 停止倒计时
//        CountDownPlug.stopCountDown();
//        // 语音播报
//        MediaPlug.print();
//
//        HttpClientFactory.closeClient();
//
//        logger.info("打印数据长度：" + value.length());
//        // 将即将打印的数据写入日志
//        FileFactory.writeTXTFile(MainView.logFile, "打印方法 1.用户选中的打印数据：" + value);
//        if (thread != null && !thread.isInterrupted()) {
//            thread.interrupt();
//        }
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                // 判断要不要继续打印
//                int parseInt = Integer.parseInt(sum); // 打印列表一共有sum条数据
//
//                List<RealEstateInfo> infoList = JacksonFactory.readJsonList(value, RealEstateInfo.class);
//                // 所需证书数量
//                int count = infoList.size();
//
//                num = parseInt - count;
//                // 剩余证书数量
//                int remaining = Integer.parseInt(MainView.remaining);
//                if (count > remaining) {
//                    int c = count;
//                    int r = remaining;
//                    String text = "打印方法 2.计划打印" + c + "本证书,剩余空白证书数量仅有" + r + "本，证书不足！";
//                    FileFactory.writeTXTFile(MainView.logFile, text);
//
//                    Platform.runLater(() -> {
//                        MediaPlug.pageCountError();
//                        TransitPage.attentionPage("计划打印" + c + "本证书,剩余空白证书数量仅有" + r + "本，请联系工作人员及时补充！", 10);
//                    });
//                    return null;
//                }
//
//                boolean gaiZhangJiState = true;
//                boolean pageTurningState = true;
//                for (int i = 0; i < infoList.size(); i++) {
//                    // System.gc();
//                    docxP = new DocxPrint();
//                    // 检查数据 存在空数据 跳过
//                    if (!checkData(infoList.get(i))) {
//                        String text = "打印方法 3.此条记录不满足打印条件=" + infoList.get(i).toString();
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                        continue;
//                    }
//
//                    // 打印提示
//                    int f = i + 1;
//                    Platform.runLater(() -> {
//                        MainView.web.getEngine().executeScript("doPrint('" + f + "')");
//                    });
//
//                    // 查杀word进程 避免word占用文件导致程序无法操作
//                    DocxPrint.comfirmSingleProcess("WINWORD");
//
//                    // 处理宗地图、分布图图片
//                    String zongDiTuSaveUrl = Constant.FileDir.ZONGDITU;
//                    // 检查是否已经存在宗地图图片 存在则先删除
//                    ClientScript.cleanPic(zongDiTuSaveUrl);
//                    // 生成宗地图
//                    boolean zongDiTuFlag = ImageFactory.generateImage(infoList.get(i).getZongditu(), zongDiTuSaveUrl);
//                    String fenBuTuSaveUrl = Constant.FileDir.FENBUTU;
//                    // 检查是否已经存在分布图图片 存在则先删除
//                    ClientScript.cleanPic(fenBuTuSaveUrl);
//                    // 生成分布图
//                    boolean fenBuTuFlag = ImageFactory.generateImage(infoList.get(i).getFenbutu(), fenBuTuSaveUrl);
//                    if (!zongDiTuFlag || !fenBuTuFlag) {
//                        MediaPlug.printError();
//                        String text = "打印方法 4.失败！  地图生成状态：" + zongDiTuFlag + "   分布图生成状态：" + fenBuTuFlag;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                        showAttention("生成宗地图、分布图出现异常，请联系工作人员。", -1);
//                        return null;
//                    } else {
//                        String text = "打印方法 5.成功！  地图生成状态：" + zongDiTuFlag + "   分布图生成状态：" + fenBuTuFlag;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                    }
//
//                    // 生成word文档
//                    boolean makeFlag = docxP.makeFile(infoList.get(i));
//                    if (!makeFlag) {
//                        MediaPlug.printError();
//                        String text = "打印方法 6.生成打印文件状态：" + makeFlag;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                        showAttention("生成打印文件失败，请联系工作人员。", -1);
//                        return null;
//                    } else {
//                        String text = "打印方法 7.生成打印文件状态：" + makeFlag;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                    }
//                    // 保存生成的word文件
//                    String sourcePath = Constant.QRCode.DOCFILEOUT;
//                    String destPath = Constant.FileDir.DOCX_DIR + DateFactory.getCurrentDateString("yyyyMMddHHmmss")
//                            + "_" + ".docx";
//                    boolean saveState = ClientScript.saveFile(sourcePath, destPath);
//                    if (!saveState) {
//                        MediaPlug.printError();
//                        String text = "打印方法 8.保存打印文件状态：" + saveState;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                        showAttention("保存打印文件失败，请联系工作人员。", -1);
//                        return null;
//                    } else {
//                        String text = "打印方法 9.保存打印文件状态：" + saveState;
//                        FileFactory.writeTXTFile(MainView.logFile, text);
//                    }
//                    // 开始打印
//                    check = new File(Constant.QRCode.DOCFILEOUT);
//                    if (check.exists()) {
//                        String url = Constant.HardWare.HARDWARE_URL + "printDoc";
//                        HttpClientFactory.getInstance().doPost(Constant.QRCode.DOCFILEOUT, url);
//                    } else {
//                        MediaPlug.printError();
//                        showAttention("系统异常，请联系工组人员。", -1);
//                        return null;
//                    }
//                    ComCommunication comCommunication = new ComCommunication(Constant.COM.PAGE_RETURNING_COM);// 翻页机 5
//                    // 接收翻页机com5发送数据 共发送3次
//                    logger.info("当前state状态 = " + pageTurningState);
//                    int countPageTurn = 0;
//                    while (pageTurningState) {
//                        // 翻页机翻页打印完成 可进行拍照照
//                        countPageTurn++;
//                        // 90s后翻页机还未检测到数据 此时设备可能出现异常
//                        if (countPageTurn >= 180) {
//                            MediaPlug.printMaybeError();
//                            SerialTool.closePort(comCommunication.getComNum());
//                            showAttention("打印过程中可能出现异常，请联系工作人员检查1。", -1);
//                            return null;
//                        }
//                        if (pageTurningComState) {
//                            if (Constant.COM.PAGE_RETURNING_COM_VALUE.equals(pageTurningComStr)) {
//                                countPageTurn = 0;
//                                logger.info("翻页机=" + pageTurningComStr);
//                                pageCount += 1;
//                                logger.info("当前页=" + pageCount);
//                                // 还原翻页机状态数
//                                pageTurningComState = false;
//                                pageTurningComStr = "";
//                                if (pageCount < 3) {
//                                    logger.info("pageCount现在=" + pageCount);
//                                    // 获得正确参数 可进行拍照 保存 一秒钟后再进行拍照 保证照片清晰
//                                    Thread.sleep(1000);
//                                    String filePath = Constant.FileDir.CERTIFICATE_IMG
//                                            + DateFactory.getCurrentDateString("yyyyMMddHHmmss") + "_"
//                                            + infoList.get(i).getCertNumber() + ".jpg";
//                                    BufferedImage img = OpenCVCamFactory.webCam.getImage();
//                                    check = new File(filePath);
//                                    try {
//                                        ImageIO.write(img, "jpg", check);
//                                    } catch (IOException e) {
//                                        MediaPlug.printError();
//                                        SerialTool.closePort(comCommunication.getComNum());
//                                        String text = "证书打印 10.保存证书" + infoList.get(i).getCertNumber() + " 第"
//                                                + pageCount + "张照片出现异常";
//                                        FileFactory.writeTXTFile(MainView.logFile, text);
//                                        showAttention("证书打印过程中出现异常1，请联系工作人员。", -1);
//                                        e.printStackTrace();
//                                        return null;
//                                    }
//                                    String text = "保存证书" + infoList.get(i).getCertNumber() + "第" + pageCount + "张照片成功！";
//                                    FileFactory.writeTXTFile(MainView.logFile, text);
//                                }
//                                if (pageCount == 2) {
//                                    // 关闭翻页机串口
//                                    SerialTool.closePort(comCommunication.getComNum());
//                                    // 盖章机 4
//                                    comCommunication = new ComCommunication(Constant.COM.GAI_ZHANG_JI);
//                                    logger.info("盖章机等待中");
//                                    System.out.println("当前state1状态 = " + gaiZhangJiState);
//                                    int countGaiZhangJi = 0;
//                                    while (gaiZhangJiState) {
//                                        countGaiZhangJi++;
//                                        // 90s后 盖章机还未检测到数据 可能出现异常
//                                        if (countGaiZhangJi >= 180) {
//                                            MediaPlug.printMaybeError();
//                                            SerialTool.closePort(comCommunication.getComNum());
//                                            showAttention("打印过程中可能出现异常，请联系工作人员检查2。", -1);
//                                            return null;
//                                        }
//                                        if (gaiZhangJiComState
//                                                && Constant.COM.GAI_ZHANG_JI_VALUE.equals(gaiZhangJiComStr)) {
//                                            logger.info("盖章机=" + gaiZhangJiComStr);
//                                            countGaiZhangJi = 0;
//                                            // 证书数量更新
//                                            remaining--;
//                                            MainView.remaining = String.valueOf(remaining);
//                                            // 更新盖章机状态
//                                            gaiZhangJiComState = false;
//                                            gaiZhangJiComStr = "";
//                                            // 更新页码
//                                            pageCount = 0;
//                                            // 清空ocr识别文件夹
//                                            ClientScript.clearOCR();
//                                            // 进行证书编号页面拍照
//                                            String path = DateFactory.getCurrentDateString("yyyyMMddHHmmss") + ".jpg";
//                                            String scanerPath = Constant.Scaner.SCANER_DIR + path;// ocr识别文件夹
//                                            Thread.sleep(3000);// 保证能获取清晰图像
//                                            BufferedImage img = OpenCVCamFactory.webCam.getImage();
//                                            // 进行照片 保存
//                                            ocrFile = new File(scanerPath);
//                                            try {
//                                                ImageIO.write(img, "jpg", ocrFile);
//                                            } catch (IOException e) {
//                                                MediaPlug.printError();
//                                                SerialTool.closePort(comCommunication.getComNum());
//                                                String text = "证书打印 11.首次获取证书" + infoList.get(i).getCertNumber()
//                                                        + " 编号页面出现异常";
//                                                FileFactory.writeTXTFile(MainView.logFile, text);
//                                                showAttention("证书打印过程中出现异常2，请联系工作人员。", -1);
//                                                e.printStackTrace();
//                                                return null;
//                                            }
//                                            // 判断scaner文件夹下是否存在文件 存在则进行ocr识别
//                                            ocrFile = new File(Constant.Scaner.SCANER_DIR);
//                                            if (ocrFile.listFiles().length > 0) {
//                                                String text = "证书打印 12.首次获取证书" + infoList.get(i).getCertNumber()
//                                                        + " 编号页面照片成功";
//                                                FileFactory.writeTXTFile(MainView.logFile, text);
//                                                logger.info("首次获取证书编号照片获取成功");
//                                                File fileImg = ocrFile.listFiles()[0];
//                                                String targetPath = Constant.Scaner.SCANER_DIR + "crop.jpg";
//                                                boolean disposeState = disposeImage(fileImg, targetPath,
//                                                        comCommunication);
//                                                if (!disposeState) {
//                                                    logger.info("图片处理异常。");
//                                                    return null;
//                                                }
//                                                logger.info("即将进行ocr识别1。");
//                                                // ocr识别
//                                                HttpReturnDto returnDto = ocrRecognition(targetPath);
//                                                if (returnDto != null && 0 == Integer.parseInt(returnDto.getRespMsg())
//                                                        && !"00000000000".equals(returnDto.getRespJson())) {
//                                                    String ocr = returnDto.getRespJson();
//                                                    String ocrtext = "证书" + infoList.get(i).getCertNumber() + "编号识别号码="
//                                                            + ocr;
//                                                    FileFactory.writeTXTFile(MainView.logFile, ocrtext);
//
//                                                    String filePath = Constant.FileDir.CERT_SCAN_DIR
//                                                            + infoList.get(i).getCertNumber() + "_" + path;// 证书编号页面照片保存文件夹
//                                                    boolean saveOCRFile = ClientScript
//                                                            .saveFile(fileImg.getAbsolutePath(), filePath);
//                                                    if (!saveOCRFile) {
//                                                        MediaPlug.printError();
//                                                        SerialTool.closePort(comCommunication.getComNum());
//                                                        String text1 = "打印方法 13.保存证书编号照片状态：" + saveOCRFile;
//                                                        FileFactory.writeTXTFile(MainView.logFile, text1);
//                                                        showAttention("保存证书编号文件失败1，请联系工作人员。", -1);
//                                                        return null;
//                                                    } else {
//                                                        String text1 = "打印方法 14.保存证书编号照片状态：" + saveOCRFile;
//                                                        FileFactory.writeTXTFile(MainView.logFile, text1);
//                                                    }
//
//                                                    // 保存打印记录 证书权证号 权力类型 不动产单元号 ocr识别号码 word文档路径 ocr照片路径 日志文件
//                                                    boolean res = saveScanCopy(infoList.get(i).getCertNumber(),
//                                                            infoList.get(i).getUnitNumber(), ocr, destPath, filePath,
//                                                            MainView.logFile);
//                                                    // 清空图片缓存
//                                                    Toolkit.getDefaultToolkit().getImage(zongDiTuSaveUrl).flush();
//                                                    Toolkit.getDefaultToolkit().getImage(fenBuTuSaveUrl).flush();
//                                                    if (!res) {
//                                                        MediaPlug.printError();
//                                                        SerialTool.closePort(comCommunication.getComNum());
//                                                        String text1 = "证书打印 15.打印证书" + infoList.get(i).getCertNumber()
//                                                                + "更新设备证书数量、保存打印记录 失败！";
//                                                        FileFactory.writeTXTFile(MainView.logFile, text1);
//                                                        showAttention("证书打印记录保存失败3，请联系工作人员", -1);
//                                                        return null;
//                                                    } else {
//                                                        String text1 = "证书打印 16.打印证书" + infoList.get(i).getCertNumber()
//                                                                + "更新设备证书数量、保存打印记录 成功！";
//                                                        FileFactory.writeTXTFile(MainView.logFile, text1);
//
//                                                        // ocr识别成功
//                                                        ocrDiscernSuccess(comCommunication, infoList.get(i));
//
//                                                        SerialTool.closePort(comCommunication.getComNum());
//                                                        break;// 跳出接收盖章机数据循环
//                                                    }
//                                                } else {
//                                                    String text1 = "证书打印17. " + infoList.get(i).getCertNumber()
//                                                            + "第一次识别失败，进行第二次识别。";
//                                                    FileFactory.writeTXTFile(MainView.logFile, text1);
//                                                    // 重新拍照 进行识别
//                                                    // 清空ocr识别文件夹
//                                                    ClientScript.clearOCR();
//                                                    // 进行证书编号页面拍照
//                                                    String path1 = DateFactory.getCurrentDateString("yyyyMMddHHmmss")
//                                                            + ".jpg";
//                                                    String scanerPath1 = Constant.Scaner.SCANER_DIR + path1;// ocr识别文件夹
//                                                    Thread.sleep(3000);// 保证能获取清晰图像
//                                                    BufferedImage img1 = OpenCVCamFactory.webCam.getImage();
//                                                    // 进行照片 保存
//                                                    ocrFile = new File(scanerPath1);
//                                                    try {
//                                                        ImageIO.write(img1, "jpg", ocrFile);
//                                                    } catch (IOException e) {
//                                                        MediaPlug.printError();
//                                                        SerialTool.closePort(comCommunication.getComNum());
//                                                        String text2 = "证书打印18.  第二次获取证书"
//                                                                + infoList.get(i).getCertNumber() + " 编号页面照片出现异常";
//                                                        FileFactory.writeTXTFile(MainView.logFile, text2);
//                                                        showAttention("证书打印过程中出现异常3，请联系工作人员。", -1);
//                                                        e.printStackTrace();
//                                                        return null;
//                                                    }
//
//                                                    // 判断scaner文件夹下是否存在文件 存在则进行ocr识别
//                                                    ocrFile = new File(Constant.Scaner.SCANER_DIR);
//                                                    if (ocrFile.listFiles().length > 0) {
//                                                        String text2 = "证书打印19.  第二次获取证书"
//                                                                + infoList.get(i).getCertNumber() + " 编号页面照片成功！";
//                                                        FileFactory.writeTXTFile(MainView.logFile, text2);
//                                                        logger.info("第二次证书编号照片获取成功");
//                                                        File fileImg1 = ocrFile.listFiles()[0];
//                                                        // 处理图片
//                                                        boolean disposeState1 = disposeImage(fileImg1, targetPath,
//                                                                comCommunication);
//                                                        if (!disposeState1) {
//                                                            logger.info("图片处理异常。");
//                                                            return null;
//                                                        }
//                                                        logger.info("即将进行ocr识别2。");
//                                                        // ocr识别
//                                                        HttpReturnDto returnDto1 = ocrRecognition(targetPath);
//                                                        if (returnDto1 != null
//                                                                && 0 == Integer.parseInt(returnDto1.getRespMsg())
//                                                                && !"00000000000".equals(returnDto1.getRespJson())) {
//                                                            String ocr = returnDto1.getRespJson();
//                                                            String ocrtext = "证书" + infoList.get(i).getCertNumber()
//                                                                    + "编号识别号码=" + ocr;
//                                                            FileFactory.writeTXTFile(MainView.logFile, ocrtext);
//                                                            // 保存证书编号页面照片
//                                                            String filePath = Constant.FileDir.CERT_SCAN_DIR
//                                                                    + infoList.get(i).getCertNumber() + "_" + path1;// 证书编号页面照片保存文件夹
//                                                            boolean saveOCRFile = ClientScript
//                                                                    .saveFile(fileImg1.getAbsolutePath(), filePath);
//                                                            if (!saveOCRFile) {
//                                                                MediaPlug.printError();
//                                                                SerialTool.closePort(comCommunication.getComNum());
//                                                                String text3 = "打印方法 20.保存证书编号照片状态：" + saveOCRFile;
//                                                                FileFactory.writeTXTFile(MainView.logFile, text3);
//                                                                showAttention("保存证书编号文件失败2，请联系工作人员。", -1);
//                                                                return null;
//                                                            } else {
//                                                                String text3 = "打印方法 21.保存证书编号照片状态：" + saveOCRFile;
//                                                                FileFactory.writeTXTFile(MainView.logFile, text3);
//                                                            }
//                                                            // 保存打印记录 证书权证号 权力类型 不动产单元号 ocr识别号码 word文档路径 ocr照片路径 日志文件
//                                                            boolean res = saveScanCopy(infoList.get(i).getCertNumber(),
//                                                                    infoList.get(i).getUnitNumber(), ocr, destPath,
//                                                                    filePath, MainView.logFile);
//                                                            // 清空图片缓存
//                                                            Toolkit.getDefaultToolkit().getImage(zongDiTuSaveUrl)
//                                                                    .flush();
//                                                            Toolkit.getDefaultToolkit().getImage(fenBuTuSaveUrl)
//                                                                    .flush();
//                                                            if (!res) {
//                                                                MediaPlug.printError();
//                                                                SerialTool.closePort(comCommunication.getComNum());
//                                                                String text4 = "证书打印 22. 打印证书"
//                                                                        + infoList.get(i).getCertNumber()
//                                                                        + "更新设备证书数量、保存打印记录 失败！";
//                                                                FileFactory.writeTXTFile(MainView.logFile, text4);
//                                                                showAttention("证书打印记录保存失败4,请联系工作人员。", -1);
//                                                                return null;
//                                                            } else {
//                                                                String text4 = "证书打印 23.打印证书"
//                                                                        + infoList.get(i).getCertNumber()
//                                                                        + "更新设备证书数量、保存打印记录 成功！";
//                                                                FileFactory.writeTXTFile(MainView.logFile, text4);
//                                                                // ocr识别成功
//                                                                ocrDiscernSuccess(comCommunication, infoList.get(i));
//
//                                                                SerialTool.closePort(comCommunication.getComNum());
//                                                                break;
//                                                            }
//                                                        } else {
//                                                            String text6 = "证书打印 24.ocr识别两次均失败！";
//                                                            FileFactory.writeTXTFile(MainView.logFile, text6);
//                                                            pageTurningState = false;
//                                                            gaiZhangJiState = false;
//                                                            // 两次识别失败 关闭串口通信。
//                                                            SerialTool.closePort(comCommunication.getComNum());
//                                                            // 两次识别均失败时 保存证书编号页面 照片
//                                                            String filePath = Constant.FileDir.CERT_SCAN_DIR
//                                                                    + infoList.get(i).getCertNumber() + "_" + path1;// 证书编号页面照片保存文件夹
//                                                            boolean saveOCRFile = ClientScript
//                                                                    .saveFile(fileImg1.getAbsolutePath(), filePath);
//                                                            if (!saveOCRFile) {
//                                                                String text3 = "打印方法 99.两次识别均失败 保存证书编号照片状态："
//                                                                        + saveOCRFile;
//                                                                FileFactory.writeTXTFile(MainView.logFile, text3);
//                                                                return null;
//                                                            } else {
//                                                                String text3 = "打印方法 98.两次识别均失败 保存证书编号照片状态："
//                                                                        + saveOCRFile;
//                                                                FileFactory.writeTXTFile(MainView.logFile, text3);
//                                                            }
//
//                                                            Platform.runLater(() -> {
//                                                                // 语音播报识别错误
//                                                                MediaPlug.readNumError();
//                                                                TransitPage.attentionPage("证书编号识别异常,请联系工作人员。", -1);
//                                                                MainView.web.getEngine()
//                                                                        .executeScript("setPageFlag('readError')");
//                                                            });
//                                                            return null;
//                                                        }
//                                                    } else {
//                                                        MediaPlug.printError();
//                                                        SerialTool.closePort(comCommunication.getComNum());
//                                                        String text7 = "证书打印 25.内部错误 ： 证书编号照片scaner文件夹为空！";
//                                                        FileFactory.writeTXTFile(MainView.logFile, text7);
//                                                        showAttention("证书打印出现异常6,请联系工作人员。", -1);
//                                                        return null;
//                                                    }
//                                                }
//                                            } else {
//                                                MediaPlug.printError();
//                                                SerialTool.closePort(comCommunication.getComNum());
//                                                String text = "证书打印 17.内部错误 ： 证书编号照片scaner文件夹为空！";
//                                                FileFactory.writeTXTFile(MainView.logFile, text);
//                                                showAttention("证书打印出现异常7,请联系工作人员。", -1);
//                                                return null;
//                                            }
//                                        }
//                                        Thread.sleep(500);
//                                    }
//                                    break;
//                                }
//                            } else {
//                                MediaPlug.printError();
//                                SerialTool.closePort(comCommunication.getComNum());
//                                String text = "打印方法 26.打印证书" + infoList.get(i).getCertNumber() + "过程中 翻页机异常";
//                                FileFactory.writeTXTFile(MainView.logFile, text);
//                                showAttention("证书打印过程中出现异常8,请联系工作人员。", -1);
//                                return null;
//                            }
//                        }
//                        Thread.sleep(500);
//                    }
//                }
//                // 加载打印完成页面
//                logger.info("打印完成");
//                String text2 = "打印成功！";
//                FileFactory.writeTXTFile(MainView.logFile, text2);
//                Platform.runLater(() -> {
//                    CountDownPlug.startCountDown();
//                    MediaPlug.printSuccess();
//                    String url = Constant.Business.BUSINESS_URL + "client/printSuccess";
//                    MainView.web.getEngine().load(url);
//                });
//                return null;
//            }
//        };
//        thread = new Thread(task);
//        thread.setDaemon(true);
//        thread.start();
//    }
}
