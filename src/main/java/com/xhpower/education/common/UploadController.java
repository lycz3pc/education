package com.xhpower.education.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xhpower.education.common.response.UploadFileRspVO;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.UploadUtil;

@Controller
@RequestMapping("/sys")
public class UploadController {
    @RequestMapping("/upload")
    @ResponseBody
    private R upload(String uploadPath, Boolean isCreat_Date_Folder, Boolean isCreate_FileName,
            HttpServletRequest request, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                /*isCreate_FileName = isCreate_FileName == null ? true : isCreate_FileName;
                isCreat_Date_Folder = isCreat_Date_Folder == null ? false : isCreat_Date_Folder;
                if (isCreat_Date_Folder) {
                    uploadPath += "/" + DateHelper.format(new Date(), "yyyyMMdd");
                }
                // 保存的文件路径
                String realPath = request.getSession().getServletContext().getRealPath("/") + uploadPath;
                // 上传文件名称
                String fileName = file.getOriginalFilename();

                // 生成新的文件名
                if (isCreate_FileName) {
                    String type = FileHelper.getSuffix(fileName);
                    fileName = System.currentTimeMillis() + "." + type;
                }
                // 创建存储目录
                File saveDir = new File(realPath, fileName.toString());

                if (!saveDir.getParentFile().exists()) {
                    saveDir.getParentFile().mkdirs();
                }
                // 转存文件
                file.transferTo(saveDir);

                if (FileHelper.isImager(file.getContentType())) {
                    if (!"gif".equals(FileHelper.getSuffix(fileName))) {
                        resize(saveDir, realPath + "/" + fileName);
                    }
                }
                
                return R.success().put("filePath", uploadPath + "/" + fileName);*/
                
            	//上传至云服务
            	UploadFileRspVO rspVO = UploadUtil.uploadFileToCloud(file);
            	return R.success().put("filePath", rspVO.getQcloud_file_wwwurl());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void resize(File file, String fileNewName) throws IOException {
        Image img = ImageIO.read(file); // 构造Image对象
        int width = img.getWidth(null); // 得到源图宽
        int height = img.getHeight(null); // 得到源图长
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
        File destFile = new File(fileNewName);
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
     /*   JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
       encoder.encode(image); // JPEG编码
*/        out.close();
    }
}
