# 伙伴匹配

**简介**:伙伴匹配

**HOST**:127.0.0.1:8081

**联系人**:

**Version**:1.0

**接口路径**:/v2/api-docs

[TOC]

# team-controller

**部分SQL语句：**

```sql
--队伍表
create table team
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(256)                       not null comment '队伍名称',
    description varchar(1024)                      null comment '描述',
    maxNum      int      default 1                 not null comment '最大人数',
    expireTime  datetime                           null comment '过期时间',
    userId      bigint comment '用户id',
    status      int      default 0                 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512)                       null comment '密码',

    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete    tinyint  default 0                 not null comment '是否删除'
) comment '队伍';
```

```sql
--用户队伍关系表
create table user_team
(
    id         bigint auto_increment comment 'id'
        primary key,
    userId     bigint comment '用户id',
    teamId     bigint comment '队伍id',
    joinTime   datetime                           null comment '加入时间',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '用户队伍关系';
```

```sql
--标签表
create table if not exists tag
(
    id         bigint auto_increment comment '主键ID' primary key,
    tagName    varchar(256)                       null comment '标签名称',
    userId     bigint                             null comment '用户ID',
    parentId   bigint                             null comment '父标签ID',
    isParent   tinyint                            null comment '0-不是父标签,1-是父标签',
    creatTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    isDelete   tinyint  default 0                 null comment '逻辑删除',
    constraint uniIdx_tagName unique (tagName)
)
    comment '标签';
create index idx_userId
    on tag (userId);
```

## addTeam

**接口地址**:`/api/team/add`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:

1，用到事务注解，保证数据库操作的一致性![screen-capture](./../Documents/WeChat Files/wxid_mbthf20smolc12/FileStorage/File/2023-10/5a451258dcfa10cb67652cd080b31c46.png)

这里假如出现插入队伍消息到队伍表成功，但是服务器宕机了，此时就进行不了插入用户到用户队伍关系表，而造成脏数据。

但是加了事务注解之后，控制数据库操作的一致性，若任意一个操作不成功，则出发回滚，到原始的状态

2，这里还自定义了一个请求类TeamAddRequest（含Team的部分属性），方便前端向后端传输数据，但存到数据库时要将TeamAddRequest转换为Team类型。

```java
BeanUtils.copyProperties(teamAddRequest,team);
```

<br/>

<br/>


**请求示例**:

```json
{
  "description": "",
  "expireTime": "",
  "maxNum": 0,
  "name": "",
  "password": "",
  "status": 0,
  "userId": 0
}
```

**请求参数**:

| 参数名称                    | 参数说明           | 请求类型 | 是否必须  | 数据类型           | schema         |
|-------------------------|----------------|------|-------|----------------|----------------|
| teamAddRequest          | teamAddRequest | body | true  | TeamAddRequest | TeamAddRequest |
| &emsp;&emsp;description |                |      | false | string         |                |
| &emsp;&emsp;expireTime  |                |      | false | string         |                |
| &emsp;&emsp;maxNum      |                |      | false | integer        |                |
| &emsp;&emsp;name        |                |      | false | string         |                |
| &emsp;&emsp;password    |                |      | false | string         |                |
| &emsp;&emsp;status      |                |      | false | integer        |                |
| &emsp;&emsp;userId      |                |      | false | integer        |                |

**响应状态**:

| 状态码 | 说明           | schema             |
|-----|--------------|--------------------| 
| 200 | OK           | BaseResponse«long» |
| 201 | Created      |                    |
| 401 | Unauthorized |                    |
| 403 | Forbidden    |                    |
| 404 | Not Found    |                    |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | integer(int64) | integer(int64) |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": 0,
  "description": "",
  "message": ""
}
```

## deleteTeam

**接口地址**:`/api/team/delete`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:1，根据队伍id删除team数据，以及用户队伍关联表相应的数据。

**请求参数**:

| 参数名称 | 参数说明 | 请求类型  | 是否必须  | 数据类型           | schema |
|------|------|-------|-------|----------------|--------|
| id   | id   | query | false | integer(int64) |        |

**响应状态**:

| 状态码 | 说明           | schema                |
|-----|--------------|-----------------------| 
| 200 | OK           | BaseResponse«boolean» |
| 201 | Created      |                       |
| 401 | Unauthorized |                       |
| 403 | Forbidden    |                       |
| 404 | Not Found    |                       |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | boolean        |                |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": true,
  "description": "",
  "message": ""
}
```

## getTeamById

**接口地址**:`/api/team/get`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

**请求参数**:

| 参数名称 | 参数说明 | 请求类型  | 是否必须  | 数据类型           | schema |
|------|------|-------|-------|----------------|--------|
| id   | id   | query | false | integer(int64) |        |

**响应状态**:

| 状态码 | 说明           | schema             |
|-----|--------------|--------------------| 
| 200 | OK           | BaseResponse«Team» |
| 401 | Unauthorized |                    |
| 403 | Forbidden    |                    |
| 404 | Not Found    |                    |

**响应参数**:

