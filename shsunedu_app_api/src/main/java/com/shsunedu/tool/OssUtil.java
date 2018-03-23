package com.shsunedu.tool;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListMultipartUploadsRequest;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.MultipartUpload;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PartSummary;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

/**
 * 
 * @ClassName: OssUtil
 * @Description: oss对象存储   key规则： kkoto/类型(doc、image、video)/文件名称
 * @author liyong
 * @date 2016年7月20日
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Component
public class OssUtil {
	@Value("${oss.accessKeyId}")
	private String ACCESSKEYID;
	@Value("${oss.accessKeySecret}")
	private String ACCESSKEYSECRET;
	@Value("${oss.out.url}")
	private String ENDPOINT;
	@Value("${oss.bucket.name}")
	private String BUCKETNAME;

	public void createFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Title: doUploadString
	 * @Description: 上传字符串
	 * @param key
	 *            标识文件的唯一的key
	 * @param content
	 *            字符串内容
	 * @return void 返回类型
	 * @throws
	 */
	public void doUploadString(String key, String content) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		ossClient.putObject(BUCKETNAME, key, new ByteArrayInputStream(content.getBytes()));
		// 关闭client
		ossClient.shutdown();
	}

	/**
	 * 
	 * @Title: doUploadbytes
	 * @Description: 上传byte数组
	 * @param key
	 *            标识文件的唯一的key
	 * @param bytes
	 *            内容
	 * @return void 返回类型
	 * @throws
	 */
	public void doUploadbytes(String key, byte[] bytes) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		ossClient.putObject(BUCKETNAME, key, new ByteArrayInputStream(bytes));
		// 关闭client
		ossClient.shutdown();
	}

	/**
	 * 
	 * @Title: doUploadUrl
	 * @Description: 上传网络流
	 * @param key
	 *            标识文件的唯一的key
	 * @param url
	 *            内容
	 * @return void 返回类型
	 * @throws
	 */
	public boolean doUploadUrl(String key, String url) {
		boolean bool = false;
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		// 上传
		try {
			InputStream inputStream = new URL(url).openStream();
			ossClient.putObject(BUCKETNAME, key, inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return bool;
		}
		// 检验文件是否上传成功
		bool = ossClient.doesObjectExist(BUCKETNAME, key);
		// 关闭client
		ossClient.shutdown();
		return bool;
	}

	/**
	 * 
	 * @Title: doUploadFileInputStream
	 * @Description: 上传文件流
	 * @param key
	 *            标识文件的唯一的key
	 * @param filePath
	 *            内容
	 * @return void 返回类型
	 * @throws
	 */
	public boolean doUploadFileInputStream(String key, InputStream inputStream) {
		boolean bool = false;
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		ossClient.putObject(BUCKETNAME, key, inputStream);
		// 检验文件是否上传成功
		bool = ossClient.doesObjectExist(BUCKETNAME, key);
		// 关闭client
		ossClient.shutdown();
		return bool;
	}

	/**
	 * 上传图片，需要这种文件格式
	 * 
	 * @param key
	 * @param inputStream
	 * @param contentType
	 * @return
	 */
	public boolean doUploadImgInputStream(String key, InputStream inputStream, String contentType) {
		boolean bool = false;
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 设置上传内容类型
		// meta.setContentType("image/jpeg");
		meta.setContentType(contentType);
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		ossClient.putObject(BUCKETNAME, key, inputStream, meta);
		// 检验文件是否上传成功
		bool = ossClient.doesObjectExist(BUCKETNAME, key);
		// 关闭client
		ossClient.shutdown();
		return bool;
	}

	/**
	 * 
	 * @Title: doUploadFile
	 * @Description: 上传本地文件
	 * @param key
	 *            标识文件的唯一的key
	 * @param filePath
	 *            内容
	 * @return void 返回类型
	 * @throws
	 */
	public void doUploadFile(String key, String filePath) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		// 上传文件流
		ossClient.putObject(BUCKETNAME, key, new File(filePath));
		// 关闭client
		ossClient.shutdown();
	}

	/**
	 * 
	 * @Title: doUploadResFile
	 * @Description: 断点续传
	 * @param key
	 *            标识文件的唯一的key
	 * @param filePath
	 *            本地存储的路径
	 * @return void 返回类型
	 * @throws
	 */
	public String doUploadResFile(String key, String filePath) {
		String tag = null;
		String uploadid = null;
		int j = 0;
		// 初始化一个OSSClient
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		ListMultipartUploadsRequest lmur = new ListMultipartUploadsRequest(BUCKETNAME);
		// 获取Bucket内所有上传事件
		MultipartUploadListing listing = ossClient.listMultipartUploads(lmur);
		// 新建一个List保存每个分块上传后的ETag和PartNumber
		List<PartETag> partETags = new ArrayList<PartETag>();
		// 遍历所有上传事件 设置UploadId
		for (MultipartUpload multipartUpload : listing.getMultipartUploads()) {
			if (multipartUpload.getKey().equals(key)) {
				uploadid = multipartUpload.getUploadId();
				break;
			}
		}
		if (StringUtils.isEmpty(uploadid)) {
			// 开始Multipart Upload,InitiateMultipartUploadRequest
			// 来指定上传Object的名字和所属Bucke
			InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(BUCKETNAME, key);
			InitiateMultipartUploadResult initiateMultipartUploadResult = ossClient.initiateMultipartUpload(initiateMultipartUploadRequest);
			uploadid = initiateMultipartUploadResult.getUploadId();
		} else {
			ListPartsRequest listPartsRequest = new ListPartsRequest(BUCKETNAME, key, uploadid);
			// listParts 方法获取某个上传事件所有已上传的块
			PartListing partListing = ossClient.listParts(listPartsRequest);
			// 遍历所有Part
			for (PartSummary part : partListing.getParts()) {
				partETags.add(new PartETag(part.getPartNumber(), part.getETag()));
				j++;
			}
		}
		File partFile = new File(filePath);
		// 设置每块为 5M（最小支持5M）
		final int partSize = 1024 * 1024 * 1;
		// 计算分块数目
		int partCount = (int) (partFile.length() / partSize);
		if (partFile.length() % partSize != 0) {
			partCount++;
		}
		try {
			for (int i = j; i < partCount; i++) {
				// 获取文件流
				FileInputStream fis;
				fis = new FileInputStream(partFile);
				// 跳到每个分块的开头
				long skipBytes = partSize * i;
				fis.skip(skipBytes);
				// 计算每个分块的大小
				long size = partSize < partFile.length() - skipBytes ? partSize : partFile.length() - skipBytes;
				// 创建UploadPartRequest，上传分块
				UploadPartRequest uploadPartRequest = new UploadPartRequest();
				uploadPartRequest.setBucketName(BUCKETNAME);
				uploadPartRequest.setKey(key);
				uploadPartRequest.setUploadId(uploadid);
				uploadPartRequest.setInputStream(fis);
				uploadPartRequest.setPartSize(size);
				uploadPartRequest.setPartNumber(i + 1);
				UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
				// 将返回的PartETag保存到List中。
				partETags.add(uploadPartResult.getPartETag());
				// 关闭文件
				fis.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(BUCKETNAME, key, uploadid, partETags);
		// 完成分块上传
		CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);
		// 打印Object的ETag（返回的ETag不是md5.具体是什么不详）
		tag = completeMultipartUploadResult.getETag();
		return tag;
	}

	/**
	 * 
	 * @Title: mkdir
	 * @Description:创建文件夹
	 * @param dir
	 *            目录
	 * @return void 返回类型
	 * @throws
	 */
	public void mkdir(String dir) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		// 上传文件流
		ossClient.putObject(BUCKETNAME, dir, new ByteArrayInputStream(new byte[0]));
		// 关闭client
		ossClient.shutdown();
	}

	/**
	 * 
	 * @Title: dirExist
	 * @Description: 校验文件是否存在
	 * @param key
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean dirExist(String key) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		// Object是否存在
		boolean isExist = ossClient.doesObjectExist(BUCKETNAME, key);
		// 关闭client
		ossClient.shutdown();
		return isExist;
	}

	/**
	 * 
	 * @Title: downFile
	 * @Description: 下载文件到本地
	 * @param key
	 *            标识文件的唯一的key
	 * @param filePath
	 *            本地存储的路径
	 * @return void 返回类型
	 * @throws
	 */
	public void downFile(String key, String filePath) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		// 下载object到文件
		ossClient.getObject(new GetObjectRequest(BUCKETNAME, key), new File(filePath));
		// 关闭client
		ossClient.shutdown();
	}

	/**
	 * 
	 * @Title: downloadToBytes
	 * @Description: 下载文件
	 * @param @param key
	 * @param @return 参数
	 * @return byte[] 返回类型
	 * @throws
	 */
	public byte[] downloadToBytes(String key) {
		try {
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
			// 下载object到文件
			// 读取数据
			OSSObject ossObject = ossClient.getObject(BUCKETNAME, key);
			InputStream in = ossObject.getObjectContent();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buffer))) {
				output.write(buffer, 0, n);
			}
			// InputStream数据读完成后，一定要close，否则会造成连接泄漏
			in.close();
			// 关闭client
			ossClient.shutdown();
			return output.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getObjectUrl(String key, int days) {
		try {
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
			// 设置过期时间
			GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(BUCKETNAME, key, HttpMethod.GET);
			request.addQueryParameter("code", System.currentTimeMillis() + "");
			// 设置过期时间
			Date expiration = TimeUtil.dayAdd(new Date(), days);
			request.setExpiration(expiration);
			// 生成URL签名(HTTP GET请求)
			URL signedUrl = ossClient.generatePresignedUrl(request);
			return signedUrl.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @Title: delFile
	 * @Description: 删除单个文件
	 * @param key
	 *            要删除的文件
	 * @return void 返回类型
	 * @throws
	 */
	public void delFile(String key) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		// 下载object到文件
		ossClient.deleteObject(BUCKETNAME, key);
		// 关闭client
		ossClient.shutdown();
	}

	/**
	 * 
	 * @Title: delMoreFile
	 * @Description:删除多个文件
	 * @param keys
	 *            参数
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	public void delMoreFile(List<String> keys) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		// 下载object到文件
		DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(BUCKETNAME).withKeys(keys));
		List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
		// 关闭client
		ossClient.shutdown();
	}

}
