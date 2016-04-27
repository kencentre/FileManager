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
	 * ��ȡ������
	 * 
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(String path) throws IOException {
		// ����·�������ļ�����
		File file = new File(path);
		// �ж��ļ��Ƿ����
		if (!file.exists()) {// ������
			return "��Ҫ���ʵ��ļ������ڣ�".getBytes();
		} else {// ����
				// ��������������
			FileInputStream inStream = new FileInputStream(file);
			// �����ֽ�����������ڽ���ȡ������д���������
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			// �����ֽڻ�������
			byte[] buffer = new byte[4096];
			// �����Ƿ��ȡ������
			int len = 0;
			// ѭ����ȡ�����������ݣ���д���ֽ��������
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			// �ر�������
			outStream.close();
			inStream.close();
			return outStream.toByteArray();
		}
	}

	/**
	 * ���ļ���д�����ݵķ���
	 * 
	 * @param path
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFile(String path, String content)
			throws IOException {
		// ����·������File����
		File file = new File(path);
		// �ж��ļ�·���Ƿ����
		if (file.exists()) {// ����
			// �����ļ����������
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(content.getBytes());// ���ļ�д������
			// �ر�������
			outStream.close();
		} else {// ������
			throw new IOException("��Ҫд����ļ������ڣ�");
		}
	}
	
	// ��ָ���ļ�
		protected void openFile(File aFile) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			File file = new File(aFile.getAbsolutePath());
			// ȡ���ļ���
			String fileName = file.getName();
			// ���ݲ�ͬ���ļ����������ļ�
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

		// ͨ���ļ����ж���ʲô���͵��ļ�
		private boolean checkEndsWithInStringArray(String checkItsEnd,
				String[] fileEndings) {
			for (String aEnd : fileEndings) {
				if (checkItsEnd.endsWith(aEnd))
					return true;
			}
			return false;
		}

}
