use MISDB
go

-- select 'PK_' + upper('tResource')

/*
if object_id('tUser', 'U') is not null
    drop table tUser
go
*/

create table tUser
(
  id nvarchar(19) not null,

  userNo nvarchar(20) not null,
  userName nvarchar(40) not null,
  password nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TUSER primary key(id)
)
go

create unique index uq_tuser
  on tUser(userNo)
go

/*
if object_id('tLanguage', 'U') is not null
    drop table tLanguage
go
*/

create table tLanguage
(
  id nvarchar(19) not null,

  langNo nvarchar(20) not null,
  langName nvarchar(40) not null,
  chineseName nvarchar(40) not null,
  areUsing bit not null default(1),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TLANGUAGE primary key(id)
)
go

create unique index uq_tLanguage
  on tLanguage(langNo)
go

/*
if object_id('tMenu', 'U') is not null
    drop table tMenu
go
*/

create table tMenu
(
  id nvarchar(19) not null,

  menuNo nvarchar(20) not null,
  parentMenuId nvarchar(19) not null,

  iconUrl nvarchar(200) not null default(''),
  menuClientId nvarchar(19) not null,
  areCatelog bit not null default(0),
  menuLevel tinyint not null,
  seq tinyint not null,
  hqDisplay bit not null default(1),
  shopDisplay bit not null default(1),
  dcDisplay bit not null default(1),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TMENU primary key(id)
)
go

create unique index uq_tMenu
  on tMenu(menuNo)
go

/*
if object_id('tMenuLang', 'U') is not null
    drop table tMenuLang
go
*/

create table tMenuLang
(
  id nvarchar(19) not null,

  menuNo nvarchar(19) not null,
  langNo nvarchar(19) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TMENULANG primary key(id)
)
go

create unique index uq_tMenuLang
  on tMenuLang(menuNo, langNo)
go

/*
if object_id('tCommonType', 'U') is not null
    drop table tCommonType
go
*/

create table tCommonType
(
  id nvarchar(19) not null,

  comTypeNo nvarchar(20) not null,
  canAdd bit not null default(0),
  canMdf bit not null default(0),
  canDel bit not null default(0),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

constraint PK_TCOMMONTYPE primary key(id)
)
go

create unique index uq_tCommonType
  on tCommonType(comTypeNo)
go

/*
if object_id('tCommonTypeLang', 'U') is not null
    drop table tCommonTypeLang
go
*/

create table tCommonTypeLang
(
  id nvarchar(19) not null,

  comTypeNo nvarchar(20) not null,
  langNo nvarchar(20) not null,

  comTypeName nvarchar(40) not null,
  comTypeDesc nvarchar(200) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TCOMMONTYPELANG primary key(id)
)
go

create unique index uq_tCommonTypeLang
  on tCommonTypeLang(comTypeNo, langNo)
go

/*
if object_id('tCommon', 'U') is not null
    drop table tCommon
go
*/

create table tCommon
(
  id nvarchar(19) not null,

  comNo nvarchar(20) not null,

  comTypeNo nvarchar(19) not null,
  num1 numeric(16,2) null,
  text1 nvarchar(40) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TCOMMON primary key(id)
)
go

create unique index uq_tCommon
  on tCommon(comNo)
go

/*
if object_id('tCommonLang', 'U') is not null
    drop table tCommonLang
go
*/

create table tCommonLang
(
  id nvarchar(19) not null,

  comNo nvarchar(20) not null,
  langNo nvarchar(20) not null,

  comName nvarchar(40) not null,
  comDesc nvarchar(200) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TCOMMONLANG primary key(id)
)
go

create unique index uq_tCommon
  on tCommonLang(comNo, langNo)
go
