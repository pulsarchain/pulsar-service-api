package com.bosha.common.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.bosha.common.api.dto.ChainCategoryDto;
import com.bosha.common.api.dto.HelpCenterCategoryDto;
import com.bosha.common.api.dto.HelpCenterDto;
import com.bosha.common.api.dto.InstructionsInfoDto;
import com.bosha.common.api.dto.SystemContentManagerListDto;
import com.bosha.common.api.dto.SystemContentManagerQueryDto;
import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.entity.DictI18n;
import com.bosha.common.api.entity.SystemContent;
import com.bosha.common.api.entity.SystemContentI18n;
import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.common.api.service.DictService;
import com.bosha.common.api.service.SystemContentService;
import com.bosha.common.server.config.CommonServiceConfig;
import com.bosha.common.server.mapper.DictMapper;
import com.bosha.common.server.mapper.SystemContentI18nMapper;
import com.bosha.common.server.mapper.SystemContentMapper;
import com.bosha.commons.dto.Page;
import com.bosha.commons.enums.LanguageEnum;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SystemContentServiceImpl
 * @Author liqingping
 * @Date 2019-12-31 16:32
 */
@Service
@Slf4j
public class SystemContentServiceImpl implements SystemContentService {

    @Autowired
    private SystemContentMapper systemContentMapper;
    @Autowired
    private DictService dictService;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private CommonServiceConfig commonServiceConfig;
    @Autowired
    private SystemContentI18nMapper systemContentI18nMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(SystemContent systemContent) {
        check(systemContent);
        Set<String> collect = systemContent.getI18ns().stream().map(SystemContentI18n::getLanguage).collect(Collectors.toSet());
        if (collect.size() != systemContent.getI18ns().size())
            throw new BaseException("language有重复");
        for (LanguageEnum value : LanguageEnum.values()) {
            if (!collect.contains(value.name()))
                throw new BaseException("language错误");
        }
        systemContent.setCreateTime(new Date());
        systemContent.setUpdateTime(null);
        if (systemContent.getStatus() == null)
            systemContent.setStatus(1);
        if (systemContent.getPush() == null)
            systemContent.setPush(0);
        List<SystemContentI18n> i18ns = systemContent.getI18ns();
        Optional<SystemContentI18n> optional = i18ns.stream().filter(systemContentI18n -> systemContentI18n.getLanguage().equals(LanguageEnum.zh_CN.name())).findFirst();
        if (optional.isPresent()) {
            SystemContentI18n n = optional.get();
            systemContent.setContent(n.getContent());
            systemContent.setTitle(n.getName());
        }
        systemContentMapper.insertSelective(systemContent);
        i18ns.forEach(systemContentI18n -> systemContentI18n.setScId(systemContent.getId()));
        systemContentI18nMapper.batchInsert(i18ns);
        return systemContent.getId();
    }

    private void check(SystemContent systemContent) {
        if (systemContent.getDictId() == null)
            return;
        Dict dict = dictService.dict(systemContent.getDictId());
        if (dict == null)
            throw new BaseException("分类不存在");
        DictTypeEnum dictTypeEnum = null;
        try {
            dictTypeEnum = DictTypeEnum.valueOf(dict.getType());
        } catch (IllegalArgumentException e) {
        }
        if (dictTypeEnum == DictTypeEnum.market || dictTypeEnum == DictTypeEnum.coin)
            throw new BaseException("不支持关联此分类");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SystemContent systemContent) {
        check(systemContent);
        systemContent.setUpdateTime(new Date());
        if (CollectionUtils.isNotEmpty(systemContent.getI18ns())) {
            List<SystemContentI18n> i18ns = systemContent.getI18ns();
            Optional<SystemContentI18n> optional = i18ns.stream().filter(systemContentI18n -> systemContentI18n.getLanguage().equals(LanguageEnum.zh_CN.name())).findFirst();
            if (optional.isPresent()) {
                SystemContentI18n n = optional.get();
                systemContent.setContent(n.getContent());
                systemContent.setTitle(n.getName());
            }
            for (SystemContentI18n i18n : i18ns) {
                for (LanguageEnum value : LanguageEnum.values()) {
                    if (!value.name().equals(i18n.getLanguage()))
                        throw new BaseException("language错误");
                }
                if (StringUtils.isAnyBlank(i18n.getName(), i18n.getContent()))
                    throw new BaseException("名称或内容不可为空");
                SystemContentI18n select = systemContentI18nMapper.getByScIdAndLanguage(systemContent.getId(), i18n.getLanguage());
                i18n.setScId(systemContent.getId());
                if (select == null) {
                    systemContentI18nMapper.insertSelective(i18n);
                } else {
                    systemContentI18nMapper.update(i18n);
                }
            }
        }
        systemContentMapper.updateByPrimaryKeySelective(systemContent);
        return true;
    }

    @Override
    public PageInfo<SystemContentManagerListDto> list(SystemContentManagerQueryDto query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(systemContentMapper.list(query));
    }

    @Override
    public List<HelpCenterDto> helpCenter() {
        LanguageEnum language = RequestContextUtils.getRequestInfo().getLanguage();
        List<HelpCenterDto> centerList = new ArrayList<>();
        List<Dict> list = dictService.listType(DictTypeEnum.help_center);
        for (Dict dict : list) {
            Optional<DictI18n> first = dict.getI18ns().stream().filter(dictI18n -> dictI18n.getLanguage().equals(language.name())).findFirst();
            String name = dict.getKey();
            if (first.isPresent()) {
                name = first.get().getName();
            }
            List<HelpCenterCategoryDto> center = systemContentMapper.helpCenter(dict.getId(), language.name());
            centerList.add(HelpCenterDto.builder().name(name).categories(center).build());
        }
        return centerList;
    }

    @Override
    public List<InstructionsInfoDto> instructions(String type) {
        LanguageEnum language = RequestContextUtils.getRequestInfo().getLanguage();
        List<Long> collect;
        if (StringUtils.isBlank(type)) {
            Dict dict = dictService.dict(DictTypeEnum.instructions);
            collect = dict.getChildren()
                    .stream().filter(dict1 -> !commonServiceConfig.getInstructions().contains(dict1.getValue()))
                    .map(Dict::getId)
                    .collect(Collectors.toList());
        } else
            collect = dictMapper.getIdsByValue(type);
        if (CollectionUtils.isEmpty(collect))
            return new ArrayList<>();
        return systemContentMapper.instructions(collect, language.name());
    }

    @Override
    public SystemContent getById(Long id) {
        LanguageEnum language = RequestContextUtils.getRequestInfo().getLanguage();
        SystemContent systemContent = systemContentMapper.selectByPrimaryKey(id);
        Optional<SystemContentI18n> first = systemContent.getI18ns().stream().filter(systemContentI18n -> systemContentI18n.getLanguage().equals(language.name())).findFirst();
        if (first.isPresent()) {
            SystemContentI18n n = first.get();
            systemContent.setTitle(n.getName());
            systemContent.setContent(n.getContent());
        }
        systemContent.setI18ns(null);
        return systemContent;
    }

    @Override
    public PageInfo<ChainCategoryDto> chainList(Page page, String name) {
        LanguageEnum language = RequestContextUtils.getRequestInfo().getLanguage();
        Dict dict = dictService.dict(DictTypeEnum.chain);
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(systemContentMapper.chain(dict.getId(), name, language.name()));
    }

    @Override
    public int countByDictId(Long dictId) {
        return systemContentMapper.countByDictId(dictId);
    }

    @Override
    public Long getMiningRuleId(Long dictId) {
        return systemContentMapper.getMiningRuleId(dictId);
    }

}
