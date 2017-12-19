package com.iknet.commons.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;




 public class JsonBinder {  
       
     private static ObjectMapper objectMapper = new ObjectMapper();  
       
     private static JsonBinder jsonBinder;  
       
     private JsonBinder(Inclusion inclusion) {  
         // serializing 设置成 Inclusion.NON_DEFAULT 来提高性能  
         objectMapper.getSerializationConfig().setSerializationInclusion(inclusion);  
     }  
       
     /** 
      * 允许修改 {@link Inclusion}, 默认是 {@link InclusionNON_DEFAULT}, 也即对于未修改的属性不用进行 json 转换. 
      *  
      * @param inclusion 
      * @see getInstance() 
      */  
     public static JsonBinder getInstance(Inclusion inclusion) {  
         if (jsonBinder == null) {  
             jsonBinder = new JsonBinder(inclusion);  
         }  
         return jsonBinder;  
     }  
       
     public static JsonBinder getInstance() {  
         if (jsonBinder == null) {  
             jsonBinder = new JsonBinder(Inclusion.NON_DEFAULT);  
         }  
         return jsonBinder;  
     }  
       
     /** 
      * 从 json 字符串生成相应的 java 对象. 
      * <ul> 
      *     <li>1) 如果 JSON 字符串为 NULL 或 "null", 则返回 null.</li> 
      *     <li>2) 如果 JSON 字符串为 "[]", 则返回空集合.</li> 
      * </ul> 
      *  
      * @param <T> 
      * @param jsonString 
      * @param clazz 
      * @return 
      */  
     public <T> T fromJson(String jsonString, Class<T> clazz) {  
         if (StringUtils.isEmpty(jsonString)) {  
             return null;  
         }  
         try {  
             return objectMapper.readValue(jsonString, clazz);  
         } catch (IOException e) {  
             return null;  
         }  
     }  
       
     /** 
      * 将 java 对象生成 json 格式. 
      * <ul> 
      *     <li>1) 如果对象为 null, 则返回 "null".</li> 
      *     <li>2) 如果对象为空集合, 则返回 "[]".</li> 
      * </ul> 
      *  
      * @param object 
      * @return 
      */  
     public String toJson(Object object) {  
         try {  
             return objectMapper.writeValueAsString(object);  
         } catch (IOException e) {  
             return null;  
         }  
     }  
       
     /** 
      * 获取 <code>objectMapper</code> 对象, 完成其他该 binder 未封装的功能. 
      *  
      * @return 
      */  
     public ObjectMapper getObjectMapper() {  
         return objectMapper;  
     }  
     
 }