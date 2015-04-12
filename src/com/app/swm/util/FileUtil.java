package com.app.swm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;

/**
 * 文件工具类
 * 
 */
public class FileUtil {

	/**
	 * 检验SDcard状态
	 * 
	 * @return boolean
	 */
	public static boolean checkSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static double getDirSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {// 如果是文件则直接返回其大小,以“兆”为单位
				double size = (double) file.length() / 1024 / 1024;
				return size;
			}
		} else {
			return 0.0;
		}
	}

	// 新建文件夹
	public static boolean makeDir(String path) {

		if (!checkSDCard()) {

			return false;
		}
		File file = new File(path);
		if (!file.exists()) {

			file.mkdirs();// 创建文件夹
		}
		return true;
	}

	/** 删除文件 **/
	public static boolean deleteFile(File f) {

		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; ++i) {
					deleteFile(files[i]);
				}
			}
		}
		return f.delete();
	}

	// 判断文件是否存在
	public static boolean fileExit(String path) {

		File file = new File(path);
		if (!file.exists()) {

			return false;
		}
		return true;
	}

	/**
	 * @Description save bitmap to file
	 * @param dirPath
	 * @param filename
	 * @param bitmap
	 * @return File
	 */
	public static File saveBitmap(String dirPath, String filename, Bitmap bitmap) {

		makeDir(dirPath);
		File file = new File(dirPath + filename);
		FileOutputStream fOut = null;
		try {
			file.createNewFile();
			fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			file = null;
			e1.printStackTrace();
		} finally {
			if (fOut != null) {
				try {
					fOut.flush();
					fOut.close();
					bitmap.recycle();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return file;
	}
}
