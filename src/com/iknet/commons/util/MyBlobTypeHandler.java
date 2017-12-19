package com.iknet.commons.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * blob 转换
 * 
 * @author luozd
 * 
 */
public class MyBlobTypeHandler extends BaseTypeHandler<String> {
	// ###指定字符集
	private static final String DEFAULT_CHARSET = "utf-8";

	public void setNonNullParameter(PreparedStatement ps, int i,
			String parameter, JdbcType jdbcType) throws SQLException {
		ByteArrayInputStream bis = null;
		try {
			// ###把String转化成byte流
			if (parameter != null) {

				bis = new ByteArrayInputStream(
						parameter.getBytes(DEFAULT_CHARSET));
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Blob Encoding Error!");
		}
		if (bis != null) {

			ps.setBinaryStream(i, bis, parameter.length());
		}
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String blobRs = "";
		Blob blob = (Blob) rs.getBlob(columnName);
		byte[] returnValue = null;
		if (null != blob) {
			returnValue = blob.getBytes(1, (int) blob.length());
		}
		try {
			// ###把byte转化成string
			if (returnValue != null) {
				blobRs = new String(returnValue, DEFAULT_CHARSET);
			}
			// return new String(returnValue, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Blob Encoding Error!");
		}
		return blobRs;
	}

	public String getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String blobRs = "";
		Blob blob = (Blob) cs.getBlob(columnIndex);
		byte[] returnValue = null;
		if (null != blob) {
			returnValue = blob.getBytes(1, (int) blob.length());
		}
		try {
			if (returnValue != null) {
				blobRs = new String(returnValue, DEFAULT_CHARSET);
			}
			// return new String(returnValue, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Blob Encoding Error!");
		}
		return blobRs;
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}