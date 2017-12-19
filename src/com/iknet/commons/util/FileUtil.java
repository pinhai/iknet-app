/**
 * 
 */
package com.iknet.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author luozd
 * 
 */
public class FileUtil {

	private static Logger log = Logger.getLogger(FileUtil.class);

	/**
	 * 根据时间升序排序
	 * 
	 * @author lyx Aug 6, 2010
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static class CompratorByLastModified implements Comparator {
		// 根据修改时间 升序排序
		public int compare(Object o1, Object o2) {

			File file1 = (File) o1;

			File file2 = (File) o2;

			long diff = file1.lastModified() - file2.lastModified();

			if (diff > 0)

				return 1;

			else if (diff == 0)

				return 0;

			else

				return -1;

		}
	}

	/**
	 * // * 返回指定目录下所有文件名 // * // * @param absolutPath // * @return //
	 */
	// public static String[] getAllFileName(String absolutPath) {
	// List<String> names = new ArrayList<String>();
	// File[] files = getAllFile(absolutPath);
	//
	// if (files != null) {
	// for (File f : files) {
	// names.add(f.getAbsolutePath());
	// }
	// }
	//
	// return names.toArray(new String[] {});
	// }

	/**
	 * <p>
	 * 创建目录
	 * </p>
	 * 
	 * <pre>
	 * 如果目录已存在，则直接返回true
	 * 如果目录不存在，则创建
	 * </pre>
	 * 
	 * @param filePath
	 *            目录路径
	 * @return boolean true:创建成功;false:创建失败
	 */
	public static boolean createFileDir(String filePath) {
		File path = new File(filePath);

		return createFileDir(path);
	}

	/**
	 * <p>
	 * 创建目录
	 * </p>
	 * 
	 * <pre>
	 * 如果目录已存在，则直接返回true
	 * 如果目录不存在，则创建
	 * </pre>
	 * 
	 * @param file
	 *            目录文件
	 * @return boolean true:创建成功;false:创建失败
	 */
	public static boolean createFileDir(File file) {
		if (file.exists()) {
			return true;
		} else {
			return file.mkdirs();
		}
	}

	/**
	 * <p>
	 * 移动文件
	 * </p>
	 * 
	 * <pre>
	 * 如果目标文件存在，将覆盖目标文件。
	 * </pre>
	 * 
	 * @param file
	 *            源文件
	 * @param newFileDir
	 *            目标目录
	 * @return boolean true:移动文件成功；flase:移动文件失败
	 */
	public static boolean moveFile(File file, String newFileDir) {
		return moveFile(file, newFileDir, file.getName());
	}

	/**
	 * <p>
	 * 移动文件
	 * </p>
	 * 
	 * <pre>
	 * 如果目标文件存在，将覆盖目标文件。
	 * </pre>
	 * 
	 * @param file
	 *            源文件
	 * @param newFileDir
	 *            目标目录
	 * @param newFileName
	 *            新文件名
	 * @return boolean true:移动文件成功；flase:移动文件失败
	 */
	public static boolean moveFile(File file, String newFileDir,
			String newFileName) {
		return moveFileWithALLPath(file, newFileDir + File.separator
				+ newFileName);
	}

	/**
	 * <p>
	 * 移动文件
	 * </p>
	 * 
	 * <pre>
	 * 根据指定的完整的文件路径及文件名移动文件
	 * </pre>
	 * 
	 * @param file
	 * @param newFilePath
	 *            文件路径及文件名
	 * @return
	 */
	public static boolean moveFileWithALLPath(File file, String newFilePath) {
		if (file != null && createFileDir(newFilePath)) {
			File newFile = new File(newFilePath);
			if (newFile.exists()) {
				newFile.delete();
			}
			return file.renameTo(new File(newFilePath));
		} else {
			return false;
		}
	}

	public static boolean deleteFile(String newFilePath) {
		if (createFileDir(newFilePath)) {
			File newFile = new File(newFilePath);
			if (newFile.exists()) {
				newFile.delete();
			}
			return true;
		} else {
			return false;
		}
	}

	// public static void copyFile2(File originFile, File newFile,
	// String dataContent) throws IOException {
	// FileWriter fileWriter = null;
	// FileReader filereader = null;
	// BufferedWriter bfw = null;
	// BufferedReader bfr = null;
	//
	// try {
	// fileWriter = new FileWriter(newFile);
	// bfw = new BufferedWriter(fileWriter);
	//
	// filereader = new FileReader(originFile);
	// bfr = new BufferedReader(filereader);
	// String str = bfr.readLine();
	// while (true) {
	// int index = str.indexOf("<DATACONTENT>");
	// if (index != -1) {
	// str = str.substring(0, index);
	// bfw.write(str);
	// bfw.newLine();
	// break;
	// } else {
	// bfw.write(str);
	// bfw.newLine();
	// str = bfr.readLine();
	// str = (str == null ? "" : str);
	// }
	// }
	// bfw.write("<DATACONTENT>");
	// bfw.write(dataContent);
	// bfw.write("</DATACONTENT></MESSAGE></MSG_PACK></MSG_SEND_DOC>");
	// } finally {
	// if (bfr != null) {
	// bfr.close();
	// }
	//
	// if (filereader != null) {
	// filereader.close();
	// }
	//
	// if (bfw != null) {
	// bfw.close();
	// }
	//
	// if (fileWriter != null) {
	// fileWriter.close();
	// }
	// }
	//
	// }

