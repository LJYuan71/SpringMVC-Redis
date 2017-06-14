## SpringRedis

### Use technology
* RESTful
* SpringMVC
* Spring Data Redis
* MyBatis
* Druid
* Bootstrap-Table
* jQuery


### CN
* Spring整合Redis进行缓存
* 使用Spring Data Redis注解管理缓存
* 使用redisTemplate对Redis进行主动存取

## 项目运行方法：
1.Redis正常运行且正确配置连接<br/>
2.MySQL数据库启动导入数据且正确配置数据库连接<br/>
3.项目执行Maven-->Update Project<br/>
4.\*选择Tomcat8以上的服务器启动项目<br/>

*本项目通过github其他人的项目+自己的理解改造。

## 注解缓存测试
##### 浏览器请求对比图：上面3个是第一次请求(未缓存),后面3测直接从redis中获取。所以请求和相应花费的时间较少<br>
![浏览器请求](https://github.com/LJYuan71/SpringMVC-Redis/src/main/webapp/images/请求对比图.png "浏览器请求对比图") 
##### 数据库请求图：可以通过eclipse控制台看出。只有前3次的请求连接了数据库查询,后面发起请求也不再去数据库中查找（缓存）<br>
![浏览器请求](https://github.com/LJYuan71/SpringMVC-Redis/src/main/webapp/images/后台数据库查询情况.png "数据库请求情况图")
##### Redis存储请求参数和数据情况<br>
![浏览器请求](https://github.com/LJYuan71/SpringMVC-Redis/src/main/webapp/images/redis请求参数存储.png "方法参数Redis")
![浏览器请求](https://github.com/LJYuan71/SpringMVC-Redis/src/main/webapp/images/redis参数对应数据存储.png "方法对应参数的数据图")
## 存取测试
存取可以在任意地方一次或多次调用,内容类型任意。 详情到ExampleServiceImpl.setAndGetDataFromRedis()方法中查看

## 缓存注解位置选择
缓存会获取到注解所在的完整类名、方法名以及方法的参数。因此如果对参数对SQL语句的执行有别的话,需要将注解放置于最接近SQL语句的位置。比如Mybatis的xml对应的namespace上
![浏览器请求](https://github.com/LJYuan71/SpringMVC-Redis/src/main/webapp/images/缓存位置.png "注解使用位置选择")