| 参数名称                    | 参数说明 | 类型             | schema         |
|-------------------------|------|----------------|----------------| 
| code                    |      | integer(int32) | integer(int32) |
| data                    |      | Team           | Team           |
| &emsp;&emsp;createTime  |      | string         |                |
| &emsp;&emsp;description |      | string         |                |
| &emsp;&emsp;expireTime  |      | string         |                |
| &emsp;&emsp;id          |      | integer        |                |
| &emsp;&emsp;isDelete    |      | integer        |                |
| &emsp;&emsp;maxNum      |      | integer        |                |
| &emsp;&emsp;name        |      | string         |                |
| &emsp;&emsp;password    |      | string         |                |
| &emsp;&emsp;status      |      | integer        |                |
| &emsp;&emsp;updateTime  |      | string         |                |
| &emsp;&emsp;userId      |      | integer        |                |
| description             |      | string         |                |
| message                 |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": {
    "createTime": "",
    "description": "",
    "expireTime": "",
    "id": 0,
    "isDelete": 0,
    "maxNum": 0,
    "name": "",
    "password": "",
    "status": 0,
    "updateTime": "",
    "userId": 0
  },
  "description": "",
  "message": ""
}
```

## joinTeam

**接口地址**:`/api/team/join`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:1，这里加了锁，避免用户疯狂点击带来的印象（类似高并发），加锁，控制只有一个线程才能获取到锁，抢到锁并执行加入队伍等操作。

**请求示例**:

```json
{
  "password": "",
  "teamId": 0
}
```

**请求参数**:

| 参数名称                 | 参数说明            | 请求类型 | 是否必须  | 数据类型            | schema          |
|----------------------|-----------------|------|-------|-----------------|-----------------|
| teamJoinRequest      | teamJoinRequest | body | true  | TeamJoinRequest | TeamJoinRequest |
| &emsp;&emsp;password |                 |      | false | string          |                 |
| &emsp;&emsp;teamId   |                 |      | false | integer         |                 |

**响应状态**:

| 状态码 | 说明           | schema                |
|-----|--------------|-----------------------| 
| 200 | OK           | BaseResponse«boolean» |
| 201 | Created      |                       |
| 401 | Unauthorized |                       |
| 403 | Forbidden    |                       |
| 404 | Not Found    |                       |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | boolean        |                |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": true,
  "description": "",
  "message": ""
}
```

## listTeams

**接口地址**:`/api/team/list`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

1，定义一个TeamUserVO（TeamUser的部分属性，脱敏以及去掉不需要的属性），对

TeamUser的包装，传递给前端的信息。

==2,问题==

1，查不出加密房间

<br/>


**请求参数**:

| 参数名称        | 参数说明 | 请求类型  | 是否必须  | 数据类型           | schema  |
|-------------|------|-------|-------|----------------|---------|
| description |      | query | false | string         |         |
| id          |      | query | false | integer(int64) |         |
| idList      |      | query | false | array          | integer |
| maxNum      |      | query | false | integer(int32) |         |
| name        |      | query | false | string         |         |
| pageNum     |      | query | false | integer(int32) |         |
| pageSize    |      | query | false | integer(int32) |         |
| searchText  |      | query | false | string         |         |
| status      |      | query | false | integer(int32) |         |
| userId      |      | query | false | integer(int64) |         |

**响应状态**:

| 状态码 | 说明           | schema                         |
|-----|--------------|--------------------------------| 
| 200 | OK           | BaseResponse«List«TeamUserVO»» |
| 401 | Unauthorized |                                |
| 403 | Forbidden    |                                |
| 404 | Not Found    |                                |

**响应参数**:

| 参数名称                                | 参数说明 | 类型             | schema         |
|-------------------------------------|------|----------------|----------------| 
| code                                |      | integer(int32) | integer(int32) |
| data                                |      | array          | TeamUserVO     |
| &emsp;&emsp;createTime              |      | string         |                |
| &emsp;&emsp;description             |      | string         |                |
| &emsp;&emsp;expireTime              |      | string         |                |
| &emsp;&emsp;hasJoin                 |      | boolean        |                |
| &emsp;&emsp;hasJoinNum              |      | integer        |                |
| &emsp;&emsp;id                      |      | integer        |                |
| &emsp;&emsp;maxNum                  |      | integer        |                |
| &emsp;&emsp;name                    |      | string         |                |
| &emsp;&emsp;status                  |      | integer        |                |
| &emsp;&emsp;updateTime              |      | string         |                |
| &emsp;&emsp;userId                  |      | integer        |                |
| &emsp;&emsp;userVO                  |      | UserVO         | UserVO         |
| &emsp;&emsp;&emsp;&emsp;avatarUrl   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;creatTime   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;email       |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;gender      |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;id          |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;phone       |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;planetCode  |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;profile     |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;tags        |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;updateTime  |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userAccount |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userRole    |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;userStatus  |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;username    |      | string         |                |
| description                         |      | string         |                |
| message                             |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": [
    {
      "createTime": "",
      "description": "",
      "expireTime": "",
      "hasJoin": true,
      "hasJoinNum": 0,
      "id": 0,
      "maxNum": 0,
      "name": "",
      "status": 0,
      "updateTime": "",
      "userId": 0,
      "userVO": {
        "avatarUrl": "",
        "creatTime": "",
        "email": "",
        "gender": 0,
        "id": 0,
        "phone": "",
        "planetCode": "",
        "profile": "",
        "tags": "",
        "updateTime": "",
        "userAccount": "",
        "userRole": 0,
        "userStatus": 0,
        "username": ""
      }
    }
  ],
  "description": "",
  "message": ""
}
```

## listMyCreateTeams

**接口地址**:`/api/team/list/my/create`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

**请求参数**:

| 参数名称        | 参数说明 | 请求类型  | 是否必须  | 数据类型           | schema  |
|-------------|------|-------|-------|----------------|---------|
| description |      | query | false | string         |         |
| id          |      | query | false | integer(int64) |         |
| idList      |      | query | false | array          | integer |
| maxNum      |      | query | false | integer(int32) |         |
| name        |      | query | false | string         |         |
| pageNum     |      | query | false | integer(int32) |         |
| pageSize    |      | query | false | integer(int32) |         |
| searchText  |      | query | false | string         |         |
| status      |      | query | false | integer(int32) |         |
| userId      |      | query | false | integer(int64) |         |

**响应状态**:

| 状态码 | 说明           | schema                         |
|-----|--------------|--------------------------------| 
| 200 | OK           | BaseResponse«List«TeamUserVO»» |
| 401 | Unauthorized |                                |
| 403 | Forbidden    |                                |
| 404 | Not Found    |                                |

**响应参数**:

| 参数名称                                | 参数说明 | 类型             | schema         |
|-------------------------------------|------|----------------|----------------| 
| code                                |      | integer(int32) | integer(int32) |
| data                                |      | array          | TeamUserVO     |
| &emsp;&emsp;createTime              |      | string         |                |
| &emsp;&emsp;description             |      | string         |                |
| &emsp;&emsp;expireTime              |      | string         |                |
| &emsp;&emsp;hasJoin                 |      | boolean        |                |
| &emsp;&emsp;hasJoinNum              |      | integer        |                |
| &emsp;&emsp;id                      |      | integer        |                |
| &emsp;&emsp;maxNum                  |      | integer        |                |
| &emsp;&emsp;name                    |      | string         |                |
| &emsp;&emsp;status                  |      | integer        |                |
| &emsp;&emsp;updateTime              |      | string         |                |
| &emsp;&emsp;userId                  |      | integer        |                |
| &emsp;&emsp;userVO                  |      | UserVO         | UserVO         |
| &emsp;&emsp;&emsp;&emsp;avatarUrl   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;creatTime   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;email       |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;gender      |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;id          |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;phone       |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;planetCode  |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;profile     |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;tags        |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;updateTime  |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userAccount |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userRole    |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;userStatus  |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;username    |      | string         |                |
| description                         |      | string         |                |
| message                             |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": [
    {
      "createTime": "",
      "description": "",
      "expireTime": "",
      "hasJoin": true,
      "hasJoinNum": 0,
      "id": 0,
      "maxNum": 0,
      "name": "",
      "status": 0,
      "updateTime": "",
      "userId": 0,
      "userVO": {
        "avatarUrl": "",
        "creatTime": "",
        "email": "",
        "gender": 0,
        "id": 0,
        "phone": "",
        "planetCode": "",
        "profile": "",
        "tags": "",
        "updateTime": "",
        "userAccount": "",
        "userRole": 0,
        "userStatus": 0,
        "username": ""
      }
    }
  ],
  "description": "",
  "message": ""
}
```

