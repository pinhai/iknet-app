package com.iknet.commons.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.iknet.commons.baseCode.BusiConstants.System_Flag;

public class IKnetUtil {

	public static JsonBinder binder = JsonBinder.getInstance();

	private static Logger log = Logger.getLogger(IKnetUtil.class);

	/**
	 * 移除 session 中的 key
	 * 
	 * @param objectKey
	 */
	public static void removeAttributeFromSession(String objectKey) {
		log.debug("removeAttributeFromSession");
		log.debug("objectKey:" + objectKey);
		if (EasyStr.isNotEmpty(objectKey)) {

			RequestContextHolder.getRequestAttributes().removeAttribute(
					objectKey, RequestAttributes.SCOPE_SESSION);
		}

	}

	/**
	 * key _Object 存入 session
	 * 
	 * @param objectKey
	 * @param object
	 */
	public static void setObjectToSession(String objectKey, Object object) {
		log.debug("setObjectToSession");
		log.debug("objectKey:" + objectKey);
		log.debug("object:" + object);
		if (object != null) {
			if (EasyStr.isNotEmpty(objectKey)) {
				log.debug("objectKey:" + objectKey + "<--->object:" + object);
				RequestContextHolder.getRequestAttributes().setAttribute(
						objectKey, object, RequestAttributes.SCOPE_SESSION);
			}

		}

	}

	/**
	 * key _Object 存入 session
	 * 
	 * @param objectKey
	 * @param object
	 */
	public static Object getObjectFromSession(String objectKey) {
		log.debug("getObjectFromSession");
		log.debug("objectKey:" + objectKey);
		Object object = null;
		if (EasyStr.isNotEmpty(objectKey)) {
			object = RequestContextHolder.getRequestAttributes().getAttribute(
					objectKey, RequestAttributes.SCOPE_SESSION);
			log.debug("objectKey:" + objectKey + "<----->-----object:" + object);
		}
		log.debug("object:" + object);
		return object;
	}

	/**
	 * 初始化 查询 过滤 条件 map
	 * 
	 * @return
	 */
	public static Map<String, Object> getQueryMap() {
		Map<String, Object> queryMap = new HashMap<String, Object>();

		return queryMap;
	}

	/**
	 * 初始化 返回 map
	 * 
	 * @return
	 */
	public static Map<String, Object> getResponseMap() {
		Map<String, Object> map = new HashMap<String, Object>();

		return map;
	}

