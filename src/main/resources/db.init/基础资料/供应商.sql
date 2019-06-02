use MISDB
go

-- select 'PK_' + upper('tVendor')

/*
if object_id('tVendor', 'U') is not null
    drop table tVendor
go
*/

create table tVendor
(
  id nvarchar(19) not null,

  vendorNo nvarchar(20) not null,

  legalPerson nvarchar(30) not null default(''),
  salesManager nvarchar(30) not null default(''),
  tel nvarchar(15) not null default(''),
  nationNo nvarchar(20) not null default(''),
  provinceNo nvarchar(20) not null default(''),
  cityNo nvarchar(20) not null default(''),
  address nvarchar(200) not null default(''),
  postalcode nvarchar(6) not null default(''),
  email nvarchar(40) not null default(''),
  vendorTypeNo nvarchar(40) not null default(''),
  vendorNatureNo nvarchar(40) not null default(''),
  taxTypeNo nvarchar(40) not null default(''),
  socialCreditCode nvarchar(40) not null default(''),
  subjectTypeNo nvarchar(40) not null default(''),
  foundDate datetime null,
  issueBankNo nvarchar(20) not null default(''),
  issueBankAccount nvarchar(40) not null default(''),
  zfbAccount nvarchar(40) not null default(''),
  wechatAccount nvarchar(40) not null default(''),

  areInvoiceNotInOrder bit not null default(0),
  areOnlineSettle bit not null default(0),
  hasUpload2B2B bit not null default(0),
  interfaceNo nvarchar(40) not null default(''),
  accountSubject nvarchar(40) not null default(''),
  settleStoreNo nvarchar(20) not null default(''),
  enterDate datetime null,
  exeuntDate datetime null,
  payModeNo nvarchar(40) not null default(''),
  registeredCapital numeric(12,2) null,
  firstDay int null,
  contactPerson1 nvarchar(20) not null default(''),
  contractEmail1 nvarchar(40) not null default(''),
  contractMobile1 nvarchar(20) not null default(''),
  contractQQ1 nvarchar(15) not null default(''),
  contractWechat1 nvarchar(40) not null default(''),
  contactPerson2 nvarchar(20) not null default(''),
  contractEmail2 nvarchar(40) not null default(''),
  contractMobile2 nvarchar(20) not null default(''),
  contractQQ2 nvarchar(15) not null default(''),
  contractWechat2 nvarchar(40) not null default(''),
  contactPerson3 nvarchar(20) not null default(''),
  contractEmail3 nvarchar(40) not null default(''),
  contractMobile3 nvarchar(20) not null default(''),
  contractQQ3 nvarchar(15) not null default(''),
  contractWechat3 nvarchar(40) not null default(''),
  memo1 nvarchar(200) not null default(''),
  memo2 nvarchar(200) not null default(''),
  memo3 nvarchar(200) not null default(''),
  memo4 nvarchar(200) not null default(''),
  memo5 nvarchar(200) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TVENDOR primary key(id)
)
go

create unique index uq_tVendor
  on tVendor(vendorNo)
go


/*
if object_id('tVendorLang', 'U') is not null
    drop table tVendorLang
go
*/

create table tVendorLang
(
  id nvarchar(19) not null,

  langId nvarchar(19) not null,
  vendorId nvarchar(19) not null,

  vendorName nvarchar(100) not null,
  vendorShortName nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TVENDORLANG primary key(id)
)
go

create unique index uq_tVendorLang
  on tVendorLang(langId, vendorId)
go

/*
if object_id('tVendorCertif', 'U') is not null
    drop table tVendorCertif
go
*/

create table tVendorCertif
(
  id nvarchar(19) not null,

  vendorId nvarchar(19) not null,
  certifTypeNo nvarchar(40) not null,
  certifNo nvarchar(20) not null,

  expirationDate datetime not null,
  picUrl nvarchar(200) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TVENDORCERTIF primary key(id)
)
go

create unique index uq_tVendorCertif
  on tVendorCertif(vendorId, certifTypeNo, certifNo)
go
