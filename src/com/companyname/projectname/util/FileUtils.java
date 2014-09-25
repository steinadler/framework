package com.companyname.projectname.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.companyname.projectname.log.Log;

/**
 * @author weinianjie
 * @date 2014年7月18日
 * @time 下午5:29:22
 */
public class FileUtils {
	
	public static void main(String[] args) {
		System.out.println(File.separator);
	}
	
	/**
	 * 删除文件
	 * @param fullname 文件全路径
	 * @return
	 */
	public static boolean delFile(String fullname) {
		File file = new File(fullname);
		if(file != null && file.isFile() && file.exists()) {
			return file.delete(); 
		}
		return false;
	}
	
	/**
	 * 获取指定目录下的所有子目录很文件
	 * @param dirname
	 * @return
	 */
	public static List<File> getAllFileAndDirectory(String dirname) {
		List<File> fileList = new ArrayList<File>();
		File dir = new File(dirname);
		if (null != dir && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (null != files && files.length > 0) {
				for (File file : files) {
					if (null != file) {
						fileList.add(file);
						if(file.isDirectory()) {
							fileList.addAll(getAllFileAndDirectory(file.getPath()));
						}
					}
				}
			}
		}
		return fileList;
	}
	
	/**
	 * 目录下文件查找，不含子目录
	 * @param dirname
	 * @param extName
	 * @return
	 */
	public static List<File> searchFile(String dirname, String extName) {
		List<File> fileNameList = new ArrayList<File>();
		List<File> fileList = new ArrayList<File>();
		File dir = new File(dirname);
		if (null != dir && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (null != files && files.length > 0) {
				for (File file : files) {
					if (null != file && file.isFile()) {
						if(extName != null && !extName.equals("")) {
							if (file.getName().toLowerCase().endsWith(extName.toLowerCase())) {
								fileNameList.add(file);
							}
						}else{
							fileNameList.add(file);
						}
					}
				}

				// 排序开始
				File[] targetFiles = new File[fileNameList.size()];
				for (int i = 0; i < fileNameList.size(); i++) {
					targetFiles[i] = fileNameList.get(i);
				}

				File fileTemp = null;
				for (int i = 0; i < targetFiles.length; i++) {
					for (int j = i; j < targetFiles.length; j++) {
						long diff = targetFiles[i].lastModified()
								- targetFiles[j].lastModified();
						if (diff < 0) {
							fileTemp = targetFiles[i];
							targetFiles[i] = targetFiles[j];
							targetFiles[j] = fileTemp;
						}
					}
				}

				fileList = new ArrayList<File>();
				for (int i = 0; i < targetFiles.length; i++) {
					fileList.add(targetFiles[i]);
				}

			}
		}
		return fileList;
	}
	
	public static List<File> searchFile(String dirname) {
		return searchFile(dirname, null);
	}
	

	/**
	 * 断点下载的方式打包目录给用户
	 * @param request
	 * @param response
	 * @param path
	 * @param dirName
	 * @param afterTarSize 打包压缩后的大小，可选参数，不提供将从算
	 */
	public static void downloadDirToClient(HttpServletRequest request,  HttpServletResponse response, String path, String dirName, long afterTarSize) {
		Integer READER_BUFFER_SIZE = 8192;
		
		InputStream in = null;
		OutputStream out = null;
		long readed = 0L;
		
		try{
			
			// 获取tar压缩后的大小
			long fileLenth = afterTarSize;
			if(fileLenth == 0) {
				// 以前没算过tar大小的就要计算
				fileLenth = getTarSize(path, dirName);
				if(fileLenth != 0) {
					// TODO 持久化到数据库下次下载的时候免算
				}
			}
			if(fileLenth == 0) {
				return;
			}
			
			// 获取上次下载的位置
			if (request.getHeader("Range") != null) {
				response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
				readed = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
			}
			
			// 设置HTTP响应头
			StringBuffer contentRangeTemp = new StringBuffer("bytes ");
			contentRangeTemp.append(new Long(readed).toString()).append("-");
			contentRangeTemp.append(new Long(fileLenth - 1).toString()).append("/");
			contentRangeTemp.append(new Long(fileLenth).toString());
			String contentRange = contentRangeTemp.toString();
			String downloadName = dirName;
			response.setContentType("application/x-gzip");
			response.setHeader("Content-Disposition","attachment;filename=" + downloadName + ".tar");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Range", contentRange);
			response.setHeader("Content-Length", fileLenth + "");
			
	        // 边压缩边下载
			String[] cmd = new String[] { "/bin/sh", "-c", " cd " + path + " && tar -c " + dirName};
			Process ps = Runtime.getRuntime().exec(cmd);
			in = ps.getInputStream();
			byte[] buf = new byte[READER_BUFFER_SIZE];
			out = response.getOutputStream();
			int i = 0;
			long oldByte = 0;
			//下载
			while ((i = in.read(buf)) != -1) {
				oldByte += i;
				if (readed != 0 && (oldByte - READER_BUFFER_SIZE) >= readed) {
					out.write(buf, 0, i);
					out.flush();
				} else {
					out.write(buf, 0, i);
					out.flush();
				}
			}
			ps.waitFor();
			
		}catch(Exception e) {
			Log.printStackTrace(e);
		}finally{
			try {
				if(in!=null)
				in.close();
				if(out!=null)
				{
				out.flush();
				out.close();
				}
			} catch (IOException ee) {
				Log.printStackTrace(ee);
			}
		}
	}
	
	/**
	 * 计算文件或者目录tar压缩以后的大小
	 * @param path
	 * @param fileName，可以是文件也可以是目录
	 * @return
	 * @throws IOException
	 */
	private static long getTarSize(String path, String fileName) throws IOException {
		long len = 0;
		String[] cmd = new String[] { "/bin/sh", "-c",
				" cd " + path + " && tar -c " + fileName + " | wc -c" };
		Process process = Runtime.getRuntime().exec(cmd);
		BufferedReader reader = null;
		String line;
		try {
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = reader.readLine()) != null) {
				len = Long.parseLong(line);
			}
			process.waitFor();
		} catch (Exception e) {
			Log.printStackTrace(e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return len;
	}
}