	/**
	 * appJsonValue To Map
	 * 
	 * @param appJsonValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> appJsonValueToMap(String appJsonValue) {
		Map<String, Object> map = binder.fromJson(appJsonValue, Map.class);

		return map;
	}

	/**
	 * appJsonValue To Object
	 * 
	 * @param appJsonValue
	 * @return
	 */
	public static String[] appJsonValueToString(String appJsonValue) {
		String[] arr = null;
		if (!System_Flag.Split_Null_Arr.equalsIgnoreCase(appJsonValue)) {
			arr = binder.fromJson(appJsonValue, String[].class);
		}
		log.debug("arr:" + arr);
		return arr;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonDataToMap(String jsonData) {
		jsonData = EasyStr.strToTrim(jsonData);
		Map<String, Object> jsonDataMap = null;
		if (EasyStr.isNotEmpty(jsonData)) {

			ObjectMapper mapper = new ObjectMapper();
			try {
				jsonDataMap = mapper.readValue(jsonData, Map.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				e.printStackTrace();
			}

		}
		log.debug("jsonDataMap:" + jsonDataMap);
		return jsonDataMap;
	}

//	public static void main(String[] args) {
//		String appJsonValue="[\"150211150121602518093882619234\"]";
//		String[] personIdArr = (String[]) IKnetUtil
//				.appJsonValueToString(appJsonValue);
//		System.err.println("personIdArr:" + personIdArr);
//	}
	// String s = "[]";
	// System.err.println("s-------------->"+appJsonValueToLong(s));
	// // String appJsonValue="[10000,12]";
	// // Object object = binder.fromJson(appJsonValue, Long[].class);
	// // Long[] arr=(Long[])object;
	// // for (int i = 0; i < arr.length; i++) {
	// // System.err.println("arr["+i+"]:"+arr[i]);
	// // }
	// // System.err.println("object:"+object);
	// // // // String appJson =
	// // // //
	// // //
	// //
	// "\"appBaiKeType\": [{\"id\": 7,\"typeName\": \"糖尿病\",\"typeDesc\": \"糖尿病\",\"sortIndex\": 1,\"isShow\": \"1\",\"baiKeType\": \"A\",\"baiKeTypeImgUrl\": \"/BaiKe/A.jpg\"},{\"id\": 6,\"typeName\": \"高血压\",\"typeDesc\": \"高血压\",\"sortIndex\": 2,\"isShow\": \"1\",\"baiKeType\": \"B\",\"baiKeTypeImgUrl\": \"/BaiKe/B.jpg\"}]}";
	// // // //
	// // // String
	// // //
	// //
	// appjson_test="{\"appBatchJson\":[{\"personId\":12,\"checkResult\":\"1\"},{\"personId\":12,\"checkResult\":\"0\"}]}";
	// // // String appJson =
	// // //
	// //
	// " { \"medicalRecord\":[{\"personId\":\"9\",\"checkShrink\":\"80\",\"checkDiastole\":\"180\",\"checkHeartRate\":\"100\",\"equipType\":\"1\",\"checkResult\":\"0\"},{\"personId\":\"9\",\"checkShrink\":\"80\",\"checkDiastole\":\"180\",\"checkHeartRate\":\"100\",\"equipType\":\"1\",\"checkResult\":\"0\"}]}";
	// // //
	// // // System.err.println("appJson:" + appjson_test);
	// // //
	// // // // ObjectMapper objectMapper = new ObjectMapper();
	// // //
	// // // // Map<String, Object> map1=null;
	// // // // try
	// // // // {
	// // // // map1 = objectMapper.readValue( appJson, Map.class);
	// // // // }
	// // // // catch (JsonParseException e)
	// // // // {
	// // // // // TODO Auto-generated catch block
	// // // // e.printStackTrace();
	// // // // }
	// // // // catch (JsonMappingException e)
	// // // // {
	// // // // // TODO Auto-generated catch block
	// // // // e.printStackTrace();
	// // // // }
	// // // // catch (IOException e)
	// // // // {
	// // // // // TODO Auto-generated catch block
	// // // // e.printStackTrace();
	// // // // }
	// // // //
	// // // // System.err.println("map1:"+map1);
	// // // //
	// // //
	// // // Map<String, ?> map = binder.fromJson(appjson_test, Map.class);
	// // //
	// // // System.err.println("map:" + map);
	// // //
	// // // List<?> list = (List<?>) map.get("appBatchJson");
	// // // System.err.println("list:" + list);
	// // //
	// // // for (int i = 0; i < list.size(); i++)
	// // // {
	// // // String ss = binder.toJson(list.get(i));
	// // // System.err.println("---------->" + list.get(i));
	// // //
	// // // AppMedicalRecord map1 = binder.fromJson(ss,
	// // AppMedicalRecord.class);
	// // // System.err.println("map1:" + map1.getPersonId());
	// // // // Map<String, Object> map1=null;
	// // // // try
	// // // // {
	// // // // map1 = objectMapper.readValue( ss, Map.class);
	// // // // }
	// // // // catch (JsonParseException e)
	// // // // {
	// // // // // TODO Auto-generated catch block
	// // // // e.printStackTrace();
	// // // // }
	// // // // catch (JsonMappingException e)
	// // // // {
	// // // // // TODO Auto-generated catch block
	// // // // e.printStackTrace();
	// // // // }
	// // // // catch (IOException e)
	// // // // {
	// // // // // TODO Auto-generated catch block
	// // // // e.printStackTrace();
	// // // // }
	// // // // System.err.println("map1:"+map1);
	// // // }
	// // //
	// // // // Gson gson = new Gson();
	// // // // List list = gson.fromJson(appJson, List.class);
	// // // //// List list = gson.fromJson(appJson, new TypeToken<List<?>>()
	// // // //// {
	// // // //// }.getType());
	// // // // System.err.println("list:"+list);
	// // // // // JSONArray jsonArray = JSONArray.getJSONObject(appJson);
	// // // // //
	// // // // // List<Answer> list =
	// // // // // (List) JSONArray.toCollection(jsonArray, Answer.class);
	// // // }
	// // //
	// // // // public static void main(String[] args) throws Exception
	// // // // {
	// // // // String appJson =
	// // //
	// //
	// "{\"appBaiKeType\": [{\"id\": 7,\"typeName\": \"糖尿病\",\"typeDesc\": \"糖尿病\",\"sortIndex\": 1,\"isShow\": \"1\",\"baiKeType\": \"A\",\"baiKeTypeImgUrl\": \"/BaiKe/A.jpg\"},{\"id\": 6,\"typeName\": \"高血压\",\"typeDesc\": \"高血压\",\"sortIndex\": 2,\"isShow\": \"1\",\"baiKeType\": \"B\",\"baiKeTypeImgUrl\": \"/BaiKe/B.jpg\"}]}}";
	// // // // ObjectMapper mapper = new ObjectMapper();
	// // // // Map<String,ArrayList<String>> o = mapper.readValue(appJson,
	// // // Map.class);
	// // // //
	// // // // System.out.println(o.get("appBaiKeType"));
	// // // // ArrayList<String> list = o.get("appBaiKeType");
	// // // //
	// // // // if(list.size()>0){
	// // // // for(int i=0;i<list.size();i++){
	// // // //
	// // // // String s = binder.toJson(list.get(i));
	// // // // AppBaiKeType type =
	// // AppJsonUtil.binder.fromJson(s,AppBaiKeType.class);
	// // // // System.out.println(type.getId());
	// // // // }
	// // // // }
	// }

}
