/**
 * 
 */
package com.iknet.commons.util;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

/**
 * 生成ID
 * 
 * @author luozd
 * 
 */
public class SequenceUtil {
	private static Logger log = Logger.getLogger(SequenceUtil.class);
//	private static String personName_Fix = "宝宝";
	private static String personName_Fix = "请改昵称";
	// private static ResourceFile configResourceFile =
	// SystemParameterConstant.configResourceFile;
	// SeqNo 用于 区分 不同 PC【最后指出 4位 数字 或者字母】
	// private static String SeqNo = "";
	private static int num = 15;

	private static int lef_num = 15;

	private static int right_num = 17;

	private static int no_num = 11;

	private static int no_lef_num = 15;

	private static int no_right_num = 5;

	private static int MedicalRecordNo_num = 6;

	private static int MedicalRecordNo_lef_num = 6;

	private static int MedicalRecordNo_right_num = 6;

	// 顺序号
	public static int orderNum = 1;

	public static String lastTimeStamp = "";

	public static DecimalFormat numFormat = new DecimalFormat("0000");

	private static int loginNo_Default_length = 3;

	// static {
	// try {
	// log.debug("configResourceFile:" + configResourceFile);
	// SeqNo = configResourceFile
	// .getPropertyValue(SystemParameterConstant.SeqNo);
	// log.debug("SeqNo:" + SeqNo);
	// } catch (Exception e) {
	// log.error("SeqNo 未 配置  :" + LogUtil.logFormat(e));
	// }
	// }

	private final static String initDateFormate = "yyMMddHHmmssS";

	private final static String initDateFormate_yymmdd = "yyMMdd";

	private final static String initDateFormate_yy = "yy";

	private final static String initDateFormate_MM = "MM";

	private final static String initDateFormate_dd = "dd";

	private final static String initDateFormate_HH = "HH";

	// private final static String initDateFormate_mm = "mm";

	private final static String initDateFormate_ssS = "mmssS";

	/**
	 * 全局 ID
	 * 
	 * <pre>
	 * 当前系统时间 精确到 0.001 毫秒 【15位】+SeqNo【最多4位】+随机数【最多11位】
	 * </pre>
	 * 
	 * @return
	 */
	public synchronized static String getSyncSeqNoId() {

		String id = "";
		String initDate = DateUtil.getNow(initDateFormate);

		StringBuffer iDBuffer = new StringBuffer();

		iDBuffer.append(EasyStr.initZeroRight(initDate, lef_num));
		if (num >= right_num) {
			num = right_num;
		}
		iDBuffer.append(EasyStr.initRandomByLength(num));
		id = iDBuffer.toString();
		log.debug("id:" + id);

		return id;

	}

	/**
	 * 全局 ID
	 * 
	 * <pre>
	 * 当前系统时间 精确到 0.001 毫秒 【15位】+随机数【最多11位】
	 * </pre>
	 * 
	 * @return
	 */
	public synchronized static String getSyncSeqNo() {

		String id = "";
		String initDate = DateUtil.getNow(initDateFormate);

		StringBuffer iDBuffer = new StringBuffer();

		iDBuffer.append(EasyStr.initZeroRight(initDate, no_lef_num));
		if (no_num >= no_right_num) {
			no_num = no_right_num;
		}
		iDBuffer.append(EasyStr.initRandomByLength(no_num));
		id = iDBuffer.toString();
		log.debug("id:" + id);

		return id;

	}

	/**
	 * 全局 病例号码
	 * 
	 * <pre>
	 * 当前系统时间 精确到 0.001 毫秒 【6位】+SeqNo【最多4位】+随机数【最多6位】
	 * </pre>
	 * 
	 * @return
	 */
	public synchronized static String getSyncMedicalRecordNo() {

		String id = "";
		String initDate = DateUtil.getNow(initDateFormate_yymmdd);

		StringBuffer iDBuffer = new StringBuffer();

		iDBuffer.append(EasyStr
				.initZeroRight(initDate, MedicalRecordNo_lef_num));
		if (MedicalRecordNo_num >= MedicalRecordNo_right_num) {
			MedicalRecordNo_num = MedicalRecordNo_right_num;
		}
		iDBuffer.append(EasyStr.initRandomByLength(MedicalRecordNo_num));
		id = iDBuffer.toString();
		log.debug("id:" + id);

		return id;

	}

	// private final static String initDateFormate_yyyyMMddHHmmss =
	// "yyMMddHHmmss";

	// public synchronized static String getSeqNo() {
	// String timeStamp = DateUtil.getNow(initDateFormate_yyyyMMddHHmmss);
	// if (timeStamp.equals(lastTimeStamp)) {
	// orderNum += 1;
	// } else {
	// lastTimeStamp = timeStamp;
	// orderNum = 1;
	// }
	// return lastTimeStamp + numFormat.format(orderNum);
	// }

	/**
	 * 系统 生成登录名
	 * 
	 * @param loginSource
	 * @return
	 */
	public synchronized static String getSyncLoginNo(String loginSource) {

		String loginNo = "";
		String initDate_yy = DateUtil.getNow(initDateFormate_yy);
		String initDate_MM = DateUtil.getNow(initDateFormate_MM);
		String initDate_dd = DateUtil.getNow(initDateFormate_dd);
		String initDate_HH = DateUtil.getNow(initDateFormate_HH);
		String initDate_ssS = DateUtil.getNow(initDateFormate_ssS);

		StringBuffer loginNoBuffer = new StringBuffer();

		loginNoBuffer.append(loginSource);

		loginNoBuffer.append(initDate_yy);
		loginNoBuffer.append(EasyStr.initMonthTo(initDate_MM));
		loginNoBuffer.append(EasyStr.initDayTo(initDate_dd));
		loginNoBuffer.append(EasyStr.initHourTo(initDate_HH));
		loginNoBuffer.append(initDate_ssS);

		loginNoBuffer
				.append(EasyStr.initRandomByLength(loginNo_Default_length));

		loginNo = loginNoBuffer.toString();
		log.debug("loginNo:" + loginNo);

		return loginNo;

	}

	/**
	 * 系统 生成登录名
	 * 
	 * @param loginSource
	 * @return
	 */
	public synchronized static String getSyncPersonName() {

		String personName = "";
		String personName_Pre = DateUtil.getNow(initDateFormate_yymmdd);

		StringBuffer personNameBuffer = new StringBuffer();

		personNameBuffer.append(personName_Pre);

		personNameBuffer.append(personName_Fix);

		personName = personNameBuffer.toString();
		log.debug("personName:" + personName);

		return personName;

	}

	public static void main(String args[]) {
		String loginSource = "T";
		String loginNo = SequenceUtil.getSyncLoginNo(loginSource);
		System.err.println("loginNo:" + loginNo + "--->:" + loginNo.length());

		System.err.println("getSyncPersonName:" + getSyncPersonName());
		// for (int i = 0; i < 10; i++)
		// {
		// String id = SequenceUtil.getSyncMedicalRecordNo();
		// // if (id.length() != 28) {
		// //
		// // }
		// // 14 10 15 10 19 52 814 364088055720730
		// System.out.println("id.length():" + id.length() + "--> i:" + i
		// + "<-->  id:" + id);
		// }

	}
}
