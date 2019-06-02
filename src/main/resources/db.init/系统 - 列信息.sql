use MISDB
go

/*
if object_id('tHeadColumn', 'U') is not null
    drop table tHeadColumn
go
*/

create table tHeadColumn
(
  id nvarchar(19) not null,

  columnNo nvarchar(40) not null,
  menuId nvarchar(19) not null,
  pageTypeNo nvarchar(40) not null,

  seq tinyint not null default(1),
  areHidden bit not null default(0),
  areSysRequired bit not null default(0),
  areUserRequired bit not null default(0),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_THEADCOLUMN primary key(id)
)
go

create unique index uq_tHeadColumn
  on tHeadColumn(columnNo, menuId, pageTypeNo)
go

/*
if object_id('tHeadColumnLang', 'U') is not null
    drop table tHeadColumnLang
go
*/

create table tHeadColumnLang
(
  id nvarchar(19) not null,

  headColumnId nvarchar(19) not null,
  langId nvarchar(19) not null,

  columnName nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_THEADCOLUMNLANG primary key(id)
)
go

create unique index uq_tHeadColumnLang
  on tHeadColumnLang(headColumnId, langId)
go

/*
if object_id('tDtlColumn', 'U') is not null
    drop table tDtlColumn
go
*/

create table tDtlColumn
(
  id nvarchar(19) not null,

  menuId nvarchar(19) not null,
  columnNo nvarchar(40) not null,
  arrayNo nvarchar(20) not null,

  dataTypeNo nvarchar(40) not null,
  pageTypeNo nvarchar(40) not null,
  seq tinyint not null default(1),
  areHidden bit not null default(0),
  areSysRequired bit not null default(0),
  areUserRequired bit not null default(0),
  width int not null,
  alias nvarchar(200) not null,
  formula nvarchar(200) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TDTLCOLUMN primary key(id)
)
go

create unique index uq_tDtlColumn
  on tDtlColumn(menuId, columnNo, arrayNo)
go

/*
if object_id('tDtlColumnLang', 'U') is not null
    drop table tDtlColumnLang
go
*/

create table tDtlColumnLang
(
  id nvarchar(19) not null,

  dtlColumnId nvarchar(19) not null,
  langId nvarchar(19) not null,

  columnName nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TDTLCOLUMNLANG primary key(id)
)
go

create unique index uq_tDtlColumnLang
  on tDtlColumnLang(dtlColumnId, langId)
go

/*
if object_id('tUserColumn', 'U') is not null
    drop table tUserColumn
go
*/

create table tUserColumn
(
  id nvarchar(19) not null,

  columnId nvarchar(19) not null,
  userId nvarchar(19) not null,

  columnName nvarchar(40) not null default(''),
  seq tinyint null,
  areSysHidden bit null,
  areUserHidden bit null,
  width int null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TUSERCOLUMN primary key(id)
)
go

create unique index uq_tUserColumn
  on tUserColumn(columnId, userId)
go