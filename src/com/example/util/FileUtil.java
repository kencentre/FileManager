package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.viewpagerdemo.R;

public class FileUtil {
	Activity activity;
	/**
	 * 读取输入流
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(String path) throws IOException {
		// 根据路径创建文件对象
		File file = new File(path);
		// 判断文件是否存在
		if (!file.exists()) {// 不存在
			return "您要访问的文件不存在！".getBytes();
		} else {// 存在
				// 创建输入流对象
			FileInputStream inStream = new FileInputStream(file);
			// 创建字节输出流，用于将读取的数据写入输出流中
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// 创建字节缓存数组
			byte[] buffer = new byte[4096];
			// 保存是否读取完数据
			int len = 0;
			// 循环读取输入流的数据，并写入字节输出流中
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			// 关闭流对象
			outStream.close();
			inStream.close();
			return outStream.toByteArray();
		}
	}

	/**
	 * 往文件中写入数据的方法
	 * 
	 * @param path
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFile(String path, String content)
			throws IOException {
		// 根据路径创建File对象
		File file = new File(path);
		// 判断文件路径是否存在
		if (file.exists()) {// 存在
			// 创建文件输出流对象
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(content.getBytes());// 往文件写入数据
			// 关闭流对象
			outStream.close();
		} else {// 不存在
			throw new IOException("您要写入的文件不存在！");
		}
	}
	
	// 打开指定文件
		protected void openFile(File aFile) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			File file = new File(aFile.getAbsolutePath());
			// 取得文件名
			String fileName = file.getName();
			// 根据不同的文件类型来打开文件
			if (checkEndsWithInStringArray(fileName,
					activity.getResources().getStringArray(R.array.fileEndingImage))) {
				intent.setDataAndType(Uri.fromFile(file), "image/*");
			} else if (checkEndsWithInStringArray(fileName, activity.getResources()
					.getStringArray(R.array.fileEndingAudio))) {
				intent.setDataAndType(Uri.fromFile(file), "audio/*");
			} else if (checkEndsWithInStringArray(fileName, activity.getResources()
					.getStringArray(R.array.fileEndingVideo))) {
				intent.setDataAndType(Uri.fromFile(file), "video/*");
			}
			activity.startActivity(intent);
		}

		// 通过文件名判断是什么类型的文件
		private boolean checkEndsWithInStringArray(String checkItsEnd,
				String[] fileEndings) {
			for (String aEnd : fileEndings) {
				if (checkItsEnd.endsWith(aEnd))
					return true;
			}
			return false;
		}

}
