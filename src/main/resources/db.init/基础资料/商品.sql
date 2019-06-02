use MISDB
go

-- select 'PK_' + upper('tBarcode')

/*
if object_id('tBarcode', 'U') is not null
    drop table tBarcode
go
*/

create table tBarcode
(
  id nvarchar(19) not null,

  goodsId nvarchar(19) not null,

  barcode nvarchar(14) not null,
  areMainBarcode bit not null,
  homeNo nvarchar(40) not null default(''),
  eachUnit numeric(16,3) not null default(1),
  memo nvarchar(200) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TBARCODE primary key(id)
)
go

create unique index uq_tBarcode
  on tBarcode(barcode)
go

/*
if object_id('tBrand', 'U') is not null
    drop table tBrand
go
*/

create table tBrand
(
  id nvarchar(19) not null,

  brandNo nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TBRAND primary key(id)
)
go

create unique index uq_tBrand
  on tBrand(brandNo)
go

/*
if object_id('tBrandLang', 'U') is not null
    drop table tBrandLang
go
*/

create table tBrandLang
(
  id nvarchar(19) not null,

  langId nvarchar(19) not null,
  brandId nvarchar(19) not null,

  brandName nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TBRANDLANG primary key(id)
)
go

create unique index uq_tBrandLang
  on tBrandLang(langId, brandId)
go

/*
if object_id('tCategory', 'U') is not null
    drop table tCategory
go
*/

create table tCategory
(
  id nvarchar(19) not null,

  upCategoryId nvarchar(19) not null,

  categoryNo nvarchar(20) not null,
  goodsCount int null,
  level int not null,
  antiLevel int not null,
  path nvarchar(500) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TCATEGORY primary key(id)
)
go

create unique index uq_tCategory
  on tCategory(categoryNo)
go

/*
if object_id('tCategoryLang', 'U') is not null
    drop table tCategoryLang
go
*/

create table tCategoryLang
(
  id nvarchar(19) not null,

  langId nvarchar(19) not null,
  categoryId nvarchar(19) not null,

  categoryName nvarchar(100) not null,
  categoryShortName nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TCATEGORYLANG primary key(id)
)
go

/*
if object_id('tComplexElement', 'U') is not null
    drop table tComplexElement
go
*/

create table tComplexElement
(
  id nvarchar(19) not null,

  goodsId nvarchar(19) not null,
  elementId nvarchar(19) not null,

  qty numeric(10,3) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TCOMPLEXELEMENT primary key(id)
)
go

create unique index uq_tComplexElement
  on tComplexElement(goodsId, elementId)
go

/*
if object_id('tGoods', 'U') is not null
    drop table tGoods
go
*/

create table tGoods
(
  id nvarchar(19) not null,

  categoryId nvarchar(19) not null,
  brandId nvarchar(19) not null,

  goodsNo nvarchar(20) not null,
  mainBarcode nvarchar(20) not null,
  pinyinCode nvarchar(50) not null default(''),
  buyer nvarchar(20) not null default(''),
  sourceTypeNo nvarchar(40) not null default(''),
  minimunUnitNo nvarchar(40) not null default(''),
  homeNo nvarchar(40) not null default(''),
  suggestSalePrice numeric(12,4) not null,
  saleTaxRate numeric(6,4) not null default(0),
  shelfLife int null,
  nationNo nvarchar(20) not null default(''),
  goodsLevelNo nvarchar(40) not null default(''),
  depositTypeNo nvarchar(40) not null default(''),
  length numeric(8,2) null,
  width numeric(8,2) null,
  height numeric(8,2) null,
  weight numeric(8,2) null,
  caseLength int null,
  caseWidth int null,
  caseHeight int null,
  caseWeight int null,
  defaultPurchaseSpecQty int not null default(1),
  defaultDistributionSpecQty int not null default(1),
  defaultWholesaleSpecQty int not null default(1),
  defaultRetailSpecQty int not null default(1),
  memo1 nvarchar(200) not null default(''),
  memo2 nvarchar(200) not null default(''),
  memo3 nvarchar(200) not null default(''),
  memo4 nvarchar(200) not null default(''),
  memo5 nvarchar(200) not null default(''),

  orderModeNo nvarchar(40) not null,
  inboundCtrlDay int null,
  outboundCtrlDay int null,
  goodsTypeNo nvarchar(40) not null,
  lifeCycleNo nvarchar(40) not null,
  saleStockDay int null,
  oplM int null,

  canVendorOrder bit not null default(1),
  canVendorReturn bit not null default(1),
  canDcOrder bit not null default(1),
  canDcReturn bit not null default(1),
  canSale bit not null default(1),
  canShopChangeSalePrice bit not null default(1),
  areTiny bit not null default(0),
  areSeasonal bit not null default(0),
  areSelfOperated bit not null default(0),
  areMustSell bit not null default(0),
  areCutParentGoods bit not null default(0),
  areCutChildGoods bit not null default(0),
  areClearStockEachDay bit not null default(0),
  areFreshGoods bit not null default(0),
  areRawMaterial bit not null default(0),
  areAntiTheftDeduction bit not null default(0),
  areFullColdChain bit not null default(0),
  arePlaceOfOrigin bit not null default(0),
  areNotJoinPromo bit not null default(0),
  areValuableGoods bit not null default(0),

  hasCheck bit not null default(0),
  checkUserId nvarchar(19) null,
  checkTime datetime null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TGOODS primary key(id)
)
go

create unique index uq_tGoods
  on tGoods(goodsNo)
go

/*
if object_id('tGoodsLang', 'U') is not null
    drop table tGoodsLang
go
*/

create table tGoodsLang
(
  id nvarchar(19) not null,

  langId nvarchar(19) not null,
  goodsId nvarchar(19) not null,

  goodsName nvarchar(100) not null,
  goodsShortName nvarchar(40) not null,
  spec nvarchar(20) not null default(''),

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TGOODSLANG primary key(id)
)
go

create unique index uq_tGoodsLang
  on tGoodsLang(langId, goodsId)
go

/*
if object_id('tMultiSpecGoods', 'U') is not null
    drop table tMultiSpecGoods
go
*/

create table tMultiSpecGoods
(
  id nvarchar(19) not null,

  goodsId nvarchar(19) not null,
  specQty int not null,
  unitNo nvarchar(40) not null,

  purchaseSpecNo nvarchar(40) not null,
  distributionSpecNo nvarchar(40) not null,
  wholesaleSpecNo nvarchar(40) not null,
  retailSpecNo nvarchar(40) not null,

  createUserId nvarchar(19) not null,
  createTime datetime not null,
  lastUpdateTime datetime not null,

  constraint PK_TMULTISPECGOODS primary key(id)
)
go

create unique index uq_tMultiSpecGoods
  on tMultiSpecGoods(goodsId, specQty, unitNo)
go
