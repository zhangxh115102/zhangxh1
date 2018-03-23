package com.shsunedu.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.shsunedu.base.api.util.SerializeUtil;

@Component
public class RedisUtil {
	@Resource
	private ShardedJedisPool shardedJedisPool;
	
	private Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	
	/**
	 * 设置对象
	 * @param key
	 * @param obj
	 * @param seconds
	 * @return
	 */
	public boolean setObject(String key,Object obj,int seconds ){
		if (key==null || key.equals("") || obj == null) {
            return false;
        }
		return set(key, JSONObject.toJSONString(obj), seconds);
	}
	/**
	 * 根据key获取转换后的对象(简单对象)
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T getObject(String key,Class<T> clazz){
		if (key == null || key.length() == 0 || clazz == null) {
            return null;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            String result = shardedJedis.get(key);
            if(result == null || result.length() == 0){
            	return null;
            }
           return JSONObject.parseObject(result, clazz);
        } catch (Exception ex) {
            logger.error("getObject error[key=" + key + "]" + ex.getMessage(), ex);
        } finally {
            returnResource(shardedJedis);
        }
		return null;
	}
	
	/**
	 * 根据key获取转换后的对象(复杂对象)
	 * @param key
	 * @param typeReference
	 * @return
	 */
	public <T> T getObject(String key,TypeReference<T> typeReference){
		if (key == null || key.length() == 0 || typeReference == null) {
            return null;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            String result = shardedJedis.get(key);
            if(result == null || result.length() == 0){
            	return null;
            }
            
            return JSONObject.parseObject(result, typeReference);
        } catch (Exception ex) {
            logger.error("getObject error[key=" + key + "]" + ex.getMessage(), ex);
        } finally {
            returnResource(shardedJedis);
        }
		return null;
	}
	
	

