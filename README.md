# Spring Boot 3 初始化

一个基于Spring Boot 3的快速开发脚手架，集成Sa-Token、Hutool、MyBatis-Plus、Redis、Knife4j和MySQL，提供基础功能模块和最佳实践。

---

## 📖 技术栈

- **核心框架**: Spring Boot 3.4.x
- **工具库**: [Hutool 5.8.x](https://hutool.cn/) - 高效Java工具库
- **ORM框架**: [MyBatis-Plus 3.5.9](https://baomidou.com/) - 增强型CRUD操作
- **权限认证**: [Sa-Token 1.42.x](https://sa-token.cc/) - 轻量级权限解决方案
- **缓存**: Spring Data Redis + Redis 7.x
- **API文档**: [Knife4j 4.4.x](https://doc.xiaominfo.com/) - Swagger增强UI
- **数据库**: MySQL 8.x

---

## 🚀 快速开始

### 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 7.x+
- Maven 3.9+

### 启动步骤
1. **克隆项目**
```bash
   git clone https://github.com/tenyon61/springboot3-demo.git
```
2、**修改配置文件**
```text
   默认启动是 local，建议新建application-local.properties替换配置
```
3、**启动项目**
```text
   访问接口文档 http://localhost:8072/doc.html
```