## listMyJoinTeams

**接口地址**:`/api/team/list/my/join`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

**请求参数**:

| 参数名称        | 参数说明 | 请求类型  | 是否必须  | 数据类型           | schema  |
|-------------|------|-------|-------|----------------|---------|
| description |      | query | false | string         |         |
| id          |      | query | false | integer(int64) |         |
| idList      |      | query | false | array          | integer |
| maxNum      |      | query | false | integer(int32) |         |
| name        |      | query | false | string         |         |
| pageNum     |      | query | false | integer(int32) |         |
| pageSize    |      | query | false | integer(int32) |         |
| searchText  |      | query | false | string         |         |
| status      |      | query | false | integer(int32) |         |
| userId      |      | query | false | integer(int64) |         |

**响应状态**:

| 状态码 | 说明           | schema                         |
|-----|--------------|--------------------------------| 
| 200 | OK           | BaseResponse«List«TeamUserVO»» |
| 401 | Unauthorized |                                |
| 403 | Forbidden    |                                |
| 404 | Not Found    |                                |

**响应参数**:

| 参数名称                                | 参数说明 | 类型             | schema         |
|-------------------------------------|------|----------------|----------------| 
| code                                |      | integer(int32) | integer(int32) |
| data                                |      | array          | TeamUserVO     |
| &emsp;&emsp;createTime              |      | string         |                |
| &emsp;&emsp;description             |      | string         |                |
| &emsp;&emsp;expireTime              |      | string         |                |
| &emsp;&emsp;hasJoin                 |      | boolean        |                |
| &emsp;&emsp;hasJoinNum              |      | integer        |                |
| &emsp;&emsp;id                      |      | integer        |                |
| &emsp;&emsp;maxNum                  |      | integer        |                |
| &emsp;&emsp;name                    |      | string         |                |
| &emsp;&emsp;status                  |      | integer        |                |
| &emsp;&emsp;updateTime              |      | string         |                |
| &emsp;&emsp;userId                  |      | integer        |                |
| &emsp;&emsp;userVO                  |      | UserVO         | UserVO         |
| &emsp;&emsp;&emsp;&emsp;avatarUrl   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;creatTime   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;email       |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;gender      |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;id          |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;phone       |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;planetCode  |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;profile     |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;tags        |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;updateTime  |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userAccount |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userRole    |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;userStatus  |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;username    |      | string         |                |
| description                         |      | string         |                |
| message                             |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": [
    {
      "createTime": "",
      "description": "",
      "expireTime": "",
      "hasJoin": true,
      "hasJoinNum": 0,
      "id": 0,
      "maxNum": 0,
      "name": "",
      "status": 0,
      "updateTime": "",
      "userId": 0,
      "userVO": {
        "avatarUrl": "",
        "creatTime": "",
        "email": "",
        "gender": 0,
        "id": 0,
        "phone": "",
        "planetCode": "",
        "profile": "",
        "tags": "",
        "updateTime": "",
        "userAccount": "",
        "userRole": 0,
        "userStatus": 0,
        "username": ""
      }
    }
  ],
  "description": "",
  "message": ""
}
```

## listTeamsByPage

**接口地址**:`/api/team/list/page`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

**请求参数**:

| 参数名称        | 参数说明 | 请求类型  | 是否必须  | 数据类型           | schema  |
|-------------|------|-------|-------|----------------|---------|
| description |      | query | false | string         |         |
| id          |      | query | false | integer(int64) |         |
| idList      |      | query | false | array          | integer |
| maxNum      |      | query | false | integer(int32) |         |
| name        |      | query | false | string         |         |
| pageNum     |      | query | false | integer(int32) |         |
| pageSize    |      | query | false | integer(int32) |         |
| searchText  |      | query | false | string         |         |
| status      |      | query | false | integer(int32) |         |
| userId      |      | query | false | integer(int64) |         |

**响应状态**:

| 状态码 | 说明           | schema                   |
|-----|--------------|--------------------------| 
| 200 | OK           | BaseResponse«Page«Team»» |
| 401 | Unauthorized |                          |
| 403 | Forbidden    |                          |
| 404 | Not Found    |                          |

**响应参数**:

| 参数名称                            | 参数说明 | 类型           | schema         |
| ----------------------------------- | -------- | -------------- | -------------- |
| code                                |          | integer(int32) | integer(int32) |
| data                                |          | Page«Team»     | Page«Team»     |
| &emsp;&emsp;countId                 |          | string         |                |
| &emsp;&emsp;current                 |          | integer        |                |
| &emsp;&emsp;maxLimit                |          | integer        |                |
| &emsp;&emsp;optimizeCountSql        |          | boolean        |                |
| &emsp;&emsp;orders                  |          | array          | OrderItem      |
| &emsp;&emsp;&emsp;&emsp;asc         |          | boolean        |                |
| &emsp;&emsp;&emsp;&emsp;column      |          | string         |                |
| &emsp;&emsp;pages                   |          | integer        |                |
| &emsp;&emsp;records                 |          | array          | Team           |
| &emsp;&emsp;&emsp;&emsp;createTime  |          | string         |                |
| &emsp;&emsp;&emsp;&emsp;description |          | string         |                |
| &emsp;&emsp;&emsp;&emsp;expireTime  |          | string         |                |
| &emsp;&emsp;&emsp;&emsp;id          |          | integer        |                |
| &emsp;&emsp;&emsp;&emsp;isDelete    |          | integer        |                |
| &emsp;&emsp;&emsp;&emsp;maxNum      |          | integer        |                |
| &emsp;&emsp;&emsp;&emsp;name        |          | string         |                |
| &emsp;&emsp;&emsp;&emsp;password    |          | string         |                |
| &emsp;&emsp;&emsp;&emsp;status      |          | integer        |                |
| &emsp;&emsp;&emsp;&emsp;updateTime  |          | string         |                |
| &emsp;&emsp;&emsp;&emsp;userId      |          | integer        |                |
| &emsp;&emsp;searchCount             |          | boolean        |                |
| &emsp;&emsp;size                    |          | integer        |                |
| &emsp;&emsp;total                   |          | integer        |                |
| description                         |          | string         |                |
| message                             |          | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": {
    "countId": "",
    "current": 0,
    "maxLimit": 0,
    "optimizeCountSql": true,
    "orders": [
      {
        "asc": true,
        "column": ""
      }
    ],
    "pages": 0,
    "records": [
      {
        "createTime": "",
        "description": "",
        "expireTime": "",
        "id": 0,
        "isDelete": 0,
        "maxNum": 0,
        "name": "",
        "password": "",
        "status": 0,
        "updateTime": "",
        "userId": 0
      }
    ],
    "searchCount": true,
    "size": 0,
    "total": 0
  },
  "description": "",
  "message": ""
}
```