    /**
     * 设置一个key的过期时间（单位：秒）
     * @param key key值
     * @param seconds 多少秒后过期
     * @return 1：设置了过期时间  0：没有设置过期时间/不能设置过期时间
     */
    public long expire(String key, int seconds) {
        if (key==null || key.equals("")) {
            return 0;
        }

        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.expire(key, seconds);
        } catch (Exception ex) {
            logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);
        } finally {
            returnResource(shardedJedis);
        }
        return 0;
    }

    /**
     * 设置一个key在某个时间点过期
     * @param key key值
     * @param unixTimestamp unix时间戳，从1970-01-01 00:00:00开始到现在的秒数
     * @return 1：设置了过期时间  0：没有设置过期时间/不能设置过期时间
     */
    public long expireAt(String key, int unixTimestamp) {
        if (key==null || key.equals("")) {
            return 0;
        }

        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.expireAt(key, unixTimestamp);
        } catch (Exception ex) {
            logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + ex.getMessage(), ex);
        } finally {
            returnResource(shardedJedis);
        }
        return 0;
    }

    /**
     * 截断一个List
     * @param key 列表key
     * @param start 开始位置 从0开始
     * @param end 结束位置
     * @return 状态码
     */
    public String trimList(String key, long start, long end) {
        if (key == null || key.equals("")) {
            return "-";
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.ltrim(key, start, end);
        } catch (Exception ex) {
            logger.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage() , ex);
        } finally {
            returnResource(shardedJedis);
        }
        return "-";
    }
    /**
     * 检查Set长度
     * @param key
     * @return
     */
    public long countSet(String key){
    	if(key == null ){
    		return 0;
    	}
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.scard(key);
        } catch (Exception ex) {
            logger.error("countSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
    	return 0;
    }
    /**
     * 添加到Set中（同时设置过期时间）
     * @param key key值
     * @param seconds 过期时间 单位s
     * @param value
     * @return
     */
    public boolean addSet(String key,int seconds, String... value) {
    	boolean result = addSet(key, value);
    	if(result){
    		long i = expire(key, seconds);
    		return i==1;
    	}
    	return false;
    }
    /**
     * 添加到Set中
     * @param key
     * @param value
     * @return
     */
    public boolean addSet(String key, String... value) {
    	if(key == null || value == null){
    		return false;
    	}
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.sadd(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("setList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    
    /**
     * @param key
     * @param value
     * @return 判断值是否包含在set中
     */
    public boolean containsInSet(String key, String value) {
    	if(key == null || value == null){
    		return false;
    	}
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.sismember(key, value);
        } catch (Exception ex) {
            logger.error("setList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }
    /**
     * 获取Set
     * @param key
     * @return
     */
    public  Set<String> getSet(String key){
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.smembers(key);
        } catch (Exception ex) {
            logger.error("getList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    /**
     * 从set中删除value
     * @param key
     * @return
     */
    public  boolean removeSetValue(String key,String... value){
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.srem(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("getList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }
    
    
    
    /**
     * 从list中删除value 默认count 1
     * @param key
     * @param values 值list
     * @return
     */
    public  int removeListValue(String key,List<String> values){
    	return removeListValue(key, 1, values);
    }
    /**
     * 从list中删除value
     * @param key
     * @param count 
     * @param values 值list
     * @return
     */
    public  int removeListValue(String key,long count,List<String> values){
    	int result = 0;
    	if(values != null && values.size()>0){
    		for(String value : values){
    			if(removeListValue(key, count, value)){
    				result++;
    			}
    		}
    	}
    	return result;
    }
    /**
     *  从list中删除value
     * @param key
     * @param count 要删除个数
     * @param value
     * @return
     */
    public  boolean removeListValue(String key,long count,String value){
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.lrem(key, count, value);
            return true;
        } catch (Exception ex) {
            logger.error("getList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
    	return false;
    }
    
    /**
     * 截取List
     * @param key 
     * @param start 起始位置
     * @param end 结束位置
     * @return
     */
    public List<String> rangeList(String key, long start, long end) {
        if (key == null || key.equals("")) {
            return null;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.lrange(key, start, end);
        } catch (Exception ex) {
            logger.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage() , ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }
    
    /**
     * 检查List长度
     * @param key
     * @return
     */
    public long countList(String key){
    	if(key == null ){
    		return 0;
    	}
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.llen(key);
        } catch (Exception ex) {
            logger.error("countList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
    	return 0;
    }
    
    /**
     * 添加到List中（同时设置过期时间）
     * @param key key值
     * @param seconds 过期时间 单位s
     * @param value 
     * @return 
     */
    public boolean addList(String key,int seconds, String... value){
    	boolean result = addList(key, value);
    	if(result){
    		long i = expire(key, seconds);
    		return i==1;
    	}
    	return false;
    }
    /**
     * 添加到List
     * @param key
     * @param value
     * @return
     */
    public boolean addList(String key, String... value) {
    	if(key == null || value == null){
    		return false;
    	}
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.lpush(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("setList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }
    /**
     * 添加到List(只新增)
     * @param key
     * @param value
     * @return
     */
    public boolean addList(String key, List<String> list) {
    	if(key == null || list == null || list.size() == 0){
    		return false;
    	}
    	for(String value : list){
    		addList(key, value);
    	}
        return true;
    }
    
    /**
     * 获取List
     * @param key
     * @return
     */
    public  List<String> getList(String key){
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.lrange(key, 0, -1);
        } catch (Exception ex) {
            logger.error("getList error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }
    /**
     * 设置HashSet对象
     *
     * @param domain 域名
     * @param key    键值
     * @param value  Json String or String value
     * @return
     */
    public boolean setHSet(String domain, String key, String value) {
        if (value == null) return false;
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.hset(domain, key, value);
            return true;
        } catch (Exception ex) {
            logger.error("setHSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 获得HashSet对象
     *
     * @param domain 域名
     * @param key    键值
     * @return Json String or String value
     */
    public String getHSet(String domain, String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.hget(domain, key);
        } catch (Exception ex) {
            logger.error("getHSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    /**
     * 删除HashSet对象
     *
     * @param domain 域名
     * @param key    键值
     * @return 删除的记录数
     */
    public long delHSet(String domain, String key) {
        ShardedJedis shardedJedis = null;
        long count = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            count = shardedJedis.hdel(domain, key);
        } catch (Exception ex) {
            logger.error("delHSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return count;
    }

    /**
     * 删除HashSet对象
     *
     * @param domain 域名
     * @param key    键值
     * @return 删除的记录数
     */
    public long delHSet(String domain, String... key) {
        ShardedJedis shardedJedis = null;
        long count = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            count = shardedJedis.hdel(domain, key);
        } catch (Exception ex) {
            logger.error("delHSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return count;
    }

    /**
     * 判断key是否存在
     *
     * @param domain 域名
     * @param key    键值
     * @return
     */
    public boolean existsHSet(String domain, String key) {
        ShardedJedis shardedJedis = null;
        boolean isExist = false;
        try {
            shardedJedis = shardedJedisPool.getResource();
            isExist = shardedJedis.hexists(domain, key);
        } catch (Exception ex) {
            logger.error("existsHSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return isExist;
    }

    /**
     * 全局扫描hset
     *
     * @param match field匹配模式
     * @return
     */
    public List<Map.Entry<String, String>> scanHSet(String domain, String match) {
        ShardedJedis shardedJedis = null;
        try {
            int cursor = 0;
            shardedJedis = shardedJedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            scanParams.match(match);
            Jedis jedis = shardedJedis.getShard(domain);
            ScanResult<Map.Entry<String, String>> scanResult;
            List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>();
            do {
                scanResult = jedis.hscan(domain, String.valueOf(cursor), scanParams);
                list.addAll(scanResult.getResult());
                cursor = Integer.parseInt(scanResult.getStringCursor());
            } while (cursor > 0);
            return list;
        } catch (Exception ex) {
            logger.error("scanHSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }


    /**
     * 返回 domain 指定的哈希集中所有字段的value值
     *
     * @param domain
     * @return
     */

    public List<String> hvals(String domain) {
        ShardedJedis shardedJedis = null;
        List<String> retList = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            retList = shardedJedis.hvals(domain);
        } catch (Exception ex) {
            logger.error("hvals error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return retList;
    }

    /**
     * 返回 domain 指定的哈希集中所有字段的key值
     *
     * @param domain
     * @return
     */

    public Set<String> hkeys(String domain) {
        ShardedJedis shardedJedis = null;
        Set<String> retList = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            retList = shardedJedis.hkeys(domain);
        } catch (Exception ex) {
            logger.error("hkeys error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return retList;
    }

    /**
     * 返回 domain 指定的哈希key值总数
     *
     * @param domain
     * @return
     */
    public long lenHset(String domain) {
        ShardedJedis shardedJedis = null;
        long retList = 0;
        try {
            shardedJedis = shardedJedisPool.getResource();
            retList = shardedJedis.hlen(domain);
        } catch (Exception ex) {
            logger.error("hkeys error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return retList;
    }

    /**
     * 设置排序集合
     *
     * @param key
     * @param score
     * @param value
     * @return
     */
    public boolean setSortedSet(String key, long score, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.zadd(key, score, value);
            return true;
        } catch (Exception ex) {
            logger.error("setSortedSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 获得排序集合
     *
     * @param key
     * @param startScore
     * @param endScore
     * @param orderByDesc
     * @return
     */
    public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (orderByDesc) {
                return shardedJedis.zrevrangeByScore(key, endScore, startScore);
            } else {
                return shardedJedis.zrangeByScore(key, startScore, endScore);
            }
        } catch (Exception ex) {
            logger.error("getSoredSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    /**
     * 计算排序长度
     *
     * @param key
     * @param startScore
     * @param endScore
     * @return
     */
    public long countSoredSet(String key, long startScore, long endScore) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            Long count = shardedJedis.zcount(key, startScore, endScore);
            return count == null ? 0L : count;
        } catch (Exception ex) {
            logger.error("countSoredSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return 0L;
    }

    /**
     * 删除排序集合
     *
     * @param key
     * @param value
     * @return
     */
    public boolean delSortedSet(String key, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            long count = shardedJedis.zrem(key, value);
            return count > 0;
        } catch (Exception ex) {
            logger.error("delSortedSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    /**
     * 获得排序集合
     *
     * @param key
     * @param startRange
     * @param endRange
     * @param orderByDesc
     * @return
     */
    public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (orderByDesc) {
                return shardedJedis.zrevrange(key, startRange, endRange);
            } else {
                return shardedJedis.zrange(key, startRange, endRange);
            }
        } catch (Exception ex) {
            logger.error("getSoredSetByRange error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    /**
     * 获得排序打分
     *
     * @param key
     * @return
     */
    public Double getScore(String key, String member) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.zscore(key, member);
        } catch (Exception ex) {
            logger.error("getSoredSet error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    public boolean set(String key, String value, int second) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.setex(key, second, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    public boolean set(String key, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("set error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    public String get(String key) {
    	return get(key, null);
    }
    public String get(String key, String defaultValue) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.get(key) == null?defaultValue:shardedJedis.get(key);
        } catch (Exception ex) {
            logger.error("get error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return defaultValue;
    }

    public boolean del(String... key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            for(String str : key){
            	shardedJedis.del(str);
            }
            return true;
        } catch (Exception ex) {
            logger.error("del error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return false;
    }

    public long incr(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.incr(key);
        } catch (Exception ex) {
            logger.error("incr error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return 0;
    }

    public long decr(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.decr(key);
        } catch (Exception ex) {
            logger.error("incr error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return 0;
    }
    
    /**
     * 队列存数据
     * @param key
     * @param value
     * @return
     */
    public long lpush(String key, String... value) {
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.lpush(key, value);
        } catch (Exception ex) {
            logger.error("lpush error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return 0;
    }
    /**
     * 队列取数据
     * @param key
     * @return
     */
    public String rpop(String key) {
    	ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.rpop(key);
        } catch (Exception ex) {
            logger.error("rpop error.", ex);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }


    private void returnResource(ShardedJedis shardedJedis) {
        try {
        	if(shardedJedis == null){
        		return;
        	}
        	shardedJedis.close();
//            shardedJedisPool.returnResource(shardedJedis);
        } catch (Exception e) {
            logger.error("returnResource error.", e);
        }
    }

    /**
     * 设置List集合
     * @param key
     * @param list
     */
    public boolean setTList(String key ,List<?> list, int seconds){
    	  ShardedJedis shardedJedis = null;
          try {
              shardedJedis = shardedJedisPool.getResource();
              shardedJedis.set(key.getBytes(), SerializeUtil.serializeList(list));
              shardedJedis.expire(key.getBytes(), seconds);
              return true;
          } catch (Exception ex) {
              logger.error("set error.", ex);
          } finally {
              returnResource(shardedJedis);
          }
          return false;
	}
	
    /**
     * 获取List集合
     * @param key
     * @return
     */
	public  List<?> getTList(String key){
		ShardedJedis shardedJedis = null;
	        try {
	            shardedJedis = shardedJedisPool.getResource();
	            byte[] data = shardedJedis.get(key.getBytes());
	            return SerializeUtil.unserializeList(data);
	        } catch (Exception ex) {
	            logger.error("getHSet error.", ex);
	        } finally {
	            returnResource(shardedJedis);
	        }
	        return null;
	}
}
