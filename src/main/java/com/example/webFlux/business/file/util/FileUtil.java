package com.example.webFlux.business.file.util;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;

public class FileUtil {

    // 缓存文件头信息-文件头信息
    public static final HashMap<String, String> mFileTypes = new HashMap<>();

    static {
        // images
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");
        mFileTypes.put("424D", "bmp");
        //
        mFileTypes.put("41433130", "dwg"); // CAD
        mFileTypes.put("38425053", "psd");
        mFileTypes.put("7B5C727466", "rtf"); // 日记本
        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("68746D6C3E", "html");
        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
//        mFileTypes.put("D0CF11E0", "docOrXls");
        mFileTypes.put("D0CF11E0", "docOrXls");//excel2003版本文件
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("252150532D41646F6265", "ps");
        mFileTypes.put("255044462D312E", "pdf");
//        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("504B0304", "docxOrXlsx");//excel2007以上版本文件
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("1F8B08", "gz");
    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param bytes 原生byte
     * @return 16进制字符串
     */
    private static Mono<String> bytesToHexString(Flux<byte[]> bytes) {

        Disposable subscribe = bytes.map(f -> {
            StringBuilder str = new StringBuilder();
            for (byte b : f) {
                int v = b & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    str.append(0);
                }
                str.append(hv);
            }
            return str;
        }).subscribe(Mono::just);
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return Mono.just(stringBuilder.toString());
        return Mono.empty();
    }

    /**
     * 得到文件头
     *
     * @param file 文件
     * @return 文件头
     * @throws IOException
     */
    private static Mono<String> getFileContent(FilePart file) throws IOException {
        byte[] b = new byte[28];
        Flux<byte[]> map = file.content().map(f -> {
            DataBuffer read = f.read(b, 0, 28);
            return b;
        });
//                 .then().map(f -> {
//            return bytesToHexString(b)
//                    .map(fi->{
//                        return Mono.just(fi);
//                    });
//        });
        return Mono.empty();
    }

    /**
     * 判断文件类型
     *
     * @param file 文件
     * @return 文件类型
     */
    public static Mono<String> getType(FilePart file) throws IOException {
        Mono<String> fileHead = getFileContent(file);
//        fileHead
//                .flatMap(f -> {
//                    return mFileTypes.get(f);
//                });
//        if (fileHead == null) {
//            return null;
//        }
//        fileHead = fileHead.toUpperCase();
//        return mFileTypes.get(fileHead);
        return Mono.empty();
    }
}