## quitTeam

**接口地址**:`/api/team/quit`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:

**请求示例**:

```json
{
  "teamId": 0
}
```

**请求参数**:

| 参数名称               | 参数说明            | 请求类型 | 是否必须  | 数据类型            | schema          |
|--------------------|-----------------|------|-------|-----------------|-----------------|
| teamQuitRequest    | teamQuitRequest | body | true  | TeamQuitRequest | TeamQuitRequest |
| &emsp;&emsp;teamId |                 |      | false | integer         |                 |

**响应状态**:

| 状态码 | 说明           | schema                |
|-----|--------------|-----------------------| 
| 200 | OK           | BaseResponse«boolean» |
| 201 | Created      |                       |
| 401 | Unauthorized |                       |
| 403 | Forbidden    |                       |
| 404 | Not Found    |                       |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | boolean        |                |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": true,
  "description": "",
  "message": ""
}
```

## updateTeam

**接口地址**:`/api/team/update`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:

1，包装一个updateTeamRequest请求类，方便前端传递参数

2，仅管理员和队伍创建者可修改

**请求示例**:

```json
{
  "description": "",
  "expireTime": "",
  "id": 0,
  "name": "",
  "password": "",
  "status": 0
}
```

**请求参数**:

| 参数名称                    | 参数说明              | 请求类型 | 是否必须  | 数据类型              | schema            |
|-------------------------|-------------------|------|-------|-------------------|-------------------|
| teamUpdateRequest       | teamUpdateRequest | body | true  | TeamUpdateRequest | TeamUpdateRequest |
| &emsp;&emsp;description |                   |      | false | string            |                   |
| &emsp;&emsp;expireTime  |                   |      | false | string            |                   |
| &emsp;&emsp;id          |                   |      | false | integer           |                   |
| &emsp;&emsp;name        |                   |      | false | string            |                   |
| &emsp;&emsp;password    |                   |      | false | string            |                   |
| &emsp;&emsp;status      |                   |      | false | integer           |                   |

**响应状态**:

| 状态码 | 说明           | schema                |
|-----|--------------|-----------------------| 
| 200 | OK           | BaseResponse«boolean» |
| 201 | Created      |                       |
| 401 | Unauthorized |                       |
| 403 | Forbidden    |                       |
| 404 | Not Found    |                       |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | boolean        |                |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": true,
  "description": "",
  "message": ""
}
```

