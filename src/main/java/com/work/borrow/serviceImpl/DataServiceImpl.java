package com.work.borrow.serviceImpl;

import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.*;
import com.work.borrow.service.DataService;
import com.work.borrow.service.UserService;
import com.work.borrow.util.FileUtils;
import com.work.borrow.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * 数据资料操作业务类
 */
@Service("dataService")
public class DataServiceImpl implements DataService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private UserService userService;

    @Value("${pid.img.file.save.path}")
    private String filePath;
    @Value("${pid.img.file.visit.path.prefix}")
    private String visitPath;

    @Override
    public Message uploadPidUpImg(MultipartFile upFile, String mobile) {
        // 验证文件
        Message message = checkFile(upFile);
        if (message != null) return message;
        InputStream inputStream = null;
        Pid pid = null;
        try {
            inputStream = upFile.getInputStream();
            // 识别身份证图片
            pid = FileUtils.IdentificationCard(upFile.getBytes());
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
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_Y, Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_Y);
            message.put(Message.KEY_DATA, pid);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_N, Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_N);
        }
        return message;
    }

    @Override
    public Message uploadPidImg(MultipartFile upFile, MultipartFile downFile, AccountInfo accountInfo) {
        String upPath = "";
        String downPath = "";
        // 检查账户
        Message message = userService.checkAccount(accountInfo.getAccount());
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_FAIL)) return message;
        // 检查上传文件
        message = checkFile(upFile);
        if (message != null) return message;
        message = checkFile(downFile);
        if (message != null) return message;
        try {
            Pid pid = FileUtils.IdentificationCard(upFile.getBytes());
            if (pid == null) return Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_INFO_INPUT_PID_N,Message.VALUE_CONTENT_ACCOUNT_INFO_INPUT_PID_N);
            // 保存文件
            upPath = saveImg(upFile, accountInfo.getAccount(), PID_IMG_UP);
            downPath = saveImg(downFile, accountInfo.getAccount(), PID_IMG_DOWN);
            // 保存身份证信息
            accountInfo.setPidUp(upPath);
            accountInfo.setPidDown(downPath);
            accountInfo.setName(pid.getName());
            accountInfo.setSex(pid.getSex());
            accountInfo.setPid(pid.getCode());
            accountInfo.setAddress(pid.getAddress());
            message = inputInfo(accountInfo);
        } catch (Exception e) {
            // 若有上传成功的删除掉
            new File(upPath).deleteOnExit();
            new File(downPath).deleteOnExit();
            e.printStackTrace();
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
                message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_Y, Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_Y);
            } else {
                message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_PID_SAVE_N, Message.VALUE_CONTENT_ACCOUNT_PID_SAVE_N);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 保存文件
     *
     * @param file 需要保存的文件
     * @return
     */
    public String saveImg(MultipartFile file, String mobile, String fileStyle) throws IOException {
        String fileType = getFileType(file);
        return saveFile(mobile, file.getInputStream(), fileType, fileStyle);
    }

    /**
     * 文件检测
     *
     * @param file
     * @return
     */
    public Message checkFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Message.createFailMessage(Message.VALUE_CODE_UPLOAD_FILE_N, Message.VALUE_CONTENT_UPLOAD_FILE_N);
        }
        return null;
    }

    /**
     * 获取文件类型
     *
     * @param upFile MultipartFile
     * @return
     */
    public String getFileType(MultipartFile upFile) {
        String contentType = upFile.getContentType();
        return contentType.substring(contentType.lastIndexOf("/") + 1, contentType.length());
    }

    /**
     * 保存图片
     *
     * @param mobile      保存图片的用户手机号
     * @param inputStream 文件输入流
     * @param fileType    文件类型
     * @param upOrDown    正面或者反面
     * @return 保存的文件的路径
     * @see DataService 下的常亮参数
     */
    public String saveFile(String mobile, InputStream inputStream, String fileType, String upOrDown) {
        StringBuffer result = new StringBuffer();
        // 获取时间戳
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取文件前缀
        StringBuffer imgPath = new StringBuffer(filePath);
        char lastChar = filePath.charAt(filePath.length() - 1);
        if (!"/".equals(lastChar) || !"\\".equals(lastChar)) {
            imgPath.append("/");
        }
        result.append(calendar.get(Calendar.YEAR) + "/");
        result.append(calendar.get(Calendar.MONTH) + "/");
        result.append(calendar.get(Calendar.DAY_OF_MONTH) + "/");
        result.append(mobile + "/");
        result.append(mobile + "_" + upOrDown + "." + fileType);

        File file = com.work.borrow.util.FileUtils.createFile(imgPath.append(result).toString());
        try {
            OutputStream outputStream = new FileOutputStream(file);
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return visitPath+"/"+result.toString();
    }

    @Override
    public Message setFast(String mobile, String id) {
        Message message = null;
        boolean setState = accountMapper.changeAccountFast(mobile, id);
        if (setState) {
            message = Message.createSuccessMessage(Message.VALUE_CONTENT_ORDER_ADD_Y, Message.VALUE_CONTENT_ACCOUNT_FAST_SET_Y);
        } else {
            message = Message.createFailMessage(Message.VALUE_CONTENT_ORDER_ADD_N, Message.VALUE_CONTENT_ACCOUNT_FAST_SET_N);
        }
        return message;
    }

//    @Override
//    public Message getAccountInfo(AccountInfo accountInfo, Page<AccountInfo> page) {
//        Message message = null;
//        Map<String, Object> param = new HashMap<>();
//        param.put("account", accountInfo);
//        param.put("page", page);
//        List<AccountInfo> accountList = accountMapper.searchAccountInfo(param);
//        if (accountList != null && !accountList.isEmpty()) {
//            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_INFO_SEARCH_Y, Message.VALUE_CONTENT_ACCOUNT_INFO_SEARCH_Y);
//            if (page != null && page.getNo() != 0) {
//                // 获取共个数
//                page.setData(accountList);
//                page.setSize(accountMapper.size(accountInfo));
//                message.put(Message.KEY_DATA, page);
//            } else {
//                message.put(Message.KEY_DATA, accountList);
//            }
//        } else {
//            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_INFO_SEARCH_N, Message.VALUE_CONTENT_ACCOUNT_INFO_SEARCH_N);
//        }
//        return message;
//    }

    @Override
    public Message inputAccountWorkInfo(AccountInfo accountInfo) {
        return inputInfo(accountInfo);
    }

    @Override
    @Transactional
    public Message inputLinkmanArray(List<LinkMan> linkManArray, String acoount) {
        Message message = userService.checkAccount(acoount);
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_FAIL)) return message;
        final int[] okSize = {0};
        // 《《《《《《《《《《《《》》》》》》》》》》》》》》》》
        // 获取实名信息id,如果没有状态为待审核之前的信息就会引发错误
        AccountInfo accountInfo = accountMapper.getUseAccountByMobile(acoount);
        // 获取已经添加的实名认证的信息的电话联系人，存在就删除
        LinkMan linkMan = new LinkMan(accountInfo.getId(), acoount);
        List<LinkMan> linkManList = accountMapper.searchLinkman(linkMan);
        if (linkManList != null && !linkManList.isEmpty()) accountMapper.deleteLinkMan(linkMan);
        // 添加联系人
        linkManArray.forEach(data -> {
            data.setAccount(acoount);
            data.setInfoID(accountInfo.getId());
            okSize[0] = okSize[0] + (accountMapper.inputAccountLinkMan(data) ? 1 : 0);
        });
        if (okSize[0] == linkManArray.size()) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_INFO_INPUT_Y, Message.VALUE_CONTENT_ACCOUNT_INFO_INPUT_Y);
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_INFO_INPUT_N, Message.VALUE_CONTENT_ACCOUNT_INFO_INPUT_N);
        }
        return message;
    }

    @Override
    public Message inputAccountCard(AccountInfo accountInfo) {
        return inputInfo(accountInfo);
    }

    @Override
    public Message inputAccountInfo(AccountInfo accountInfo) {
        return inputInfo(accountInfo);
    }

    @Override
    public Message searchAccountInfo(AccountInfo accountInfo,Page<AccountInfo> page) {
        Message message = null;
        Map<String,Object> map = new HashMap<>();
        map.put(AccountMapper.ACCOUNT_INFO_PREFIX,accountInfo);
        map.put(AccountMapper.PAGE_ALIAS,page);
        List<AccountInfo> accountList = accountMapper.queryAccountInfo(map);
        if (accountList != null && !accountList.isEmpty()) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ORDER_QUERY_Y, Message.VALUE_CONTENT_ORDER_QUERY_Y);
            if (page != null && page.getNo() != 0 && page.getLength() != 0) {
                page.setData(accountList);
                // 获取共个数
                page.setSize(accountMapper.size(accountInfo));
                message.put(Message.KEY_DATA, page);
            } else {
                if (accountList.size() > 1) {
                    message.put(Message.KEY_DATA, accountList);
                } else {
                    message.put(Message.KEY_DATA, accountList.get(0));
                }
            }
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ORDER_QUERY_N, Message.VALUE_CONTENT_ORDER_QUERY_N);
        }
        return message;
    }

    /**
     * 录入信息公用方法
     * @param accountInfo
     * @return
     */
    private Message inputInfo(AccountInfo accountInfo) {
        Message message = userService.checkAccount(accountInfo.getAccount());
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_FAIL)) return message;
        AccountInfo queryInfo = accountMapper.getUseAccountByMobile(accountInfo.getAccount());
        boolean result = false;
        if (queryInfo != null && queryInfo.getId() != 0) { //修改
            result = accountMapper.updateAccountInfo(accountInfo);
        } else { // 添加
            result = accountMapper.inputAccountInfo(accountInfo);
        }
        if (result) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_INFO_INPUT_Y, Message.VALUE_CONTENT_ACCOUNT_INFO_INPUT_Y);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_INFO_INPUT_N, Message.VALUE_CONTENT_ACCOUNT_INFO_INPUT_N);
        }
        return message;
    }

    @Override
    public Message getUnOkInfo(String account) {
        Message message = null;
        AccountInfo accountInfo = accountMapper.getAccountStartStatus(account);
        if (accountInfo != null) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_INFO_FINASH_N,Message.VALUE_CONTENT_ACCOUNT_INFO_FINASH_N);
            message.put(Message.KEY_DATA,accountInfo.getStatus());
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_INFO_FINASH_Y,Message.VALUE_CONTENT_ACCOUNT_INFO_FINASH_Y);
        }
        return message;
    }

    @Override
    public Message inputStatus(AccountInfo accountInfo) {
        Message message = null;
        boolean updateStatus = accountMapper.updateStatus(accountInfo);
        if (updateStatus) {
            message = Message.createSuccessMessage();
        } else {
            message = Message.createFailMessage();
        }
        return message;
    }
}
