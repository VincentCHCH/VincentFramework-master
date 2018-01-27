package com.vincent.network.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.vincent.network.LibApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;


public class FileUtils {

    public static final String BACKGROUND_FILE_PATH = ".airtouchscitybackground";

    public static final String BACKGROUND_BLUR_FILE_PATH = "blurbackground";

    public static final String INDIA_BACKGROUND_FILE_PATH = "indian";

    public static final String INDIA_BACKGROUND_BLUR_FILE_PATH = "indian/blurbackground";


    private static final String LOG_TAG = FileUtils.class.getSimpleName();

    /**
     * Write text to filer.
     *
     * @param content
     * @param dir
     * @param fileName
     * @return
     */
    public static boolean write2File(String content, String dir, String fileName) {
        return write2File(content, dir, fileName, false);
    }

    /**
     * @param fileName
     * @return
     */
    public static String readFile(String fileName) {
        File targetFile = new File(fileName);
        StringBuilder readedStr = new StringBuilder();

        InputStream in = null;
        BufferedReader br = null;

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
                return readedStr.toString();
            } else {
                fileInputStream = new FileInputStream(targetFile);
                in = new BufferedInputStream(fileInputStream);

                inputStreamReader = new InputStreamReader(in, "UTF-8");
                br = new BufferedReader(inputStreamReader);
                String tmp;

                while ((tmp = br.readLine()) != null) {
                    readedStr.append(tmp);
                }

                return readedStr.toString();
            }
        } catch (Exception e) {
            LogUtil.error(LOG_TAG,"getUnsafeOkHttpClientWithCer",e);
        }finally {
            closeInputStream(in);
            closeInputStream(fileInputStream);

            closeReader(br);
            closeReader(inputStreamReader);

        }
        return readedStr.toString();
    }

    private static void closeInputStream(InputStream inputStream){
        if (inputStream != null){
            try{
                inputStream.close();
            }catch (Exception e){
                LogUtil.error("FileUtil","closeInputStream",e);
            }

        }
    }

    private static void closeReader(Reader reader){
        if (reader != null){
            try{
                reader.close();
            }catch (Exception e){
                LogUtil.error("FileUtil","closeReader",e);
            }

        }
    }

    private static void closeOutputStream(OutputStream outputStream){
        if (outputStream != null){
            try{
                outputStream.close();
            }catch (Exception e){
                LogUtil.error("FileUtil","closeOutputStream",e);
            }

        }
    }

    /**
     * Retrieves last modified time.
     *
     * @param path
     * @return
     */
    public static long getLastModifiedTime(String path) {
        File file = new File(path);
        return file.lastModified();
    }

    /**
     * Check whether file exists.
     *
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Check whether file exists.
     *
     * @return
     */
    public static boolean fileCpy(String srcPath, String dstPath, boolean rewrite) {
        File srcFile = null;
        File dstFile = null;
        FileInputStream fosfrom = null;
        FileOutputStream fosto = null;
        try {
            srcFile = new File(srcPath);
            dstFile = new File(dstPath);
            if (!srcFile.exists()) {
                return false;
            }
            if (!srcFile.isFile()) {
                return false;
            }
            if (!srcFile.canRead()) {
                return false;
            }
            if (!dstFile.getParentFile().exists()) {
                dstFile.getParentFile().mkdirs();
            }
            if (dstFile.exists() && rewrite) {
                dstFile.delete();
            }
            fosfrom = new FileInputStream(srcFile);
            fosto = new FileOutputStream(dstFile, false);
            byte bt[] = new byte[1024];
            int c;

            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            return true;
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "fileCpy:"+e);
            return false;
        } finally {
            if (fosfrom != null) {
                try {
                    fosfrom.close();
                } catch (IOException e) {
                    LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "fileCpy.IOException.1"+e);
                }
            }
            if (fosto != null) {
                try {
                    fosto.close();
                } catch (IOException e) {
                    LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "fileCpy.IOException.2"+e);
                }
            }
        }
    }

    /**
     * Rename file.
     *
     * @param srcPath
     * @param dstPath
     * @return
     */
    public static boolean renameToFile(String srcPath, String dstPath) {
        File srcFile = null;
        File dstFile = null;
        try {
            srcFile = new File(srcPath);
            dstFile = new File(dstPath);
            return srcFile.renameTo(dstFile);
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "Exception:"+e);
            return false;
        }

    }

    /**
     * Create file.
     *
     * @param dir      directory
     * @param fileName file name
     */
    public static boolean createFile(String dir, String fileName) {
        if (StringUtil.isEmpty(dir) || StringUtil.isEmpty(fileName)) {
            return false;
        }
        try {
            // create directory
            createDir(dir);
            return createFile(StringUtil.trimRight(dir, "/") + "/"
                    + StringUtil.trimLeft(fileName, "/"));
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "createFile:"+e);
            return false;
        }
    }

    /**
     * Create directory.
     *
     * @param dir
     */
    public static void createDir(String dir) {
        if (StringUtil.isEmpty(dir)) {
            return;
        }
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * Create file.
     *
     * @param path the whole file.
     */
    public static boolean createFile(String path) {
        if (StringUtil.isEmpty(path)) {
            return false;
        }
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            return true;
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "createFile"+e);
            return false;
        }
    }

    /**
     * Create file.
     *
     * @param path the whole file.
     */
    public static void clearFileInDir(String path) {
        if (StringUtil.isEmpty(path)) {
            return;
        }
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {

            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    /**
     * Delete file.
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    /**
     * Uncompress the zip file to folder
     *
     * @param zipFileString
     * @param outPathString
     * @throws Exception
     */
    public static void unZipFolder(String zipFileString, String outPathString) {
        LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "UnZipFolder(zipFileString, String)");
        unZipFolder(zipFileString, outPathString);
    }

    /**
     * Uncompress the zip file to folder
     *
     * @param inputStream   input stream
     * @param outPathString folder uncompressed
     * @throws Exception
     */
    public static void unZipFolder(InputStream inputStream, String outPathString) {
        if (inputStream == null || StringUtil.isEmpty(outPathString)) {
            return;
        }
        LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "UnZipFolder(InputStream, String)");
        java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(inputStream);
        java.util.zip.ZipEntry zipEntry;
        String szName = "";
        FileOutputStream out = null;
        try {
            while ((zipEntry = inZip.getNextEntry()) != null) {
                szName = zipEntry.getName();


                if (zipEntry.isDirectory()) {

                    // get the folder name of the widget
                    szName = szName.substring(0, szName.length() - 1);
                    File folder = new File(outPathString + File.separator + szName);
                    folder.mkdirs();

                } else {

                    try{
                        File file = new File(outPathString + File.separator + szName);
                        file.createNewFile();
                        // get the output stream of the file
                        out = new FileOutputStream(file, false);
                        int len;
                        byte[] buffer = new byte[1024];
                        // read (len) bytes into buffer
                        while ((len = inZip.read(buffer)) != -1) {
                            // write (len) byte from buffer at the position 0
                            out.write(buffer, 0, len);
                            out.flush();
                        }
                    }finally {
                        closeOutputStream(out);
                    }

                }

            }//end of while
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR,LOG_TAG,"unZipFolder:"+e);
        } finally {
            closeInputStream(inZip);

        }
    }

    /**
     * Write text to filer.
     *
     * @param content
     * @param dir
     * @param fileName
     * @return
     */
    public static boolean write2File(String content, String dir, String fileName, boolean appendMode) {
        if (StringUtil.isEmpty(dir) || StringUtil.isEmpty(fileName)) {
            return false;
        }
        String path = StringUtil.trimRight(dir, "/") + "/"
                + StringUtil.trimLeft(fileName, "/");
        if (!createFile(dir, fileName)) {
            return false;
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(path, appendMode);
            bw = new BufferedWriter(fw);

            if (appendMode) {
                bw.append(StringUtil.notNullString(content));
            } else {
                bw.write(StringUtil.notNullString(content));
            }

            bw.flush();
            bw.close();
            fw.close();
            return true;
        } catch (IOException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR,LOG_TAG, "createFile.1:"+e);
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                LogUtil.log(LogUtil.LogLevel.ERROR,LOG_TAG, "createFile.2:"+e);
            }
        }
    }

    /**
     * Check whether SDCard is available to access.
     *
     * @return true is SDCard is available or false
     */
    public static boolean isSDCardAvailableToAccess() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

    public static boolean close(Closeable c, String name) {

        try {
            if (c != null) {
                c.close();
            }
        } catch (IOException e) {
            LogUtil.log(LogUtil.LogLevel.ERROR,LOG_TAG, "Issue when closing " + name+e);
            return false;
        }

        return true;
    }


    /**
     * Get data from stream
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, LOG_TAG, e.toString());
        } finally {
            closeOutputStream(outStream);
            closeInputStream(inStream);

        }
        byte[] result = outStream.toByteArray();
        closeOutputStream(outStream);

        return result;
    }


    public static boolean saveFile(Bitmap bm, String saveFileName) {
        File dirFile = new File(saveFileName);
        BufferedOutputStream bos = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (!dirFile.exists()) {
                dirFile.createNewFile();
            }
            File myCaptureFile = new File(saveFileName);
            fileOutputStream = new FileOutputStream(myCaptureFile);
            bos = new BufferedOutputStream(fileOutputStream);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();

        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.ERROR, LOG_TAG, e.toString());
            return false;
        } finally {
            closeOutputStream(bos);
            closeOutputStream(fileOutputStream);
        }
        return true;
    }

    public static File getBlurFile() {
        String appDataPath = Environment.getExternalStorageDirectory().getPath();

        File bgFile = new File(appDataPath, BACKGROUND_BLUR_FILE_PATH);
        if (bgFile.exists()) {
            return bgFile;
        }
        return null;
    }


    /**
     * @param
     * @return
     */
    public static String readAssesFile() {

        StringBuilder readedStr = new StringBuilder();

        InputStream in = null;
        BufferedReader br = null;
        try {

            in = LibApplication.getContext().getResources().getAssets().open("android_Hplus_update_info.txt");
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tmp;

            while ((tmp = br.readLine()) != null) {
                readedStr.append(tmp);
            }
            br.close();
            in.close();

            return readedStr.toString();

        } catch (Exception e) {
            LogUtil.log(LogUtil.LogLevel.DEBUG,LOG_TAG, "readFile:"+e);
            LogUtil.log(LogUtil.LogLevel.ERROR, LOG_TAG, e.toString());
            return readedStr.toString();
        }
    }

    //从resources中的raw 文件夹中获取文件并读取数据
    public static String readFromRaw(int rawData) {
        StringBuilder readedStr = new StringBuilder();
        try {
            InputStream in = LibApplication.getContext().getResources().openRawResource(rawData);
            //获取文件的字节数
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tmp;

            while ((tmp = br.readLine()) != null) {
                readedStr.append(tmp);
                readedStr.append("\n");
            }
            br.close();
            in.close();

            return readedStr.toString();
        } catch (Exception e) {
            return readedStr.toString();
        }
    }


}