# user-controller

## getCurrentUser

**接口地址**:`/api/user/current`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

1，主要是提供一个接口来获取当前用户信息，方便前端展示个人信息

**请求参数**:

暂无

**响应状态**:

| 状态码 | 说明           | schema             |
|-----|--------------|--------------------| 
| 200 | OK           | BaseResponse«User» |
| 401 | Unauthorized |                    |
| 403 | Forbidden    |                    |
| 404 | Not Found    |                    |

**响应参数**:

| 参数名称                     | 参数说明 | 类型             | schema         |
|--------------------------|------|----------------|----------------| 
| code                     |      | integer(int32) | integer(int32) |
| data                     |      | User           | User           |
| &emsp;&emsp;avatarUrl    |      | string         |                |
| &emsp;&emsp;creatTime    |      | string         |                |
| &emsp;&emsp;email        |      | string         |                |
| &emsp;&emsp;gender       |      | integer        |                |
| &emsp;&emsp;id           |      | integer        |                |
| &emsp;&emsp;isDelete     |      | integer        |                |
| &emsp;&emsp;phone        |      | string         |                |
| &emsp;&emsp;planetCode   |      | string         |                |
| &emsp;&emsp;profile      |      | string         |                |
| &emsp;&emsp;tags         |      | string         |                |
| &emsp;&emsp;updateTime   |      | string         |                |
| &emsp;&emsp;userAccount  |      | string         |                |
| &emsp;&emsp;userPassword |      | string         |                |
| &emsp;&emsp;userRole     |      | integer        |                |
| &emsp;&emsp;userStatus   |      | integer        |                |
| &emsp;&emsp;username     |      | string         |                |
| description              |      | string         |                |
| message                  |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": {
    "avatarUrl": "",
    "creatTime": "",
    "email": "",
    "gender": 0,
    "id": 0,
    "isDelete": 0,
    "phone": "",
    "planetCode": "",
    "profile": "",
    "tags": "",
    "updateTime": "",
    "userAccount": "",
    "userPassword": "",
    "userRole": 0,
    "userStatus": 0,
    "username": ""
  },
  "description": "",
  "message": ""
}
```

## deleteUser

**接口地址**:`/api/user/delete`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:

1，根据Id删除用户，只有管理员可删除用户

2，这里将是否为管理员这个校验提取为一个方法，实现：从request的session中获取当前用户，查看当前user的userRole属性是否为1。

**请求参数**:

| 参数名称 | 参数说明 | 请求类型  | 是否必须 | 数据类型           | schema |
|------|------|-------|------|----------------|--------|
| id   | id   | query | true | integer(int64) |        |

**响应状态**:

| 状态码 | 说明           | schema                |
|-----|--------------|-----------------------| 
| 200 | OK           | BaseResponse«boolean» |
| 201 | Created      |                       |
| 401 | Unauthorized |                       |
| 403 | Forbidden    |                       |
| 404 | Not Found    |                       |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | boolean        |                |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": true,
  "description": "",
  "message": ""
}
```

## userLogin

**接口地址**:`/api/user/login`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:

1，将请求体封装为UserLoginRequest包装类，方便前端传递参数。

2,对用户进行脱敏，密码等敏感信息就不传递给前端了，这里是定义了一个方法脱敏

getSafetyUser，其实可以定义一个vo对象，对象不设置密码等熟悉，给前端传递这个vo对象，其实效果是一样的。

2，将登陆态存入Session中，默认存在服务器中，压力较大，可以指定位置存储，一般写在redis中，在配置文件中设置session过期时间及存储位置（redis），超过这个时间后，用户再请求页面，就要再重新登录。

USER_LOGIN_STAT为自定义的一个session属性的名称，==不是SessionId==，其是用来存储user的，这里将其提取为常量，避免出错。

**请求示例**:

```json
{
  "userAccount": "",
  "userPassword": ""
}
```

**请求参数**:

| 参数名称                     | 参数说明             | 请求类型 | 是否必须  | 数据类型             | schema           |
|--------------------------|------------------|------|-------|------------------|------------------|
| userLoginRequest         | userLoginRequest | body | true  | UserLoginRequest | UserLoginRequest |
| &emsp;&emsp;userAccount  |                  |      | false | string           |                  |
| &emsp;&emsp;userPassword |                  |      | false | string           |                  |

**响应状态**:

| 状态码 | 说明           | schema             |
|-----|--------------|--------------------| 
| 200 | OK           | BaseResponse«User» |
| 201 | Created      |                    |
| 401 | Unauthorized |                    |
| 403 | Forbidden    |                    |
| 404 | Not Found    |                    |

**响应参数**:

| 参数名称                     | 参数说明 | 类型             | schema         |
|--------------------------|------|----------------|----------------| 
| code                     |      | integer(int32) | integer(int32) |
| data                     |      | User           | User           |
| &emsp;&emsp;avatarUrl    |      | string         |                |
| &emsp;&emsp;creatTime    |      | string         |                |
| &emsp;&emsp;email        |      | string         |                |
| &emsp;&emsp;gender       |      | integer        |                |
| &emsp;&emsp;id           |      | integer        |                |
| &emsp;&emsp;isDelete     |      | integer        |                |
| &emsp;&emsp;phone        |      | string         |                |
| &emsp;&emsp;planetCode   |      | string         |                |
| &emsp;&emsp;profile      |      | string         |                |
| &emsp;&emsp;tags         |      | string         |                |
| &emsp;&emsp;updateTime   |      | string         |                |
| &emsp;&emsp;userAccount  |      | string         |                |
| &emsp;&emsp;userPassword |      | string         |                |
| &emsp;&emsp;userRole     |      | integer        |                |
| &emsp;&emsp;userStatus   |      | integer        |                |
| &emsp;&emsp;username     |      | string         |                |
| description              |      | string         |                |
| message                  |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": {
    "avatarUrl": "",
    "creatTime": "",
    "email": "",
    "gender": 0,
    "id": 0,
    "isDelete": 0,
    "phone": "",
    "planetCode": "",
    "profile": "",
    "tags": "",
    "updateTime": "",
    "userAccount": "",
    "userPassword": "",
    "userRole": 0,
    "userStatus": 0,
    "username": ""
  },
  "description": "",
  "message": ""
}
```

## logOut

**接口地址**:`/api/user/logout`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:1，提供一个接口满足用户退出登录。

2，实现：移除用户登陆态即可。

**请求参数**:

暂无

**响应状态**:

| 状态码 | 说明           | schema            |
|-----|--------------|-------------------| 
| 200 | OK           | BaseResponse«int» |
| 201 | Created      |                   |
| 401 | Unauthorized |                   |
| 403 | Forbidden    |                   |
| 404 | Not Found    |                   |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | integer(int32) | integer(int32) |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": 0,
  "description": "",
  "message": ""
}
```

## matchUsers—

**接口地址**:`/api/user/match`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

1.提供一个推荐和用户相似度最匹配的前top N 的用户（前端的心动模式）

2，用到编辑距离算法

- 编辑距离算法(找出最相似的字符串)
    - 原理：https://blog.csdn.net/DBC_121/article/details/104198838
    - 比如字符串A，B。A->B需要多少步，返回值为步数
    - 一次操作要求：插入一个字符 或 删除一个字符 或 替换一个字符
    - 这里的字符串改成

==问题：==

userTags内容是空的，但是整个数据不是为空，而是“[]
”，所以不管isEmpty，isBlank啥的，都是返回的true，但是你看，比如用户原本有标签，但是他把自己所有的标签都删了，数据不就是“[]
”这个么，那还是能把他查出来

**请求参数**:

| 参数名称 | 参数说明 | 请求类型  | 是否必须  | 数据类型           | schema |
|------|------|-------|-------|----------------|--------|
| num  | num  | query | false | integer(int64) |        |

**响应状态**:

| 状态码 | 说明           | schema                   |
|-----|--------------|--------------------------| 
| 200 | OK           | BaseResponse«List«User»» |
| 401 | Unauthorized |                          |
| 403 | Forbidden    |                          |
| 404 | Not Found    |                          |

**响应参数**:

| 参数名称                     | 参数说明 | 类型             | schema         |
|--------------------------|------|----------------|----------------| 
| code                     |      | integer(int32) | integer(int32) |
| data                     |      | array          | User           |
| &emsp;&emsp;avatarUrl    |      | string         |                |
| &emsp;&emsp;creatTime    |      | string         |                |
| &emsp;&emsp;email        |      | string         |                |
| &emsp;&emsp;gender       |      | integer        |                |
| &emsp;&emsp;id           |      | integer        |                |
| &emsp;&emsp;isDelete     |      | integer        |                |
| &emsp;&emsp;phone        |      | string         |                |
| &emsp;&emsp;planetCode   |      | string         |                |
| &emsp;&emsp;profile      |      | string         |                |
| &emsp;&emsp;tags         |      | string         |                |
| &emsp;&emsp;updateTime   |      | string         |                |
| &emsp;&emsp;userAccount  |      | string         |                |
| &emsp;&emsp;userPassword |      | string         |                |
| &emsp;&emsp;userRole     |      | integer        |                |
| &emsp;&emsp;userStatus   |      | integer        |                |
| &emsp;&emsp;username     |      | string         |                |
| description              |      | string         |                |
| message                  |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": [
    {
      "avatarUrl": "",
      "creatTime": "",
      "email": "",
      "gender": 0,
      "id": 0,
      "isDelete": 0,
      "phone": "",
      "planetCode": "",
      "profile": "",
      "tags": "",
      "updateTime": "",
      "userAccount": "",
      "userPassword": "",
      "userRole": 0,
      "userStatus": 0,
      "username": ""
    }
  ],
  "description": "",
  "message": ""
}
```

## recommendUsers—

**接口地址**:`/api/user/recommend`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

1，这是一个展示所有用户的接口，方便前端在主页展示

2，这里用到了redis缓存，定时任务，分布式锁，定时到用户使用较少的时间段，进行缓存预热

**请求参数**:

| 参数名称     | 参数说明     | 请求类型  | 是否必须  | 数据类型           | schema |
|----------|----------|-------|-------|----------------|--------|
| pageNum  | pageNum  | query | false | integer(int64) |        |
| pageSize | pageSize | query | false | integer(int64) |        |

**响应状态**:

| 状态码 | 说明           | schema                   |
|-----|--------------|--------------------------| 
| 200 | OK           | BaseResponse«Page«User»» |
| 401 | Unauthorized |                          |
| 403 | Forbidden    |                          |
| 404 | Not Found    |                          |

**响应参数**:

| 参数名称                                 | 参数说明 | 类型             | schema         |
|--------------------------------------|------|----------------|----------------| 
| code                                 |      | integer(int32) | integer(int32) |
| data                                 |      | Page«User»     | Page«User»     |
| &emsp;&emsp;countId                  |      | string         |                |
| &emsp;&emsp;current                  |      | integer        |                |
| &emsp;&emsp;maxLimit                 |      | integer        |                |
| &emsp;&emsp;optimizeCountSql         |      | boolean        |                |
| &emsp;&emsp;orders                   |      | array          | OrderItem      |
| &emsp;&emsp;&emsp;&emsp;asc          |      | boolean        |                |
| &emsp;&emsp;&emsp;&emsp;column       |      | string         |                |
| &emsp;&emsp;pages                    |      | integer        |                |
| &emsp;&emsp;records                  |      | array          | User           |
| &emsp;&emsp;&emsp;&emsp;avatarUrl    |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;creatTime    |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;email        |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;gender       |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;id           |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;isDelete     |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;phone        |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;planetCode   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;profile      |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;tags         |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;updateTime   |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userAccount  |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userPassword |      | string         |                |
| &emsp;&emsp;&emsp;&emsp;userRole     |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;userStatus   |      | integer        |                |
| &emsp;&emsp;&emsp;&emsp;username     |      | string         |                |
| &emsp;&emsp;searchCount              |      | boolean        |                |
| &emsp;&emsp;size                     |      | integer        |                |
| &emsp;&emsp;total                    |      | integer        |                |
| description                          |      | string         |                |
| message                              |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": {
    "countId": "",
    "current": 0,
    "maxLimit": 0,
    "optimizeCountSql": true,
    "orders": [
      {
        "asc": true,
        "column": ""
      }
    ],
    "pages": 0,
    "records": [
      {
        "avatarUrl": "",
        "creatTime": "",
        "email": "",
        "gender": 0,
        "id": 0,
        "isDelete": 0,
        "phone": "",
        "planetCode": "",
        "profile": "",
        "tags": "",
        "updateTime": "",
        "userAccount": "",
        "userPassword": "",
        "userRole": 0,
        "userStatus": 0,
        "username": ""
      }
    ],
    "searchCount": true,
    "size": 0,
    "total": 0
  },
  "description": "",
  "message": ""
}
```

