package com.xhpower.education.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 
* @ClassName: CacheMap 
* @Description: 缓存类
* @author lisf
* @date 2016年10月11日 下午5:37:37 
* 
* @param <K>
* @param <V>
 */
public class CacheMap<K, V> {
	class CacheObject<K2,V2> {
	      CacheObject(K2 key, V2 value, long ttl) {
	            this.key = key;
	            this.cachedObject = value;
	            this.ttl = ttl;
	            this.lastAccess = System.currentTimeMillis();
	        }
	 
	        final K2 key;
	        final V2 cachedObject;
	        long lastAccess;        // 最后访问时间
	        long accessCount;       // 访问次数
	        long ttl;               // 对象存活时间(time-to-live)
	 
	        boolean isExpired() {
	            if (ttl == 0) {
	                return false;
	            }
	            return lastAccess + ttl < System.currentTimeMillis();
	        }
	        V2 getObject() {
	            lastAccess = System.currentTimeMillis();
	            accessCount++;
	            return cachedObject;
	        }
	    }
	
	    protected Map<K,CacheObject<K,V>> cacheMap;
	 
	    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
	    private final Lock readLock = cacheLock.readLock();
	    private final Lock writeLock = cacheLock.writeLock();
	    
	    protected int cacheSize;      // 缓存大小 , 0 -> 无限制
	     
	    protected  boolean existCustomExpire ; //是否设置默认过期时间
	     
	    public int getCacheSize() {
	        return cacheSize;
	    }
	 
	    protected long defaultExpire;     // 默认过期时间, 0 -> 永不过期
	     
	    public CacheMap(int cacheSize ,long defaultExpire){
	        this.cacheSize  = cacheSize ;
	        this.defaultExpire  = defaultExpire ;
	        cacheMap = new HashMap<K, CacheObject<K,V>>(cacheSize+1);
	    }
	 
	     
	    public long getDefaultExpire() {
	        return defaultExpire;
	    }
	 
	 
	    protected boolean isNeedClearExpiredObject(){
	        return defaultExpire > 0 || existCustomExpire ;
	    }
	 
	     
	    public void put(K key, V value) {
	        put(key, value, defaultExpire );
	    }
	 
	 
	    public void put(K key, V value, long expire) {
	        writeLock.lock();
	 
	        try {
	            CacheObject<K,V> co = new CacheObject<K,V>(key, value, expire);
	            if (expire != 0) {
	                existCustomExpire = true;
	            }
	            if (isFull()) {
	                eliminate() ;
	            }
	            cacheMap.put(key, co);
	        }
	        finally {
	            writeLock.unlock();
	        }
	    }
	 
	 
	 
	    /**
	     * {@inheritDoc}
	     */
	    public V get(K key) {
	        readLock.lock();
	 
	        try {
	            CacheObject<K,V> co = cacheMap.get(key);
	            if (co == null) {
	                return null;
	            }
	            if (co.isExpired() == true) {
	                cacheMap.remove(key);
	                return null;
	            }
	 
	            return co.getObject();
	        }
	        finally {
	            readLock.unlock();
	        }
	    }
	     
	    public final int eliminate() {
	        writeLock.lock();
	        try {
	            return eliminateCache();
	        }
	        finally {
	            writeLock.unlock();
	        }
	    }
	     
	    public int eliminateCache() {
	        Iterator<CacheObject<K, V>> iterator = cacheMap.values().iterator();
	        int count  = 0 ;
	        long minAccessCount = Long.MAX_VALUE  ;
	        while(iterator.hasNext()){
	            CacheObject<K, V> cacheObject = iterator.next();
	             
	            if(cacheObject.isExpired() ){
	                iterator.remove(); 
	                count++ ;
	                continue ;
	            }else{
	                minAccessCount  = Math.min(cacheObject.accessCount , minAccessCount)  ;
	            }
	        }
	         
	        if(count > 0 ) return count ;
	         
	        if(minAccessCount != Long.MAX_VALUE ){
	             
	            iterator = cacheMap.values().iterator();
	             
	            while(iterator.hasNext()){
	                CacheObject<K, V> cacheObject = iterator.next();
	                 
	                cacheObject.accessCount  -=  minAccessCount ;
	                 
	                if(cacheObject.accessCount <= 0 ){
	                    iterator.remove();
	                    count++ ;
	                }
	                 
	            }
	             
	        }
	         
	        return count;
	    }
	 
	     
	    public boolean isFull() {
	        if (cacheSize == 0) {//o -> 无限制
	            return false;
	        }
	        return cacheMap.size() >= cacheSize;
	    }
	 
	     
	    public void remove(K key) {
	        writeLock.lock();
	        try {
	            cacheMap.remove(key);
	        }
	        finally {
	            writeLock.unlock();
	        }
	    }
	 
	     
	    public void clear() {
	        writeLock.lock();
	        try {
	            cacheMap.clear();
	        }
	        finally {
	            writeLock.unlock();
	        }
	    }
	 
	    public int size() {
	        return cacheMap.size();
	    }
	 
	     
	    public boolean isEmpty() {
	        return size() == 0;
	    }
}
