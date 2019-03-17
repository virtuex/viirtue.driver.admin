# 驾考宝典后台管理系统及APP数据接口
> 个人练习项目
## 項目簡介
  在当今社会里，也许没有哪一种技术能像 Internet 这样对我们的工作方式．生活方式和学习方式带来如此迅猛而强烈的影响。随着家用轿车的普及，需要参加驾照考试的人数越来越多，而科目一及科目四都属于驾驶理论及文明驾驶范畴，需要做符合大纲的模拟题辅助学习。
  驾考宝典可以帮助用户通过手机进行全天侯的学习，并可以通过该应用中一些实用的功能及复习方法，让用户在极短的时间内完成对文明驾驶范畴试题的学习并能提高用户的通过率，从而节省用户的时间。
## 技术选型
### 后端技术
- [SpringBoot](http://spring.io/projects/spring-boot/)
- [Mybatis](http://www.mybatis.org/)
- [Spring Data Jpa]()
### 前端技术
- [Jquery](http://jquery.com/)
- [AdminLTE3](https://adminlte.io/themes/dev/AdminLTE/index.html)
- [BootStrap](http://www.bootcss.com/)
### App
- [MUI-前段APP框架](http://dev.dcloud.net.cn/mui/)

## 设计
### 功能设计
#### 前端APP功能
- 习题练习功能，应该具备如下练习方式
  - 随机练习
  - 强化练习，其中包括
    - 按难易程度，分为易错题、一般题、简单题三种
    - 按知识点分，分为信号、时间、罚款、距离、速度、酒驾等知识点
    - 按试题类型，分为选择题、判断题
- 模拟考试功能
  - 按照章节、知识点比例、试题类型，在题库中随机抽取100题构成试卷
  - 要求选择项顺序能随意打乱
  - 模拟考试有可选项“优先考未做题”、“全真模拟考试”
  - 自动计时、自动判卷、错题标记、错题收藏功能
- 统计功能
  - 考试题目统计
  - 考试成绩统计
- 考试记录复习回顾功能
  - 错题归类功能，按照章节知识点归类，答题时，给出答题正误情况的历史统计，收藏/取消收藏、查看答案，并给出简要点评
  - 题目收藏功能，按照章节知识点归类，答题时，给出答题正误情况的历史统计，收藏/取消收藏、查看答案，并给出简要点评
#### 后端管理功能
- 题库管理
- 用户管理
- 统计管理
- 评论管理

### 模块设计
#### APP模块设计
- 随机练习
- 强化练习
- 模拟考试
- 个人中心
  - 个人成绩历史
  - 易错题
  - 收藏
#### 后端管理模块设计
- 用户模块
- 题库管理模块
- 统计模块
- 评论管理模块

### 数据库设计
- 题库表(TB_ITEM_BANK)

| 字段名 | 类型 | 长度 | 是否主键 | 是否为空 | 描述 |
| ------ | ------ | ------ | ------| ------| ------|
| ITEM_BANK_ID | int | 11 | Y| N| 题目主键|
| ITEM_BANK_SUBJECT | longtext |  | N| N| 题目内容|
| ITEM_BANK_CHOICEA | varchar | 11 | N| N| 选项A|
| ITEM_BANK_CHOICEB | varchar | 11 | N| N| 选项B|
| ITEM_BANK_CHOICEC | varchar | 11 | N| Y| 选项C|
| ITEM_BANK_CHOICED | varchar | 11 | N| Y| 选项D|
| ITEM_BANK_KOWNLEDGE_TYPE | int | 11 | ------| N| 难易程度|
| ITEM_BANK_DIFFICUT_LEVEL | int | 11 | ------| N| 难易程度|
| ITEM_BANK_SUBJECT_TYPE | int | 11 | ------| N| 题目类型(选择，判断)|
| ITEM_BANK_IMAGE_URL | int | 11 | ------| N| 图片URL|
| ITEM_BANK_ANSWER_ANALYSE | int | 11 | ------| N| 题目解析|


- 用户表(TB_USER)

| 字段名 | 类型 | 长度 | 是否主键 | 是否为空 | 描述 |
| ------ | ------ | ------ | ------| ------| ------|
| USER_ID | int | 11 | Y| N| 用户ID|
| USER_NAME| varchar | 255 | N| N| 用户名|
| USER_PASSWORD | varchar | 255 | N| N| 密码|
| USER_GENDER | int | 11 | N| N| 性别|
| USER_PROVICE | varchar   | 255 | N| N| 省|
| USER_CITY | varchar | 255 | N| N| 市|
| USER_TYPE | int | 11 | N| N| 用户类型(会员)|
| USER_AGE | int | 11 | N| N| 年龄|

- 评论表(TB_COMMENT)---考虑使用mangodb作为评论的存储介质

| 字段名 | 类型 | 长度 | 是否主键 | 是否为空 | 描述 |
| ------ | ------ | ------ | ------| ------| ------|
| COMMENT_ID | int | 11 | Y| N| 评论ID|
| COMMENT_CONTENT | longtext |  | N| N| 评论内容|
| COMMENT_ITEM_ID | int | 11 | ------| ------| 题目ID|
| COMMENT_USER_ID | int | 11 | ------| ------| 评论人ID|

- 成绩表(TB_GRADE

| 字段名 | 类型 | 长度 | 是否主键 | 是否为空 | 描述 |
| ------ | ------ | ------ | ------| ------| ------|
| GRADE_ID | int | 11 | Y| N| 成绩ID|
| GRADE_USER_GRADE | int | 11 | N| N| 分数|
| GRADE_RIGHT_ITEM_IDS | longtext |  | N| N| 所有正确答案组成的字符串|
| GRADE_ERROR_ITEM_IDS | longtext |  | N| N| 所有错误答案组成的字符串|
| GRADE_USER_ID | int | 11 | N| N| 用户ID（得分者）|


## 说明
- 可直接运行DriverAdminApplication
- 其中用户中密码需要经过明文的base64编码
- 题库可通过内置爬虫获取数据
  - 启动项目后，访问http://127.0.0.1:8880/spider可激活爬虫程序