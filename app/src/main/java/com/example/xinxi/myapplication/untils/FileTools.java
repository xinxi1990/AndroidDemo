package com.example.xinxi.myapplication.untils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * @author xinxi
 * 读写文件工具类
 */


public class FileTools {

    private static final String TAG = "FileTools";

    /**
     * 读取文件
     *
     * @return
     */
    public void readfile(String path) {

        try {

            FileReader fr = new FileReader(path);
            BufferedReader r = new BufferedReader(fr);
            String result = "";
            String line = null;

            while ((line = r.readLine()) != null) {
                result += line + "\n";
            }

            Log.d(TAG, "SD卡文件里面的内容:" + result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个文件目录下所有文件
     *
     * @param foldername
     * @return 遍历的文件列表
     */
    public List getFiles(String foldername) {

        List list = new ArrayList();
        try {
            File file = new File(foldername);
            File[] array = file.listFiles();

            if (file.exists() && array != null && array.length != 0) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].isFile()) {
                        Log.d(TAG, "文件路径是:" + array[i].getPath());
                        list.add(array[i].getPath());

                    } else if (array[i].isDirectory()) {
                        getFiles(array[i].getPath());
                    }
                }
            }
        } catch (Throwable te) {
            te.printStackTrace();
        } finally {
            return list;
        }
    }


    /**
     * 删除某个文件目录下所有文件
     */

    public void delFiles(String filename) {
        File file = new File(filename);
        if (file.isFile() && file.exists()) {
            file.delete();
            if (!file.exists()) {
                Log.d(TAG, file + "删除成功");
            } else {
                Log.d(TAG, file + "删除失败");
            }
        } else {
            Log.d(TAG, file + "不存在");
        }
    }

    /**
     * 删除某个文件夹
     */

    public void deleteDirectory(String dirname) {
        File file = new File(dirname);

        if (file.isDirectory() && file.exists() && this.getFiles(dirname).size() != 0) {
            List filelist = this.getFiles(dirname);
            for (Object files : filelist) {
                this.delFiles(files.toString());
            }
            if ((this.getFiles(dirname).size() == 0)) {
                Log.d(TAG, dirname + "删除成功");
            } else {
                Log.d(TAG, dirname + "删除失败");
            }
        } else {

            Log.d(TAG, dirname + "不存在");
        }

    }


    /**
     * 获取行数
     */
    public int getfilenumber(String filename) throws IOException {
        int count = 0;
        File f = new File(filename);
        InputStream input = new FileInputStream(f);

        BufferedReader b = new BufferedReader(new InputStreamReader(input));
        String value = b.readLine();

        if (value != null)
            while (value != null) {
                count++;
                value = b.readLine();
            }

        b.close();
        input.close();
        //Log.d(TAG,filename + "的行数:" + count);
        return count;
    }


    /**
     * 获取某一关键字的行数
     */
    public int getlinenumber(String filename, String key) throws IOException {

        int count = 0;
        File f = new File(filename);
        InputStream input = new FileInputStream(f);
        BufferedReader b = new BufferedReader(new InputStreamReader(input));

        String value = b.readLine();
        if (value != null)
            while (value != null) {
                count++;
                value = b.readLine();

                if (value.startsWith(key)) {
                    value = null;
                }
            }

        b.close();
        input.close();
        //Log.d(TAG,key + "的行数:" + count);
        return count;
    }


    /**
     * 获取文件某几行内容
     */

    public String readlines(String filename, String key) throws IOException {

        String result = "";

        try {

            int startcount = this.getlinenumber(filename, key);

            int count = 0;
            File f = new File(filename);
            InputStream input = new FileInputStream(f);

            BufferedReader b = new BufferedReader(new InputStreamReader(input));

            String value = b.readLine();

            if (value != null)
                while (value != null) {

                    count++;
                    value = b.readLine();

                    if (count > startcount) {
                        if (value != null && value.trim() != "")
                            result += value + "\n";
                    } else {
                        continue;
                    }
                }
            b.close();
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Log.d(TAG,"最终拼接:" + result);
            return result;
        }
    }


    /**
     * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = "不适用";
        }
        return sdpath;

    }


}
