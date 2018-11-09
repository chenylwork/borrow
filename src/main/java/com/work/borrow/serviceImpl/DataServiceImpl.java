package com.work.borrow.serviceImpl;

import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.Message;
import com.work.borrow.service.DataService;
import com.work.borrow.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 数据资料操作业务类
 */
@Service("dataService")
public class DataServiceImpl implements DataService {
    @Autowired
    private AccountMapper accountMapper;

    @Value("${pid.img.file.path}")
    private String filePath;
    @Override
    public Message uploadPidImg(MultipartFile upFile, String mobile) {
        // 验证文件
        Message message = checkFile(upFile);
        if (message != null) return message;
        InputStream inputStream = null;
        String resultData = null;
        try {
            inputStream = upFile.getInputStream();
            // 识别身份证图片
            resultData = FileUtils.IdentificationCard(upFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取文件类型
        String upFileType = getFileType(upFile);
        boolean save = false;
        // 保存图片文件
        String upFilePath = saveFile(mobile, inputStream, upFileType, PID_IMG_UP);
        // 存储文件路径
        save = accountMapper.addPidImg(mobile, upFilePath);
        if (save) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_Y,Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_Y);
            message.put(Message.KEY_DATA,resultData);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_N,Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_N);
        }
        return message;
    }

    @Override
    public Message uploadPidHand(MultipartFile pidHand, String mobile) {
        Message message = checkFile(pidHand);
        if (message != null) return message;
        try {
            String filePath = saveImg(pidHand, mobile, PID_IMG_HAND);
            if (filePath != null && !filePath.equals("")) {
                boolean updeteHand = accountMapper.addPidHand(mobile, filePath);
                message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_Y,Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_Y);
            } else {
                message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_N,Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_N);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
    /**
     * 保存文件
     * @param file 需要保存的文件
     * @return
     */
    public String saveImg(MultipartFile file,String mobile,String fileStyle) throws IOException {
        String fileType = getFileType(file);
        return saveFile(mobile,file.getInputStream(),fileType,fileStyle);
    }

    /**
     * 文件检测
     * @param file
     * @return
     */
    public Message checkFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_UP_E,Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_UP_E);
        }
        return null;
    }

    /**
     * 获取文件类型
     * @param upFile MultipartFile
     * @return
     */
    public String getFileType(MultipartFile upFile) {
        String contentType = upFile.getContentType();
        return contentType.substring(contentType.lastIndexOf("/")+1,contentType.length());
    }

    /**
     * 保存图片
     * @param mobile 保存图片的用户手机号
     * @param inputStream 文件输入流
     * @param fileType 文件类型
     * @param upOrDown 正面或者反面
     * @see DataService 下的常亮参数
     * @return 保存的文件的路径
     */
    public String saveFile(String mobile,InputStream inputStream,String fileType,String upOrDown) {
        // 获取时间戳
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取文件
        StringBuffer imgPath = new StringBuffer(filePath);
        char lastChar = filePath.charAt(filePath.length()-1);
        if (!"/".equals(lastChar) || !"\\".equals(lastChar)) {
            imgPath.append("/");
        }
        imgPath.append(calendar.get(Calendar.YEAR)+"/");
        imgPath.append(calendar.get(Calendar.MONTH)+"/");
        imgPath.append(calendar.get(Calendar.DAY_OF_MONTH)+"/");
        imgPath.append(mobile+"/");
        imgPath.append(mobile+"_"+upOrDown+"."+fileType);
        File file = com.work.borrow.util.FileUtils.createFile(imgPath.toString());
        try {
            OutputStream outputStream = new FileOutputStream(file);
            FileCopyUtils.copy(inputStream,outputStream);
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return imgPath.toString();
    }

    @Override
    public Message setFast(String mobile,String id) {
        Message message = null;
        boolean setState = accountMapper.changeAccountFast(mobile, id);
        if (setState) {
            message = Message.createSuccessMessage(Message.VALUE_CONTENT_ORDER_ADD_Y,Message.VALUE_CONTENT_ACCOUNT_FAST_SET_Y);
        } else {
            message = Message.createFailMessage(Message.VALUE_CONTENT_ORDER_ADD_N,Message.VALUE_CONTENT_ACCOUNT_FAST_SET_N);
        }
        return message;
    }
}
