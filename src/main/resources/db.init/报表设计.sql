
--主报表设计表
CREATE TABLE [dbo].[tReportDesign] (
  [id] nvarchar(19) COLLATE Chinese_PRC_CS_AI  NOT NULL,
  [reportName] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
  [publishStatus] nvarchar(40) COLLATE Chinese_PRC_CS_AI  NOT NULL,
  [category] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
  [reportType] nvarchar(40) COLLATE Chinese_PRC_CS_AI  NOT NULL,
	[chartType] nvarchar(60) COLLATE Chinese_PRC_CS_AI  NOT NULL,
	[reportState] nvarchar(300) COLLATE Chinese_PRC_CS_AI  NOT NULL,
	[sql] nvarchar(4000) COLLATE Chinese_PRC_CS_AI  NOT NULL,
	[config] text NOT NULL,


	[createUserId] nvarchar(20)  NOT NULL,
  [createTime] datetime  NOT NULL,
	[createStore] nvarchar(20)  NOT NULL,
	[createStoreId] nvarchar(20)  NOT NULL,
  [lastUpdateTime] datetime  NOT NULL,
  CONSTRAINT [PK_tReportDesign] PRIMARY KEY CLUSTERED ([id])
	)

	--发布历史表
	CREATE TABLE [dbo].[tReportDesignHistory] (
      [id] nvarchar(19) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [reportId] nvarchar(19) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [reportName] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [publishStatus] nvarchar(40) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [category] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [reportType] nvarchar(40) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [chartType] nvarchar(60) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [reportState] nvarchar(300) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [sql] nvarchar(4000) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [config] text COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [version] int  NOT NULL,
      [createUserId] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [createTime] datetime  NOT NULL,
      [createStore] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [createStoreId] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
      [lastUpdateTime] datetime  NOT NULL,
      CONSTRAINT [PK_tReportDesignHistory] PRIMARY KEY CLUSTERED ([id])
    	)

--报表关系关联表
    	CREATE TABLE [dbo].[tReportRelation] (
          [reportId] nvarchar(19) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [childReportId] nvarchar(19) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [relationType] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [relationState] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          CONSTRAINT [PK_tReportRelation] PRIMARY KEY CLUSTERED ([reportId],[childReportId])
        	)


--报表打印模板
CREATE TABLE [dbo].[tTemplatePrint] (
          [id] nvarchar(19) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [reportId] nvarchar(19) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [templateFileName] nvarchar(100) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [templateName] nvarchar(100) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [scaling] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [filePath] nvarchar(200) COLLATE Chinese_PRC_CS_AI  NOT NULL,
          [width] int  NOT NULL,
          [height] int  NOT NULL,

					[codePrintModel] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
					[codeNumber] int  NOT NULL,
					[codeModel] nvarchar(20) COLLATE Chinese_PRC_CS_AI  NOT NULL,
					[createUserId] nvarchar(20)  NOT NULL,
          [createTime] datetime  NOT NULL,
          [lastUpdateTime] datetime  NOT NULL
          CONSTRAINT [PK_tTemplatePrint] PRIMARY KEY CLUSTERED ([id])
        	)

