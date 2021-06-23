# 解析区域数据

## 数据来源
### 国家统计局2020年份
http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/index.html

## 应用架构
Spring Boot + Mybatis + Jsoup + Lombok + MySQL

## 使用说明
1. 建立数据库address，将resource/database/address.sql导入
2. 修改resource/application.properties的datasource的username、password、url为实际环境的值
3. 运行入口可参照AddressApplicationTests
4. resource/database/address-2020.sql 为已解析处理的数据
