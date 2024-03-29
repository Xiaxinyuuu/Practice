package com.xiaxinyu.cloudmall.categoryproduct.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaxinyu.cloudmall.categoryproduct.model.dao.CategoryMapper;
import com.xiaxinyu.cloudmall.categoryproduct.model.pojo.Category;
import com.xiaxinyu.cloudmall.categoryproduct.model.request.AddCategoryReq;
import com.xiaxinyu.cloudmall.categoryproduct.model.vo.CategoryVO;
import com.xiaxinyu.cloudmall.categoryproduct.service.CategoryService;
import com.xiaxinyu.cloudmall.common.exception.ExceptionEnum;
import com.xiaxinyu.cloudmall.common.exception.ExceptionUnify;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq){
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        Category oldCategory = categoryMapper.selectByName(category.getName());
        if(oldCategory != null){
            throw new ExceptionUnify(ExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if(count != 1){
            throw new ExceptionUnify(ExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Category updateCategory){
        if(updateCategory.getName() != null) {
            Category oldCategory = categoryMapper.selectByName(updateCategory.getName());
            if (oldCategory != null && !oldCategory.getId().equals(updateCategory.getId())) {
                throw new ExceptionUnify(ExceptionEnum.NAME_EXISTED);
            }
        }
        int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
        if(count != 1){
            throw  new ExceptionUnify(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id){
        Category oldCategory = categoryMapper.selectByPrimaryKey(id);
        if(oldCategory == null){
            throw new ExceptionUnify(ExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if(count != 1){
            throw new ExceptionUnify(ExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo  listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listForCustomer")
    public List<CategoryVO> listForCustomer(Integer parentId){
        List<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList,parentId);
        return categoryVOList;
    }

    public void recursivelyFindCategories(List<CategoryVO> categoryVOList,Integer parentId){
        //递归获取所有子类别，并组合成一个目录树
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if(! CollectionUtils.isEmpty(categoryList)){
            for(int i = 0;i < categoryList.size();i ++){
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(),categoryVO.getId());
            }
        }
    }
}
