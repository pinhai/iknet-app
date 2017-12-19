package com.iknet.commons.util;

import org.apache.log4j.Logger;

/**
 * 
 * @author luozd
 * 
 */
public class BaseService {
	private Logger log = Logger.getLogger(this.getClass());

	protected String getIknetId() {
		String SeqNoId = SequenceUtil.getSyncSeqNoId();
		log.debug("getIknetId-->SeqNoId:" + SeqNoId);
		return SeqNoId;
	}
}