## userRegister

**接口地址**:`/api/user/register`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:

1，将请求体封装为UserRegisterRequest包装类，方便前端传递参数。

2，其中有个账户名不含特殊字符的校验，有个对密码进行加密后存到数据库的操作

**请求示例**:

```json
{
  "checkPassword": "",
  "planetCode": "",
  "userAccount": "",
  "userPassword": ""
}
```

**请求参数**:

| 参数名称                      | 参数说明                | 请求类型 | 是否必须  | 数据类型                | schema              |
|---------------------------|---------------------|------|-------|---------------------|---------------------|
| userRegisterRequest       | userRegisterRequest | body | true  | UserRegisterRequest | UserRegisterRequest |
| &emsp;&emsp;checkPassword |                     |      | false | string              |                     |
| &emsp;&emsp;planetCode    |                     |      | false | string              |                     |
| &emsp;&emsp;userAccount   |                     |      | false | string              |                     |
| &emsp;&emsp;userPassword  |                     |      | false | string              |                     |

**响应状态**:

| 状态码 | 说明           | schema             |
|-----|--------------|--------------------| 
| 200 | OK           | BaseResponse«long» |
| 201 | Created      |                    |
| 401 | Unauthorized |                    |
| 403 | Forbidden    |                    |
| 404 | Not Found    |                    |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | integer(int64) | integer(int64) |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": 0,
  "description": "",
  "message": ""
}
```

## searchUsers

**接口地址**:`/api/user/search`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

1，这是针对用户中心的接口，仅管理员可查询，主要对用户进行管理。

**请求参数**:

| 参数名称     | 参数说明     | 请求类型  | 是否必须  | 数据类型   | schema |
|----------|----------|-------|-------|--------|--------|
| userName | userName | query | false | string |        |

**响应状态**:

| 状态码 | 说明           | schema                   |
|-----|--------------|--------------------------| 
| 200 | OK           | BaseResponse«List«User»» |
| 401 | Unauthorized |                          |
| 403 | Forbidden    |                          |
| 404 | Not Found    |                          |

**响应参数**:

| 参数名称                     | 参数说明 | 类型             | schema         |
|--------------------------|------|----------------|----------------| 
| code                     |      | integer(int32) | integer(int32) |
| data                     |      | array          | User           |
| &emsp;&emsp;avatarUrl    |      | string         |                |
| &emsp;&emsp;creatTime    |      | string         |                |
| &emsp;&emsp;email        |      | string         |                |
| &emsp;&emsp;gender       |      | integer        |                |
| &emsp;&emsp;id           |      | integer        |                |
| &emsp;&emsp;isDelete     |      | integer        |                |
| &emsp;&emsp;phone        |      | string         |                |
| &emsp;&emsp;planetCode   |      | string         |                |
| &emsp;&emsp;profile      |      | string         |                |
| &emsp;&emsp;tags         |      | string         |                |
| &emsp;&emsp;updateTime   |      | string         |                |
| &emsp;&emsp;userAccount  |      | string         |                |
| &emsp;&emsp;userPassword |      | string         |                |
| &emsp;&emsp;userRole     |      | integer        |                |
| &emsp;&emsp;userStatus   |      | integer        |                |
| &emsp;&emsp;username     |      | string         |                |
| description              |      | string         |                |
| message                  |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": [
    {
      "avatarUrl": "",
      "creatTime": "",
      "email": "",
      "gender": 0,
      "id": 0,
      "isDelete": 0,
      "phone": "",
      "planetCode": "",
      "profile": "",
      "tags": "",
      "updateTime": "",
      "userAccount": "",
      "userPassword": "",
      "userRole": 0,
      "userStatus": 0,
      "username": ""
    }
  ],
  "description": "",
  "message": ""
}
```

