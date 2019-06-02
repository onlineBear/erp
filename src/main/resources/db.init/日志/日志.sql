use MISDB
go

-- select 'PK_' + upper('tOperationLog')

/*
if object_id('tOperationLog', 'U') is not null
    drop table tOperationLog
go
*/

create table tOperationLog
(
  id nvarchar(19) not null,

  recordId nvarchar(19) not null,

  operTime datetime not null,
  operUserId nvarchar(19) not null,
  ipv4 nvarchar(15) not null,
  operTypeNo nvarchar(20) not null,
  operMenuId nvarchar(19) not null,
  operDesc nvarchar(100) not null,
  url nvarchar(100) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TOPERATIONLOG primary key(id)
)
go
