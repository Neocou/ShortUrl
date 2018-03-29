# ShortUrl
A ShortUrl demo using spring boot



# 一个最近看过的短链接生成方式

短链接即是将原本的长链接转换成较短的链接，网上有百度等公司提供的短链接服务。<br>
这里我自己尝试实现一下，项目采用spring boot快速开发，前端未作处理，仅通过http get传参来测试功能。<br>
另：采用Redis缓存数据，更新数据采用异步方式，数据库使用mysql，Java dao层次采用mybatis,返回数据为json.<br>

基本思路：<br>
    1、获取redis存储的自增长值,标志数据量<br>
    2、对应算法将自增长值变换成62进制编码并取对应字符，即短链接代码<br>
    3、62个编码即A-Za-z0-9,理论上一位62种可能，六位62^6,500亿左右足以应付，不用考虑重复问题。PS：也可以采用一些手段防止恶意生成短链接。<br>
    4、存储在mysql之类的数据库<br>
    
    
# 测试效果
1、短链接查询<br>
  127.0.0.1:8907/short/{短链接代码}<br>
  输入地址栏：127.0.0.1:8907/short/x  <br>
  返回：{"code":"0","data":"cant find org url by code :x","status":5055}<br>
2、短链接生成<br>
  127.0.0.1:8907/short/get/{长链接地址}<br>
  输入地址栏：http://127.0.0.1:8907/short/get/baidu.com<br>
  返回：{"code":0,"data":{"result":"true","url_short":"127.0.0.1:8907/short/X","url_long":"baidu.com","type":"0"},"status":200}<br>
3、测试生成<br>
  输入地址栏：127.0.0.1:8907/short/X  <br>
  返回：{"code":0,"data":"baidu.com","status":200}<br>


# 注释

下载测试请更改application里的配置参数，内附有sql数据库文件
