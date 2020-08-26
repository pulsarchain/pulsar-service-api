package com.bosha.common.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.bosha.common.api.dto.DictRequestDto;
import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.entity.DictI18n;
import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.common.api.service.DictService;
import com.bosha.common.server.mapper.DictI18nMapper;
import com.bosha.common.server.mapper.DictMapper;
import com.bosha.commons.enums.LanguageEnum;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: DictServiceImpl
 * @Author liqingping
 * @Date 2019-12-14 18:10
 */
@RestController
@Slf4j
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictI18nMapper dictI18nMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(@RequestBody Dict dict) {
        if (dict.getParentId() == 0) {
            throw new BaseException("暂不支持后台添加一级分类");
        }
        Set<String> collect = dict.getI18ns().stream().map(DictI18n::getLanguage).collect(Collectors.toSet());
        if (collect.size() != dict.getI18ns().size())
            throw new BaseException("language有重复");
        for (LanguageEnum value : LanguageEnum.values()) {
            if (!collect.contains(value.name()))
                throw new BaseException("language错误");
        }
        dict.setCreateTime(new Date());
        dict.setOperator(RequestContextUtils.getRequestInfo().getAdminUserName());
        Optional<DictI18n> optional = dict.getI18ns().stream().filter(n -> n.getLanguage().equals(LanguageEnum.zh_CN.name())).findFirst();
        if (optional.isPresent()) {
            DictI18n n = optional.get();
            dict.setKey(n.getName());
        }
        try {
            dictMapper.insertSelective(dict);
        } catch (DuplicateKeyException e) {
            throw new BaseException(dict.getType() + " 类型下已存在此 " + dict.getKey() + " key");
        }
        dict.getI18ns().forEach(dictI18n -> dictI18n.setDictId(dict.getId()));
        dictI18nMapper.batchInsert(dict.getI18ns());
        return dict.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(@RequestBody Dict dict) {
        List<DictI18n> i18ns = dict.getI18ns();
        Optional<DictI18n> optional = dict.getI18ns().stream().filter(n -> n.getLanguage().equals(LanguageEnum.zh_CN.name())).findFirst();
        if (optional.isPresent()) {
            DictI18n n = optional.get();
            dict.setKey(n.getName());
        }
        if (CollectionUtils.isNotEmpty(i18ns)) {
            for (DictI18n i18n : i18ns) {
                for (LanguageEnum value : LanguageEnum.values()) {
                    if (!value.name().equals(i18n.getLanguage()))
                        throw new BaseException("language错误");
                }
                if (StringUtils.isBlank(i18n.getName()))
                    throw new BaseException("名称不可为空");
                DictI18n select = dictI18nMapper.getByDictIdAndLanguage(dict.getId(), i18n.getLanguage());
                DictI18n build = DictI18n.builder().dictId(dict.getId()).name(i18n.getName()).language(i18n.getLanguage()).build();
                if (select == null) {
                    dictI18nMapper.insertSelective(build);
                } else {
                    dictI18nMapper.update(build);
                }
            }
        }
        dictMapper.updateByPrimaryKeySelective(dict);
        return true;
    }

    @Override
    public PageInfo<Dict> list(@RequestBody DictRequestDto dto) {
        PageHelper.startPage(dto.getPage(), dto.getSize());
        return PageInfo.of(dictMapper.listParent(dto.getName(), dto.getType().name()));
    }

    @Override
    public PageInfo<Dict> list(@RequestParam(value = "name", required = false) String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        PageHelper.startPage(page, size);
        return PageInfo.of(dictMapper.projectCategories(name));
    }

    @Override
    public List<Dict> listType(@RequestParam("type") DictTypeEnum dictType) {
        return dictMapper.list(dictType.name());
    }

    @Override
    public Map<String, Dict> mapType(@RequestParam("type") DictTypeEnum dictType) {
        return listType(dictType).stream().collect(Collectors.toMap(Dict::getKey, dict -> dict));
    }

    @Override
    public Dict dict(@PathVariable Long id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    @Override
    public Dict dict(@RequestParam("type") DictTypeEnum dictTypeEnum) {
        return dictMapper.getByType(dictTypeEnum.name());
    }

    @Override
    public List<Dict> getByParentId(@RequestParam("parentId") Long parentId) {
        return dictMapper.getByParentId(parentId, true);
    }

    @Override
    public Dict getByTypeAndValue(@RequestParam("typeEnum") DictTypeEnum typeEnum, @RequestParam("value") String value) {
        return dictMapper.getByTypeAndValue(typeEnum.name(), value);
    }

}