	/**
	 * 复制文件
	 * 
	 * @param originFile
	 * @param newFile
	 * @throws IOException
	 * 
	 */
	public static void copyFile(File originFile, File newFile)
			throws IOException {
		FileInputStream fromFile = null;
		FileOutputStream toFile = null;
		try {
			byte[] cache = new byte[1024];
			fromFile = new FileInputStream(originFile);
			toFile = new FileOutputStream(newFile);
			while (fromFile.read(cache) != -1) {
				toFile.write(cache);
			}
		} finally {
			if (fromFile != null) {
				try {
					fromFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (toFile != null) {
				try {
					toFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void stringSave2File(String data, String fileName)
			throws IOException {
		FileWriter fwriter = null;
		try {
			fwriter = new FileWriter(fileName);
			fwriter.write(data);
		} finally {
			if (fwriter != null) {
				fwriter.flush();
				fwriter.close();
			}
		}
	}

	/**
	 * 通过Dom4j获取xml文档对象模型
	 * 
	 * @param xmlFile
	 * @return
	 * @throws DocumentException
	 */
	public static Document dom4jPraseXmlBySAX(File xmlFile)
			throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlFile);
		return document;
	}

	/**
	 * 通过Dom4j获取xml文档对象模型
	 * 
	 * @param xmlFile
	 * @return
	 * @throws DocumentException
	 */
	public static Document dom4jPraseXmlBySAX(File xmlFile,
			Map<String, String> map) throws DocumentException {
		SAXReader reader = new SAXReader();
		if (map != null) {
			// 设置DocumentFactory()的命名空间 setXPathNamespaceURIs
			reader.getDocumentFactory().setXPathNamespaceURIs(map);
		}
		Document document = reader.read(xmlFile);
		return document;
	}

	// /**
	// * 将fileName 文件的后缀名转换成 fileType 并返回 转换后的文件名
	// *
	// * @param fileName
	// * @param fileType
	// * @return
	// */
	// public static String getFileTypeReplaceFileNameType(String fileName,
	// String fileType) {
	// String newFileName = "";
	// fileName = fileName.trim();
	//
	// StringBuffer fileNameBuffer = new StringBuffer();
	// String fileNameType = ".";
	// int lastIndex = fileName.lastIndexOf(fileNameType);
	//
	// if (fileName == null || fileName.length() <= 1) {
	// // 当文件名为null或者为.或者无 后缀名的时候 将当前时间作为其 文件名
	// fileNameBuffer.append(DateUtil.getNowDate());
	// }
	//
	// if (lastIndex > -1) {
	// fileNameType = fileName.substring(lastIndex, fileName.length());
	// if (fileNameType.length() > 1) {
	// String fileNameNew = fileName
	// .replaceAll(fileNameType, fileType);
	// fileNameBuffer.append(fileNameNew);
	// } else {
	// String newfileName = fileName.substring(0,
	// fileName.length() - 1);
	// fileNameBuffer.append(newfileName);
	// fileNameBuffer.append(fileType);
	// }
	// } else {
	// fileNameBuffer.append(fileName);
	// fileNameBuffer.append(fileType);
	// }
	//
	// newFileName = fileNameBuffer.toString();
	//
	// return newFileName;
	// }

	/**
	 * 写XML文件
	 * 
	 * @param document
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeXML(Document document, String fileName)
			throws IOException {
		XMLWriter writer = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			// // 美化格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			writer = new XMLWriter(out, format);
			// writer = new XMLWriter(out);
			writer.write(document);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug(ex.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
				writer = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

	// /**
	// * 写XML文件
	// *
	// * @param document
	// * @param fileName
	// * @throws IOException
	// */
	// public static void writeXML(Document document, String fileName,
	// String encoding) throws IOException {
	// XMLWriter writer = null;
	// FileOutputStream out = null;
	// try {
	// out = new FileOutputStream(fileName);
	// // // 美化格式
	// OutputFormat format = OutputFormat.createPrettyPrint();
	// if (!StringUtil.isEmpty(encoding)) {
	// format.setEncoding(encoding);
	// }
	//
	// writer = new XMLWriter(out, format);
	// // writer = new XMLWriter(out);
	// writer.write(document);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// log.debug(ex.getMessage());
	// } finally {
	// if (writer != null) {
	// writer.close();
	// writer = null;
	// }
	// if (out != null) {
	// out.close();
	// out = null;
	// }
	// }
	// }

	/**
	 * 计算 文件大小
	 * 
	 * @param filesize
	 * @return
	 */
	public static String convertFileSize(long filesize) {
		// bug 5.09M 显示5.9M
		String strUnit = "Bytes";
		String strAfterComma = "";
		int intDivisor = 1;
		if (filesize >= 1024 * 1024) {
			strUnit = "MB";
			intDivisor = 1024 * 1024;
		} else if (filesize >= 1024) {
			strUnit = "KB";
			intDivisor = 1024;
		}
		if (intDivisor == 1)
			return filesize + " " + strUnit;
		strAfterComma = "" + 100 * (filesize % intDivisor) / intDivisor;
		if (strAfterComma == "")
			strAfterComma = ".0";
		return filesize / intDivisor + "." + strAfterComma + " " + strUnit;
	}

	public static void main(String args[]) {
		// int filesize = 10240000;
		// 5M
		int filesize = 5012000;
		System.err.println(convertFileSize(filesize));
	}
	// /**
	// * <p>
	// * 创建目录
	// * </p>
	// *
	// * <pre>
	// * 如果目录已存在，则直接返回true
	// * 如果目录不存在，则创建
	// * </pre>
	// *
	// * @param filePath
	// * 目录路径
	// * @return boolean true:创建成功;false:创建失败
	// */
	// public static boolean createFileDir(String filePath) {
	// File path = new File(filePath);
	//
	// return createFileDir(path);
	// }
	//
	// /**
	// * <p>
	// * 创建目录
	// * </p>
	// *
	// * <pre>
	// * 如果目录已存在，则直接返回true
	// * 如果目录不存在，则创建
	// * </pre>
	// *
	// * @param file
	// * 目录文件
	// * @return boolean true:创建成功;false:创建失败
	// */
	// public static boolean createFileDir(File file) {
	// if (file.exists()) {
	// return true;
	// } else {
	// return file.mkdirs();
	// }
	// }
	//
	// /**
	// * <p>
	// * 移动文件
	// * </p>
	// *
	// * <pre>
	// * 如果目标文件存在，将覆盖目标文件。
	// * </pre>
	// *
	// * @param file
	// * 源文件
	// * @param newFileDir
	// * 目标目录
	// * @return boolean true:移动文件成功；flase:移动文件失败
	// */
	// public static boolean moveFile(File file, String newFileDir) {
	// return moveFile(file, newFileDir, file.getName());
	// }
	//
	// /**
	// * <p>
	// * 移动文件
	// * </p>
	// *
	// * <pre>
	// * 如果目标文件存在，将覆盖目标文件。
	// * </pre>
	// *
	// * @param file
	// * 源文件
	// * @param newFileDir
	// * 目标目录
	// * @param newFileName
	// * 新文件名
	// * @return boolean true:移动文件成功；flase:移动文件失败
	// */
	// public static boolean moveFile(File file, String newFileDir,
	// String newFileName) {
	// return moveFileWithALLPath(file, newFileDir + File.separator
	// + newFileName);
	// }
	//
	// /**
	// * <p>
	// * 移动文件
	// * </p>
	// *
	// * <pre>
	// * 根据指定的完整的文件路径及文件名移动文件
	// * </pre>
	// *
	// * @param file
	// * @param newFilePath
	// * 文件路径及文件名
	// * @return
	// */
	// public static boolean moveFileWithALLPath(File file, String newFilePath)
	// {
	// if (file != null && createFileDir(newFilePath)) {
	// File newFile = new File(newFilePath);
	// if (newFile.exists()) {
	// newFile.delete();
	// }
	// return file.renameTo(new File(newFilePath));
	// } else {
	// return false;
	// }
	// }
	//
	// /**
	// * <p>
	// * 返回指定目录下所有文件
	// * </p>
	// *
	// * <pre>
	// * 取得指定目录下所有文件。
	// * 如果参数为空，则返回NULL
	// * 如果目录不存在，则创建并返回空数组
	// * </pre>
	// *
	// * @param absolutPath
	// * 文件目录
	// * @return File[] 文件列表
	// */
	// public static File[] getAllFile(String absolutPath) {
	// if (StringUtil.isEmpty(absolutPath)) {
	// return null;
	// }
	// File files = new File(absolutPath);
	// if (createFileDir(files)) {
	// return files.listFiles(new FileFilter() {
	//
	// public boolean accept(File file) {
	// return file.isFile();
	// }
	//
	// });
	// } else {
	// return new File[0];
	// }
	// }
}