## searchUsersByTags

**接口地址**:`/api/user/search/tags`

**请求方式**:`GET`

**请求数据类型**:`application/x-www-form-urlencoded`

**响应数据类型**:`*/*`

**接口描述**:

1，提供一个可以根据标签查询用户的接口

2，有两种方法，sql查询，内存查询

sql查询：

优点：查询简单，直接一个in查询就搞定

缺点：数据库压力大，每次还要便利每一条数据的tags看看符不符合要求。

内存查询（本地分布式计算）

优点：对数据库进行解压，查出所有的数据，存到list里，对list操作，拿出需要的数据。

缺点：操作麻烦，占用内存。

**请求参数**:

| 参数名称        | 参数说明        | 请求类型  | 是否必须  | 数据类型  | schema |
|-------------|-------------|-------|-------|-------|--------|
| tagNameList | tagNameList | query | false | array | string |

**响应状态**:

| 状态码 | 说明           | schema                   |
|-----|--------------|--------------------------| 
| 200 | OK           | BaseResponse«List«User»» |
| 401 | Unauthorized |                          |
| 403 | Forbidden    |                          |
| 404 | Not Found    |                          |

**响应参数**:

| 参数名称                     | 参数说明 | 类型             | schema         |
|--------------------------|------|----------------|----------------| 
| code                     |      | integer(int32) | integer(int32) |
| data                     |      | array          | User           |
| &emsp;&emsp;avatarUrl    |      | string         |                |
| &emsp;&emsp;creatTime    |      | string         |                |
| &emsp;&emsp;email        |      | string         |                |
| &emsp;&emsp;gender       |      | integer        |                |
| &emsp;&emsp;id           |      | integer        |                |
| &emsp;&emsp;isDelete     |      | integer        |                |
| &emsp;&emsp;phone        |      | string         |                |
| &emsp;&emsp;planetCode   |      | string         |                |
| &emsp;&emsp;profile      |      | string         |                |
| &emsp;&emsp;tags         |      | string         |                |
| &emsp;&emsp;updateTime   |      | string         |                |
| &emsp;&emsp;userAccount  |      | string         |                |
| &emsp;&emsp;userPassword |      | string         |                |
| &emsp;&emsp;userRole     |      | integer        |                |
| &emsp;&emsp;userStatus   |      | integer        |                |
| &emsp;&emsp;username     |      | string         |                |
| description              |      | string         |                |
| message                  |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": [
    {
      "avatarUrl": "",
      "creatTime": "",
      "email": "",
      "gender": 0,
      "id": 0,
      "isDelete": 0,
      "phone": "",
      "planetCode": "",
      "profile": "",
      "tags": "",
      "updateTime": "",
      "userAccount": "",
      "userPassword": "",
      "userRole": 0,
      "userStatus": 0,
      "username": ""
    }
  ],
  "description": "",
  "message": ""
}
```

## updateUser

**接口地址**:`/api/user/update`

**请求方式**:`POST`

**请求数据类型**:`application/json`

**响应数据类型**:`*/*`

**接口描述**:1，仅管理员和用户自己可以修改信息，管理员，修改任意，自己只能修改自己

**请求示例**:

```json
{
  "avatarUrl": "",
  "creatTime": "",
  "email": "",
  "gender": 0,
  "id": 0,
  "isDelete": 0,
  "phone": "",
  "planetCode": "",
  "profile": "",
  "tags": "",
  "updateTime": "",
  "userAccount": "",
  "userPassword": "",
  "userRole": 0,
  "userStatus": 0,
  "username": ""
}
```

**请求参数**:

| 参数名称                     | 参数说明 | 请求类型 | 是否必须  | 数据类型    | schema |
|--------------------------|------|------|-------|---------|--------|
| user                     | user | body | true  | User    | User   |
| &emsp;&emsp;avatarUrl    |      |      | false | string  |        |
| &emsp;&emsp;creatTime    |      |      | false | string  |        |
| &emsp;&emsp;email        |      |      | false | string  |        |
| &emsp;&emsp;gender       |      |      | false | integer |        |
| &emsp;&emsp;id           |      |      | false | integer |        |
| &emsp;&emsp;isDelete     |      |      | false | integer |        |
| &emsp;&emsp;phone        |      |      | false | string  |        |
| &emsp;&emsp;planetCode   |      |      | false | string  |        |
| &emsp;&emsp;profile      |      |      | false | string  |        |
| &emsp;&emsp;tags         |      |      | false | string  |        |
| &emsp;&emsp;updateTime   |      |      | false | string  |        |
| &emsp;&emsp;userAccount  |      |      | false | string  |        |
| &emsp;&emsp;userPassword |      |      | false | string  |        |
| &emsp;&emsp;userRole     |      |      | false | integer |        |
| &emsp;&emsp;userStatus   |      |      | false | integer |        |
| &emsp;&emsp;username     |      |      | false | string  |        |

**响应状态**:

| 状态码 | 说明           | schema            |
|-----|--------------|-------------------| 
| 200 | OK           | BaseResponse«int» |
| 201 | Created      |                   |
| 401 | Unauthorized |                   |
| 403 | Forbidden    |                   |
| 404 | Not Found    |                   |

**响应参数**:

| 参数名称        | 参数说明 | 类型             | schema         |
|-------------|------|----------------|----------------| 
| code        |      | integer(int32) | integer(int32) |
| data        |      | integer(int32) | integer(int32) |
| description |      | string         |                |
| message     |      | string         |                |

**响应示例**:

```json
{
  "code": 0,
  "data": 0,
  "description": "",
  "message": ""
}
```