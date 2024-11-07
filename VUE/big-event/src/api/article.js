import request from '@/utils/request.js'

// 获取文章分类列表
export const areticleCategoryListService = () => {
    // const tokenStore = useTokenStore();
    //在pinia中定义的咱应式数据，都不器要.value
    // return request.get('/category',{headers:{Authorization:tokenStore.token}})
    return request.get('/category')
}

// 添加文章分类
export const articleCategoryAddService = (CategoryData) => {
    return request.post('/category', CategoryData)
}

// 修改文章分类
export const articleCategoryUpdateService = (CategoryData) => {
    return request.put('/category', CategoryData)
}

// 删除文章分类
export const articleCategoryDeleteService = (id) => {
    return request.delete('/category?id=' + id)
}

//文章列表查询
export const articleListService = (params) => {
    return request.get('/article', { params: params })
}

//文章添加
export const articleAddService = (articleData) => {
    return request.post('/article', articleData)
}