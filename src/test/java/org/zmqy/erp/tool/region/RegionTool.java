package org.zmqy.erp.tool.region;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.mapper.mis.biz.sys.base.RegionLangMapper;
import org.zmqy.erp.mapper.mis.biz.sys.base.RegionMapper;
import org.zmqy.erp.model.mis.entity.sys.base.Region;
import org.zmqy.erp.model.mis.entity.sys.base.RegionLang;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RegionTool {
    @Autowired
    private RegionMapper mapper;
    @Autowired
    private RegionLangMapper regionLangMapper;

    @Test
    @Transactional
    @Rollback(false)
    public void save(){
        Date operTime = new Date();

        String[] regionNoList = {"450102", "450103", "450105", "450107", "450108",
                                 "450109", "450110"};
        String upRegionNo = "450100";
        String[] regionNameList = {"兴宁区", "青秀区", "江南区", "西乡塘区", "良庆区",
                                   "邕宁区", "武鸣区"};
        String[] regionShortNameList = {"兴宁区", "青秀区", "江南区", "西乡塘区", "良庆区",
                "邕宁区", "武鸣区"};

        for(int i=0;i<regionNameList.length;i++){
            String regionNo = regionNoList[i];
            String regionName = regionNameList[i];
            String regionShortName = regionShortNameList[i];

            QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(Region.REGIONNO, upRegionNo);

            Region upRegion = this.mapper.selectOne(queryWrapper);

            if(upRegion == null){
                throw new RuntimeException("无此父级地区编码");
            }

            Region region = new Region();
            region.setRegionNo(regionNo);
            region.setUpRegionId(upRegion.getId());
            region.setRegionLevel(upRegion.getRegionLevel() + 1);
            region.setCreateUserId("");
            region.setCreateTime(operTime);
            region.setLastUpdateTime(operTime);

            this.mapper.insert(region);

            RegionLang regionLang = new RegionLang();
            regionLang.setLangId("1073471954843422721");
            regionLang.setRegionId(region.getId());
            regionLang.setRegionName(regionName);
            regionLang.setRegionShortName(regionShortName);
            regionLang.setCreateUserId("");
            regionLang.setCreateTime(operTime);
            regionLang.setLastUpdateTime(operTime);

            this.regionLangMapper.insert(regionLang);
        }
    }
}
